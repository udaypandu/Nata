package nata.com.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import nata.com.activities.DashboardActivity;
import nata.com.customviews.CustomProgressDialogDefault;
import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by Shankar on 2/7/2017.
 */

public class TwitterFragment extends Fragment {
    public static final String TAG = TwitterFragment.class.getSimpleName();
    private DashboardActivity parent;
    private View view;
    private CustomProgressDialogDefault customProgressDialogDefault;

    WebView webView;
    private boolean loadingFinished = true;
    private boolean redirect = false;
    String url = "https://mobile.twitter.com/natatelugu?lang=en";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (DashboardActivity) getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_twitter, container, false);

        inITUI();
        return view;
    }

    private void inITUI() {

        webView = (WebView) view.findViewById(R.id.web_view);
        customProgressDialogDefault = new CustomProgressDialogDefault(getActivity());
        customProgressDialogDefault.showProgress("");

        webView.setBackgroundColor(Utility.getColor(parent, R.color.transparent));
        webView.setWebChromeClient(new WebChromeClient());
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.requestFocusFromTouch();
        webView.setHorizontalScrollBarEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new webViewClient());


         /*PLUG IN'S*/
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

        webView.loadUrl(url);


    }

    private class webViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (!loadingFinished)
                redirect = true;

            loadingFinished = false;
            if (url.endsWith(".pdf")) {
                String googleDocs = "https://docs.google.com/viewer?url=";
                view.loadUrl(googleDocs + url);
            } else
                view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            loadingFinished = false;
        }

        @Override
        public void onPageFinished(WebView view, final String url) {

            if (!redirect)
                loadingFinished = true;

            if (loadingFinished && !redirect) {
                 /*DISMISS THE LOADER*/
                customProgressDialogDefault.dismissProgress();
            } else
                redirect = false;

            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            AlertDialog.Builder builder = new AlertDialog.Builder(parent);
            builder.setMessage(Utility.getResourcesString(parent, R.string.notification_error_ssl_cert_invalid));
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();

    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
        webView.resumeTimers();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.loadUrl("about:blank");
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    if (webView != null)
                        webView.destroy();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 3000);
        webView = null;
    }

}
