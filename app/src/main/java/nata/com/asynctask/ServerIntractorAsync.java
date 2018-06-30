package nata.com.asynctask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;



import java.util.HashMap;

import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.parsers.Parser;
import nata.com.utility.APIConstants;
import nata.com.utility.Utility;

/**
 * Created by ShankarRao on 3/28/2016.
 */
public class ServerIntractorAsync extends BaseAsynkTask {
    private String mResponse = null;
    private Model model;

    public ServerIntractorAsync(Context context, String dialogMessage,
                                boolean showDialog, String url, HashMap<String, String> mParamMap,
                                APIConstants.REQUEST_TYPE requestType, IAsyncCaller caller, Parser parser) {
        super(context, dialogMessage, showDialog, url, mParamMap, requestType,
                caller, parser);

    }

    @Override
    public Integer doInBackground(Void... params) {
        if (!Utility.isNetworkAvailable(mContext)) {
            return 0;
        }

        switch (mRequestType) {
            case GET:
                Utility.showLog("Request URL ", mUrl);
                if (mUrl.contains(APIConstants.COUNTRIES_URL)
                        || mUrl.contains(APIConstants.STATES_URL)
                        || mUrl.contains(APIConstants.DISTRICTS_URL)
                        || mUrl.contains(APIConstants.MANDALS_URL)
                        || mUrl.contains(APIConstants.VILLAGES_URL)
                        || mUrl.contains(APIConstants.GET_PROFESSIONS)
                        || mUrl.contains(APIConstants.CITIES_URL)
                        || mUrl.contains(APIConstants.GET_ID_PROOFS)
                        || mUrl.contains(APIConstants.GET_BLOOD_GROUPS)
                        ) {
                    mResponse = Utility.httpGetRequestToServer(Utility.getURL(mUrl, mParams));
                } else if (mUrl.contains(APIConstants.FIND_ALL_JOBS)
                        || mUrl.contains(APIConstants.JOBS_HOME_URL)
                        || mUrl.contains(APIConstants.NEWS_HOME_URL)
                        || mUrl.contains(APIConstants.GET_PROFILE)
                        || mUrl.contains(APIConstants.GET_OUR_FORCE_VILLAGE)
                        || mUrl.contains(APIConstants.GET_OUR_FORCE_DISTRICT)
                        || mUrl.contains(APIConstants.GET_OUR_FORCE_MANDAL)
                        || mUrl.contains(APIConstants.GET_OUR_FORCE_STATE)
                        || mUrl.contains(APIConstants.GET_MEMBERS)
                        || mUrl.contains(APIConstants.FORUM_ALL)
                        || mUrl.contains(APIConstants.PRIVATE_TOPICS_FORUM)
                        || mUrl.contains(APIConstants.PUBLIC_TOPICS_FORUM)
                        || mUrl.contains(APIConstants.GET_MEMBERS_FORUM)
                        || mUrl.contains(APIConstants.GET_MODERATORS_FORUM)
                        || mUrl.contains(APIConstants.IS_MEMBER_FORUM)
                        || mUrl.contains(APIConstants.TOPIC_FORUM)
                        || mUrl.contains(APIConstants.DELETE_TOPIC_FORUM)
                        || mUrl.contains(APIConstants.ASK_HELPS_LIST_FOR_HELP_DELETE)
                        || mUrl.contains(APIConstants.ASK_MY_HELP_DELETE)
                        || mUrl.contains(APIConstants.ASK_HELPS_LIST_FOR_MODERATERS)
                        || mUrl.contains(APIConstants.ASK_MY_HELP_LIST)
                        || mUrl.contains(APIConstants.ASK_MY_HELP_MODERATE_GROUPS)
                        || mUrl.contains(APIConstants.ASK_MY_HELP_GET_HELP_DETAIL)
                        || mUrl.contains(APIConstants.MY_APPLIED_JOBS_URL)
                        || mUrl.contains(APIConstants.MY_POSTS_DELETE_URL)
                        || mUrl.contains(APIConstants.MY_POSTED_JOBS_URL)
                        || mUrl.contains(APIConstants.ASK_HELPS_REPORT_ABUSE)
                        || mUrl.contains(APIConstants.ASK_HELPS_DELETE_REPLY)
                        ) {
                    mResponse = Utility.getWithHeader(Utility.getURL(mUrl, mParams), mContext);
                } else {
                    mResponse = Utility.GETHeader(mUrl, mContext);
                }
                if (mResponse != null) {

                    Utility.showLog("mResponse  ", mResponse);
                }
                return parseResposnse(mResponse);
            case POST:
                Utility.showLog("Request URL ", mUrl);
                Utility.showLog("Request mParams != null getParams  ", ""
                        + Utility.getParams(mParams));
                if (mUrl.contains(APIConstants.LOGIN) || mUrl.contains(APIConstants.REGISTER_URL)) {
                    mResponse = Utility.httpLoginCookiesPostRequest(mUrl,
                            Utility.getParams(mParams), mContext);
                } else if (mUrl.contains(APIConstants.APPLY_JOBS_URL)
                        || mUrl.contains(APIConstants.POST_JOB_URL)
                        || mUrl.contains(APIConstants.POST_TOPIC_FORUM)
                        || mUrl.contains(APIConstants.POST_REPLY_FORUM)
                        || mUrl.contains(APIConstants.ASK_MY_HELP_POST)
                        || mUrl.contains(APIConstants.UPDATE_DEVICE)
                        || mUrl.contains(APIConstants.ASK_MY_HELP_REPLY)
                        || mUrl.contains(APIConstants.MY_APPLIED_JOBS_UPDATE_JOB_STATUS)
                        || mUrl.contains(APIConstants.CHANGE_PASSWORD)
                        || mUrl.contains(APIConstants.ASK_HELPS_LIST_FOR_MODERATERS_POST)
                        || mUrl.contains(APIConstants.ASK_HELPS_LIST_FOR_HELP_REPLY)
                        || mUrl.contains(APIConstants.UPDATE_PROFILE)
                        || mUrl.contains(APIConstants.MY_POSTS_EDIT_URL)
                        ) {
                    mResponse = Utility.httpPostRequestToServerWithHeaderCookies(mUrl,
                            Utility.getParams(mParams), mContext);
                } else {
                    mResponse = Utility.httpPostRequestToServerWithHeader(mUrl,
                            Utility.getParams(mParams), mContext);
                }
                if (mResponse != null) {
                    Utility.showLog("mResponse  ", mResponse);
                } else {
                    Utility.showLog("mResponse  ", mResponse);
                }

                return parseResposnse(mResponse);
            default:
                return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        try {

            if (!isCancelled() && result == 1) {
                if (model != null) {
                    caller.onComplete(model);
                } else {
                    Utility.showToastMessage(mContext, "Server response error!");
                }
            } else if (result == 0) {
                Utility.showSettingDialog(
                        mContext,
                        mContext.getResources().getString(
                                R.string.no_internet_msg),
                        mContext.getResources().getString(
                                R.string.no_internet_title),
                        Utility.NO_INTERNET_CONNECTION).show();
                model = null;
                caller.onComplete(model);
            } else {
                model = null;
                caller.onComplete(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int parseResposnse(String response) {
        if (response != null) {

            return getResponse(response);

        }

        return -1;
    }

    private int getResponse(String response) {
        try {
            model = parser.parseResponse(response, mContext);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*
     *
	 * @Sparity
	 *
	 * These methods are to make asynctasks concurrent, and run on paralel on
	 * android 3+
	 */

    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task) {
        execute(task, (P[]) null);
    }

    @SuppressLint("NewApi")
    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task,
                                                                 P... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}

