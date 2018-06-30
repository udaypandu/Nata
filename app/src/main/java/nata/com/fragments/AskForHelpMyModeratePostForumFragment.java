package nata.com.fragments;

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
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.interfaces.IUpdateSelectedFile;
import nata.com.models.JobPostingModel;
import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.parsers.JobPostingParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by Shankar on 4/3/2017.
 */

public class AskForHelpMyModeratePostForumFragment extends Fragment implements View.OnClickListener, IUpdateSelectedFile, IAsyncCaller {
    public static final String TAG = "AskForHelpMyModeratePostForumFragment";
    private DashboardActivity mParent;
    private Toolbar mToolbar;
    private View rootView;

    private ImageView img_forum_post;
    private TextView tv_forum_name;
    private TextView tv_post;
    private EditText edt_topic_title;
    private EditText edt_write_some_thing;

    private TextView tv_camera_icon;
    private TextView tv_gallery_icon;

    private Typeface typeface;
    private Typeface mMaterilTypeface;

    private File mSelectedFile;
    private static IUpdateSelectedFile iUpdateSelectedFile;
    private RelativeLayout rl_img_item;
    private ImageView img_selected;
    private TextView tv_delete_icon;

    private String id;
    private String name;
    private String img_url;

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
        id = bundle.getString(Constants.ID);
        name = bundle.getString(Constants.POST_FROM);
        img_url = bundle.getString(Constants.POST_IMAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        rootView = inflater.inflate(R.layout.fragment_post_forum, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        typeface = Utility.setTypeFace_Lato_Regular(mParent);
        mMaterilTypeface = Utility.setTypeFace_Image(mParent);
        img_forum_post = (ImageView) rootView.findViewById(R.id.img_forum_post);
        tv_forum_name = (TextView) rootView.findViewById(R.id.tv_forum_name);
        tv_post = (TextView) rootView.findViewById(R.id.tv_post);
        tv_post.setTypeface(typeface);
        tv_forum_name.setTypeface(typeface);

        edt_topic_title = (EditText) rootView.findViewById(R.id.edt_topic_title);
        edt_topic_title.setTypeface(typeface);

        edt_write_some_thing = (EditText) rootView.findViewById(R.id.edt_write_some_thing);
        edt_write_some_thing.setTypeface(typeface);

        tv_camera_icon = (TextView) rootView.findViewById(R.id.tv_camera_icon);
        tv_camera_icon.setTypeface(mMaterilTypeface);
        tv_gallery_icon = (TextView) rootView.findViewById(R.id.tv_gallery_icon);
        tv_gallery_icon.setTypeface(mMaterilTypeface);

        rl_img_item = (RelativeLayout) rootView.findViewById(R.id.rl_img_item);
        img_selected = (ImageView) rootView.findViewById(R.id.img_selected);
        tv_delete_icon = (TextView) rootView.findViewById(R.id.tv_delete_icon);
        tv_delete_icon.setTypeface(mMaterilTypeface);
        rl_img_item.setVisibility(View.GONE);

        tv_forum_name.setText(name);
        if (!Utility.isValueNullOrEmpty(img_url))
            Utility.URLProfilePicLoading(img_forum_post,
                    img_url, null, R.drawable.place_holder);
        tv_gallery_icon.setOnClickListener(this);
        tv_camera_icon.setOnClickListener(this);
        tv_delete_icon.setOnClickListener(this);
        tv_post.setOnClickListener(this);
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
            case R.id.tv_delete_icon:
                mSelectedFile = null;
                rl_img_item.setVisibility(View.GONE);
                break;
        }
    }

    private void postForum() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        paramMap.put("title", edt_topic_title.getText().toString());
        paramMap.put("content", edt_write_some_thing.getText().toString());
        paramMap.put("isprivate", "0");
       /* else
            paramMap.put("isprivate", "1");*/
        if (mSelectedFile != null) {
            paramMap.put("image", Utility.convertFileToByteArray(mSelectedFile));
            paramMap.put("image_name", mSelectedFile.getName());
        }
        JobPostingParser mJobPostingParser = new JobPostingParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.ASK_HELPS_LIST_FOR_MODERATERS_POST + id,
                paramMap,
                APIConstants.REQUEST_TYPE.POST,
                this, mJobPostingParser);
        Utility.execute(serverIntractorAsync);

    }

    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(edt_topic_title.getText().toString().trim())) {
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
        mParent.startActivityForResult(Intent.createChooser(intent, "Select Post Image"), Constants.FROM_POST_ASK_FOR_HELP_MODERATE_GALLERY_ID);
    }

    private void captureFile() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mParent.startActivityForResult(intent, Constants.FROM_POST_ASK_FOR_HELP_MODERATE_CAMERA_ID);
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
                    AskForHelpMyModerateListFragment.getInstance().updateNewData();
                    mParent.onBackPressed();
                }
            } else {
                Utility.showToastMessage(mParent, "OOPS..! Some problem with the API");
            }
        }
    }

}
