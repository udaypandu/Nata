package nata.com.asynctask;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;


import java.io.File;
import java.util.HashMap;

import nata.com.customviews.CustomProgressDialogDefault;
import nata.com.parsers.Parser;
import nata.com.utility.APIConstants;

/**
 * Created by Shankar Rao on 10/20/2016.
 */
public abstract class BaseAsynkTask extends AsyncTask<Void, Void, Integer> {
    protected AnimationDrawable Anim;
    protected CustomProgressDialogDefault mCustomProgressDialog = null;
    protected Context mContext;
    protected String mDialogMessage, mApiMessage;
    protected boolean mShowDialog;

    protected HashMap<String, String> mParams;
    protected APIConstants.REQUEST_TYPE mRequestType;
    protected IAsyncCaller caller;
    protected String mUrl;
    @SuppressWarnings("rawtypes")
    protected Parser parser;
    protected HashMap<String, File> mFileMap;
    protected LayoutInflater mLayoutInflater;

    /**
     *
     * @param context
     *            ,Context to show progress dialog
     * @param dialogMessage
     *            , Dialog message for progress dialog
     * @param showDialog
     *            , boolean varialble to show progress dialog or not
     * @param url
     *            , Url of the web service
     * @param mParamMap
     *            , HashMap of keys
     * @param requestType
     *            , Type of request(GET/POST)
     * @param caller
     *            , Caller activity which will recieve response
     * @param parser
     *            , JSON parser for the response
     */
    public BaseAsynkTask(Context context, String dialogMessage,
                         boolean showDialog, String url, HashMap<String, String> mParamMap,
                         APIConstants.REQUEST_TYPE requestType, IAsyncCaller caller, Parser parser) {

        this.mContext = context;
        this.mDialogMessage = dialogMessage;
        mShowDialog = showDialog;
        mParams = mParamMap;
        mRequestType = requestType;
        this.caller = caller;
        this.mUrl = url;
        this.parser = parser;
        mCustomProgressDialog = new CustomProgressDialogDefault(mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mShowDialog) {
            mCustomProgressDialog.showProgress("");
        }
    }

    /**
     * Abstract method for interaction with Web Service.
     */
    public abstract Integer doInBackground(Void... params);

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (mCustomProgressDialog != null) {
            mCustomProgressDialog.dismissProgress();
        }
    }
}