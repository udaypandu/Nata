package nata.com.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.SpinnerAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.interfaces.IUpdateSelectedFile;
import nata.com.models.ForumModel;
import nata.com.models.JobPostingModel;
import nata.com.models.Model;
import nata.com.models.SpinnerModel;
import nata.com.nata.R;
import nata.com.parsers.ForumParser;
import nata.com.parsers.JobPostingParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by Shankar on 4/3/2017.
 */

public class PostAskForHelpFragment extends Fragment implements View.OnClickListener, IUpdateSelectedFile, IAsyncCaller {
    public static final String TAG = "PostAskForHelpFragment";
    private DashboardActivity mParent;
    private Toolbar mToolbar;
    private View rootView;

    private TextView tv_post;
    private EditText edt_topic_title;
    private EditText edt_write_some_thing;
    private EditText et_select_category;

    private TextView tv_camera_icon;
    private TextView tv_gallery_icon;

    private Typeface typeface;
    private Typeface mMaterilTypeface;

    private File mSelectedFile;
    private static IUpdateSelectedFile iUpdateSelectedFile;
    private RelativeLayout rl_img_item;
    private ImageView img_selected;
    private TextView tv_delete_icon;

    private String mFrom;
    private ForumModel mForumModel;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        iUpdateSelectedFile = this;
        Bundle bundle = getArguments();
        mFrom = bundle.getString(Constants.FROM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.share_your_opinion));
        rootView = inflater.inflate(R.layout.fragment_ask_for_help_post, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        getCategoryList();
        typeface = Utility.setTypeFace_Lato_Regular(mParent);
        mMaterilTypeface = Utility.setTypeFace_Image(mParent);
        tv_post = (TextView) rootView.findViewById(R.id.tv_post);
        tv_post.setTypeface(typeface);

        edt_topic_title = (EditText) rootView.findViewById(R.id.edt_topic_title);
        edt_topic_title.setTypeface(typeface);

        edt_write_some_thing = (EditText) rootView.findViewById(R.id.edt_write_some_thing);
        edt_write_some_thing.setTypeface(typeface);

        et_select_category = (EditText) rootView.findViewById(R.id.et_select_category);
        et_select_category.setTypeface(typeface);

        tv_camera_icon = (TextView) rootView.findViewById(R.id.tv_camera_icon);
        tv_camera_icon.setTypeface(mMaterilTypeface);
        tv_gallery_icon = (TextView) rootView.findViewById(R.id.tv_gallery_icon);
        tv_gallery_icon.setTypeface(mMaterilTypeface);

        rl_img_item = (RelativeLayout) rootView.findViewById(R.id.rl_img_item);
        img_selected = (ImageView) rootView.findViewById(R.id.img_selected);
        tv_delete_icon = (TextView) rootView.findViewById(R.id.tv_delete_icon);
        tv_delete_icon.setTypeface(mMaterilTypeface);
        rl_img_item.setVisibility(View.GONE);

        tv_gallery_icon.setOnClickListener(this);
        tv_camera_icon.setOnClickListener(this);
        tv_delete_icon.setOnClickListener(this);
        et_select_category.setOnClickListener(this);
        tv_post.setOnClickListener(this);
    }

    private void getCategoryList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        ForumParser mForumParser = new ForumParser();
        String mFinalUrl = APIConstants.FORUM_ALL + "2";
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                mFinalUrl, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mForumParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_post:
                if (isValidFields()) {
                    postForum();
                }
                break;
            case R.id.tv_gallery_icon:
                pickFile();
                break;
            case R.id.tv_camera_icon:
                captureFile();
                break;
            case R.id.et_select_category:
                setDataToTheCategory();
                break;
            case R.id.tv_delete_icon:
                mSelectedFile = null;
                rl_img_item.setVisibility(View.GONE);
                break;
        }
    }

    private void setDataToTheCategory() {
        if (mForumModel != null)
            showSpinnerDialog(getActivity(), "Category", et_select_category, mForumModel.getForumsSpinnerModels(), 1);
    }

    public void showSpinnerDialog(final Context context, final String title, final EditText et_spinner,
                                  ArrayList<SpinnerModel> itemsList, final int id) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);

        /*CUSTOM TITLE*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_dialog_title, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_alert_dialog_title);
        RelativeLayout dialog_back_ground = (RelativeLayout) view.findViewById(R.id.dialog_back_ground);
        dialog_back_ground.setBackgroundColor(context.getResources().getColor(R.color.themeColor));
        tv_title.setText(title);
        tv_title.setTextColor(context.getResources().getColor(R.color.blackColor));
        builderSingle.setCustomTitle(view);


        final SpinnerAdapter adapter = new SpinnerAdapter(context, itemsList);
        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpinnerModel mData = (SpinnerModel) adapter.getItem(which);
                        if (id == 1) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                        }
                    }
                });
        builderSingle.show();
    }

    private void postForum() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        paramMap.put("title", edt_topic_title.getText().toString());
        paramMap.put("content", edt_write_some_thing.getText().toString());
        if (mFrom.equalsIgnoreCase(PublicForumFragment.TAG))
            paramMap.put("isprivate", "0");
        else
            paramMap.put("isprivate", "1");
        if (mSelectedFile != null) {
            paramMap.put("image", Utility.convertFileToByteArray(mSelectedFile));
            paramMap.put("image_name", mSelectedFile.getName());
        }
        JobPostingParser mJobPostingParser = new JobPostingParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.ASK_MY_HELP_POST + getCategoryId(et_select_category.getText().toString()),
                paramMap,
                APIConstants.REQUEST_TYPE.POST,
                this, mJobPostingParser);
        Utility.execute(serverIntractorAsync);

    }

    private String getCategoryId(String s) {
        String mCategoryID = "";
        for (int i = 0; i < mForumModel.getForumModels().size(); i++) {
            if (mForumModel.getForumModels().get(i).getName().equals(s)) {
                mCategoryID = mForumModel.getForumModels().get(i).getId();
            }
        }
        return mCategoryID;
    }


    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(et_select_category.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, et_select_category, "Please select category");
            et_select_category.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_topic_title.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, edt_topic_title, "Please enter topic title");
            edt_topic_title.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_write_some_thing.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, edt_write_some_thing, "Please write something");
            edt_write_some_thing.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    private void pickFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mParent.startActivityForResult(Intent.createChooser(intent, "Select Post Image"), Constants.FROM_POST_ASK_FOR_HELP_GALLERY_ID);
    }

    private void captureFile() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mParent.startActivityForResult(intent, Constants.FROM_POST_ASK_FOR_HELP_CAMERA_ID);
    }

    @Override
    public void updateFile(String path) {
        mSelectedFile = new File(path);
        rl_img_item.setVisibility(View.VISIBLE);
        Uri uri = Uri.fromFile(mSelectedFile);
        Picasso.with(mParent).load(uri).placeholder(R.drawable.place_holder).into(img_selected);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof JobPostingModel) {
                    JobPostingModel jobPostingModel = (JobPostingModel) model;
                    Utility.showToastMessage(getActivity(), jobPostingModel.getMessage());
                    if (mFrom.equalsIgnoreCase(AskForHelpFragment.TAG)) {
                        AskForHelpFragment.getInstance().updateNewData();
                    }
                    getActivity().onBackPressed();
                } else if (model instanceof ForumModel) {
                    mForumModel = (ForumModel) model;
                }
            } else {
                Utility.showToastMessage(mParent, "OOPS..! Some problem with the API");
            }
        }
    }
}
