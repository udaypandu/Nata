package nata.com.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;

import java.io.File;
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.interfaces.IUpdateSelectedFile;
import nata.com.models.DeletePostModel;
import nata.com.models.ForumDetailModel;
import nata.com.models.JobPostingModel;
import nata.com.models.Model;
import nata.com.models.PublicForumModel;
import nata.com.models.ReportAbuseModel;
import nata.com.models.ReportDeleteModel;
import nata.com.nata.R;
import nata.com.parsers.DeleteParser;
import nata.com.parsers.ForumDetailParser;
import nata.com.parsers.JobPostingParser;
import nata.com.parsers.ReportAbuseParser;
import nata.com.parsers.ReportDeleteParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumDetailFragment extends Fragment implements IAsyncCaller, View.OnClickListener, IUpdateSelectedFile {

    public static final String TAG = "ForumDetailFragment";
    private DashboardActivity mParent;
    private Toolbar mToolbar;
    private View rootView;

    private PublicForumModel publicForumModel;

    private ImageView img_person;
    private TextView tv_name;
    private TextView tv_date;
    private TextView tv_delete_icon;
    // private TextView tv_report_icon;
    private TextView tv_topic_name;
    private TextView tv_topic_details;
    private TextView txt_replies;
    private ImageView img_uploaded;

    private EditText edt_comment;
    private TextView txt_camera_icon;
    private TextView txt_send_icon;

    private Typeface typefaceLatoRegular;
    private Typeface typefaceMaterialRegular;
    private Typeface typefaceFontAwesomeRegular;
    private String mID = "";
    private static IUpdateSelectedFile iUpdateSelectedFile;

    private LinearLayout ll_reply;

    private File mSelectedFile;
    private EditText edt_upload_image;
    private EditText edt_reply_comment;
    private String mFrom;

    private NativeExpressAdView mAdView;
    private VideoController mVideoController;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        Bundle bundle = getArguments();
        if (bundle.containsKey(Constants.PUBLIC_FORUM_MODEL)) {
            publicForumModel = (PublicForumModel) bundle.getSerializable(Constants.PUBLIC_FORUM_MODEL);
        }
        if (bundle.containsKey(Constants.FROM)) {
            mFrom = bundle.getString(Constants.FROM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
        }
       /* if (rootView != null) {
            return rootView;
        }*/
        rootView = inflater.inflate(R.layout.fragment_forum_detail, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        getFullDataFromAPI();

        typefaceLatoRegular = Utility.setTypeFace_Lato_Regular(mParent);
        typefaceMaterialRegular = Utility.setTypeFace_Image(mParent);
        typefaceFontAwesomeRegular = Utility.setTypeFace_fontawesome(mParent);

        img_person = (ImageView) rootView.findViewById(R.id.img_person);
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_date = (TextView) rootView.findViewById(R.id.tv_date);
        tv_delete_icon = (TextView) rootView.findViewById(R.id.tv_delete_icon);
        //tv_report_icon = (TextView) rootView.findViewById(R.id.tv_report_icon);
        tv_topic_name = (TextView) rootView.findViewById(R.id.tv_topic_name);
        tv_topic_details = (TextView) rootView.findViewById(R.id.tv_topic_details);
        txt_replies = (TextView) rootView.findViewById(R.id.txt_replies);
        img_uploaded = (ImageView) rootView.findViewById(R.id.img_uploaded);

        edt_comment = (EditText) rootView.findViewById(R.id.edt_comment);
        txt_camera_icon = (TextView) rootView.findViewById(R.id.txt_camera_icon);
        txt_send_icon = (TextView) rootView.findViewById(R.id.txt_send_icon);

        ll_reply = (LinearLayout) rootView.findViewById(R.id.ll_reply);

        mAdView = (NativeExpressAdView) rootView.findViewById(R.id.adView);

        txt_replies.setTypeface(typefaceLatoRegular);
        edt_comment.setTypeface(typefaceLatoRegular);
        txt_camera_icon.setTypeface(typefaceFontAwesomeRegular);
        txt_send_icon.setTypeface(typefaceFontAwesomeRegular);

        tv_delete_icon.setOnClickListener(this);
        txt_camera_icon.setOnClickListener(this);
        txt_send_icon.setOnClickListener(this);



        //tv_report_icon.setOnClickListener(this);

        if (Constants.logAddsOnOrOff)
            mAdView.setVisibility(View.VISIBLE);
        // Set its video options.
        mAdView.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());

        // The VideoController can be used to get lifecycle events and info about an ad's video
        // asset. One will always be returned by getVideoController, even if the ad has no video
        // asset.
        mVideoController = mAdView.getVideoController();
        mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            @Override
            public void onVideoEnd() {
                super.onVideoEnd();
            }
        });

        // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
        // loading.
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mVideoController.hasVideoContent()) {
                    Log.d("sdf", "Received an ad that contains a video asset.");
                } else {
                    Log.d("sdf", "Received an ad that does not contain a video asset.");
                }
            }
        });
        mAdView.loadAd(new AdRequest.Builder().build());
    }

    private void getFullDataFromAPI() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        String mFinalUrl = "";
        if (mFrom.equalsIgnoreCase(AskForHelpMyModerateListFragment.TAG)) {
            mFinalUrl = APIConstants.ASK_HELPS_LIST_FOR_MODERATERS_POST_DETAIL + publicForumModel.getId();
        } else if (mFrom.equalsIgnoreCase(AskForHelpFragment.TAG)) {
            mFinalUrl = APIConstants.ASK_MY_HELP_GET_HELP_DETAIL + publicForumModel.getId();
        } else {
            mFinalUrl = APIConstants.TOPIC_FORUM + publicForumModel.getId();
        }
        ForumDetailParser mForumDetailParser = new ForumDetailParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                mFinalUrl
                , paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mForumDetailParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof ForumDetailModel) {
                    setForumDetailData((ForumDetailModel) model);
                } else if (model instanceof JobPostingModel) {
                    JobPostingModel jobPostingModel = (JobPostingModel) model;
                    Utility.showToastMessage(getActivity(), jobPostingModel.getMessage());
                    getFullDataFromAPI();
                } else if (model instanceof DeletePostModel) {
                    DeletePostModel deletePostModel = (DeletePostModel) model;
                    Utility.showToastMessage(getActivity(), deletePostModel.getMessage());
                    if (mFrom.equalsIgnoreCase(PublicForumFragment.TAG))
                        PublicForumFragment.getInstance().updateNewData();
                    else if (mFrom.equalsIgnoreCase(PrivateForumFragment.TAG))
                        PrivateForumFragment.getInstance().updateNewData();
                    else if (mFrom.equalsIgnoreCase(AskForHelpFragment.TAG))
                        AskForHelpFragment.getInstance().updateNewData();
                    else if (mFrom.equalsIgnoreCase(AskForHelpMyModerateListFragment.TAG))
                        AskForHelpMyModerateListFragment.getInstance().updateNewData();
                    mParent.onBackPressed();
                } else if (model instanceof ReportAbuseModel) {
                    ReportAbuseModel reportAbuseModel = (ReportAbuseModel) model;
                    Utility.showToastMessage(mParent, reportAbuseModel.getMessage());
                } else if (model instanceof ReportDeleteModel) {
                    ReportDeleteModel mReportDeleteModel = (ReportDeleteModel) model;
                    Utility.showToastMessage(mParent, mReportDeleteModel.getMessage());
                    getFullDataFromAPI();
                }
            } else {
                Utility.showToastMessage(mParent, model.getMessage());
            }
        }
    }

    private void setForumDetailData(ForumDetailModel forumDetailModel) {
        mID = forumDetailModel.getId();

        tv_name.setText(forumDetailModel.getUsername());
        tv_name.setTypeface(typefaceLatoRegular);
        tv_date.setText(Utility.getCommunityDateTime(forumDetailModel.getRecordeddate()));
        tv_date.setTypeface(typefaceLatoRegular);
        tv_delete_icon.setTypeface(typefaceMaterialRegular);
        //tv_report_icon.setTypeface(typefaceMaterialRegular);
        tv_topic_name.setTypeface(typefaceLatoRegular);
        tv_topic_name.setText(forumDetailModel.getName());
        tv_topic_details.setTypeface(typefaceLatoRegular);
        tv_topic_details.setText(forumDetailModel.getContent());
        if (!Utility.isValueNullOrEmpty(forumDetailModel.getImage())) {
            img_uploaded.setVisibility(View.VISIBLE);
            Utility.URLProfilePicLoading(img_uploaded,
                    forumDetailModel.getImage(), null, R.drawable.place_holder);
        } else {
            img_uploaded.setVisibility(View.GONE);
        }

        if (Utility.getSharedPrefStringData(mParent, Constants.PREF_KEY_UUID).
                equalsIgnoreCase(forumDetailModel.getUserid()) ||
                Utility.getSharedPrefStringData(mParent, Constants.PREF_KEY_ROLE_NAME).
                        equalsIgnoreCase("Super Admin")) {
            tv_delete_icon.setVisibility(View.VISIBLE);
        } else {
            tv_delete_icon.setVisibility(View.GONE);
        }

        setRepliesData(forumDetailModel);
    }

    private void setRepliesData(final ForumDetailModel forumDetailModel) {
        ll_reply.removeAllViews();
        if (forumDetailModel.getForumsReplyModels() != null && forumDetailModel.getForumsReplyModels().size() > 0) {
            txt_replies.setVisibility(View.VISIBLE);
            for (int i = 0; i < forumDetailModel.getForumsReplyModels().size(); i++) {
                LinearLayout comment_item = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.comments_item, null);
                ImageView img_comment_person = (ImageView) comment_item.findViewById(R.id.img_comment_person);
                TextView tv_comment_name = (TextView) comment_item.findViewById(R.id.tv_comment_name);
                TextView tv_comment_date = (TextView) comment_item.findViewById(R.id.tv_comment_date);
                ImageView img_comment = (ImageView) comment_item.findViewById(R.id.img_comment);
                TextView tv_comment_details = (TextView) comment_item.findViewById(R.id.tv_comment_details);
                TextView tv_report_icon = (TextView) comment_item.findViewById(R.id.tv_report_icon);

                if (Utility.getSharedPrefStringData(mParent, Constants.PREF_KEY_UUID).
                        equalsIgnoreCase(forumDetailModel.getForumsReplyModels().get(i).getUserid()) ||
                        Utility.getSharedPrefStringData(mParent, Constants.PREF_KEY_ROLE_NAME).
                                equalsIgnoreCase("Super Admin")) {
                    tv_report_icon.setText(Utility.getResourcesString(getActivity(), R.string.delete_icon));
                } else {
                    tv_report_icon.setText(Utility.getResourcesString(getActivity(), R.string.report_problem));
                }
                tv_comment_name.setText(forumDetailModel.getForumsReplyModels().get(i).getUsername());
                tv_comment_date.setText(Utility.getCommunityDateTime(forumDetailModel.getForumsReplyModels().get(i).getRecordeddate()));
                tv_comment_details.setText(Utility.capitalizeFirstLetter(forumDetailModel.getForumsReplyModels().get(i).getContent()));

                tv_comment_name.setTypeface(typefaceLatoRegular);
                tv_comment_date.setTypeface(typefaceLatoRegular);
                tv_comment_details.setTypeface(typefaceLatoRegular);
                tv_report_icon.setTypeface(typefaceMaterialRegular);

                if (mFrom.equalsIgnoreCase(AskForHelpFragment.TAG) || mFrom.equalsIgnoreCase(AskForHelpMyModerateListFragment.TAG)) {
                    tv_report_icon.setVisibility(View.GONE);
                } else {
                    tv_report_icon.setVisibility(View.VISIBLE);
                }

                if (!Utility.isValueNullOrEmpty(forumDetailModel.getForumsReplyModels().get(i).getImage())) {
                    img_comment.setVisibility(View.VISIBLE);
                    Utility.URLProfilePicLoading(img_comment,
                            forumDetailModel.getForumsReplyModels().get(i).getImage(), null, R.drawable.place_holder);
                } else {
                    img_comment.setVisibility(View.GONE);
                }
                tv_report_icon.setId(i);
                tv_report_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = view.getId();
                        if (Utility.getSharedPrefStringData(mParent, Constants.PREF_KEY_UUID).
                                equalsIgnoreCase(forumDetailModel.getForumsReplyModels().get(position).getUserid()) ||
                                Utility.getSharedPrefStringData(mParent, Constants.PREF_KEY_ROLE_NAME).
                                        equalsIgnoreCase("Super Admin")) {
                            deletePost(forumDetailModel.getForumsReplyModels().get(position).getId());
                        } else {
                            reportPost(forumDetailModel.getForumsReplyModels().get(position).getId());
                        }
                    }
                });

                ll_reply.addView(comment_item);
            }
        }
    }

    private void deletePost(String id) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        String mFinalUrl = "";
        if (mFrom.equalsIgnoreCase(AskForHelpMyModerateListFragment.TAG)) {
            mFinalUrl = APIConstants.ASK_HELPS_DELETE_REPLY + publicForumModel.getId() + "/" + id;
        } else if (mFrom.equalsIgnoreCase(AskForHelpFragment.TAG)) {
            mFinalUrl = APIConstants.ASK_HELPS_DELETE_REPLY + publicForumModel.getId() + "/" + id;
        } else {
            mFinalUrl = APIConstants.ASK_HELPS_DELETE_REPLY + publicForumModel.getId() + "/" + id;
        }
        ReportDeleteParser mDeleteParser = new ReportDeleteParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                mFinalUrl,
                paramMap,
                APIConstants.REQUEST_TYPE.GET,
                this, mDeleteParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_camera_icon:
                showCommentDialog();
                break;
            case R.id.tv_delete_icon:
                deletePost();
                break;
            case R.id.txt_send_icon:
                if (isValidFields()) {
                    postComment(edt_comment.getText().toString(), null);
                    edt_comment.setText("");
                }
                break;
        }
    }

    private void reportPost(String id) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        String mFinalUrl = "";
        if (mFrom.equalsIgnoreCase(AskForHelpMyModerateListFragment.TAG)) {
            mFinalUrl = APIConstants.ASK_HELPS_REPORT_ABUSE + publicForumModel.getId() + "/" + id;
        } else if (mFrom.equalsIgnoreCase(AskForHelpFragment.TAG)) {
            mFinalUrl = APIConstants.ASK_HELPS_REPORT_ABUSE + publicForumModel.getId() + "/" + id;
        } else {
            mFinalUrl = APIConstants.ASK_HELPS_REPORT_ABUSE + publicForumModel.getId() + "/" + id;
        }
        ReportAbuseParser mDeleteParser = new ReportAbuseParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                mFinalUrl,
                paramMap,
                APIConstants.REQUEST_TYPE.GET,
                this, mDeleteParser);
        Utility.execute(serverIntractorAsync);
    }

    private void deletePost() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        String mFinalUrl = "";
        if (mFrom.equalsIgnoreCase(AskForHelpMyModerateListFragment.TAG)) {
            mFinalUrl = APIConstants.ASK_HELPS_LIST_FOR_HELP_DELETE + publicForumModel.getId();
        } else if (mFrom.equalsIgnoreCase(AskForHelpFragment.TAG)) {
            mFinalUrl = APIConstants.ASK_MY_HELP_DELETE + publicForumModel.getId();
        } else {
            mFinalUrl = APIConstants.DELETE_TOPIC_FORUM + publicForumModel.getId();
        }
        DeleteParser mDeleteParser = new DeleteParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                mFinalUrl,
                paramMap,
                APIConstants.REQUEST_TYPE.GET,
                this, mDeleteParser);
        Utility.execute(serverIntractorAsync);
    }

    private void showCommentDialog() {
        final Dialog mDialog = new Dialog(mParent);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.comment_browse_layout);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(true);

        TextView tv_comment = (TextView) mDialog.findViewById(R.id.tv_comment);
        tv_comment.setTypeface(typefaceLatoRegular);

        edt_reply_comment = (EditText) mDialog.findViewById(R.id.edt_reply_comment);
        edt_reply_comment.setTypeface(typefaceLatoRegular);

        edt_upload_image = (EditText) mDialog.findViewById(R.id.edt_upload_image);
        edt_upload_image.setTypeface(typefaceLatoRegular);

        Button btn_upload = (Button) mDialog.findViewById(R.id.btn_upload);
        btn_upload.setTypeface(typefaceLatoRegular);

        Button btn_cancel = (Button) mDialog.findViewById(R.id.btn_cancel);
        btn_cancel.setTypeface(typefaceLatoRegular);
        Button btn_submit_small = (Button) mDialog.findViewById(R.id.btn_submit_small);
        btn_submit_small.setTypeface(typefaceLatoRegular);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });

        btn_submit_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidReplyFields()) {
                    postComment(edt_reply_comment.getText().toString(), mSelectedFile);
                    mDialog.dismiss();
                }
            }
        });

        mDialog.show();
    }

    private void pickFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mParent.startActivityForResult(Intent.createChooser(intent, "Select Reply logo"), Constants.FROM_REPLY_ID);
    }

    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(edt_comment.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, edt_comment, "Please enter your comment");
            edt_comment.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    private boolean isValidReplyFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(edt_reply_comment.getText().toString().trim())) {
            Utility.showToastMessage(mParent, "Please enter your comment");
            edt_comment.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_upload_image.getText().toString().trim())) {
            Utility.showToastMessage(mParent, "Please upload image");
            edt_upload_image.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    private void postComment(String content, File file) {
        if (!Utility.isValueNullOrEmpty(mID)) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
            paramMap.put("api_key", Constants.API_KEY_VALUE);
            paramMap.put("content", content);
            if (file != null) {
                paramMap.put("image", Utility.convertFileToByteArray(file));
                paramMap.put("image_name", file.getName());
            }
            String mFinalUrl = "";
            if (mFrom.equalsIgnoreCase(AskForHelpMyModerateListFragment.TAG)) {
                mFinalUrl = APIConstants.ASK_HELPS_LIST_FOR_HELP_REPLY + publicForumModel.getId();
            } else if (mFrom.equalsIgnoreCase(AskForHelpFragment.TAG)) {
                mFinalUrl = APIConstants.ASK_MY_HELP_REPLY + publicForumModel.getId();
            } else {
                mFinalUrl = APIConstants.POST_REPLY_FORUM + publicForumModel.getId();
            }

            JobPostingParser mJobPostingParser = new JobPostingParser();
            ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                    Utility.getResourcesString(getActivity(),
                            R.string.please_wait), true,
                    mFinalUrl,
                    paramMap,
                    APIConstants.REQUEST_TYPE.POST,
                    this, mJobPostingParser);
            Utility.execute(serverIntractorAsync);
        }
    }

    @Override
    public void updateFile(String path) {
        mSelectedFile = new File(path);
        edt_upload_image.setText(mSelectedFile.getName());
    }
}

