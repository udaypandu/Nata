package nata.com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import nata.com.activities.DashboardActivity;
import nata.com.nata.R;
import nata.com.utility.Utility;


public class WebViewFragment extends Fragment {

    public static final String TAG = "WebViewFragment";
    private WebView webView;
    private DashboardActivity mParent;
    private Toolbar mToolbar;
    private View rootView;


    private String mTitle;
    private String url;
    private RelativeLayout rl_status_bar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        url = getArguments().getString("URL");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View view) {


        if (url != null && !url.equals("") && !url.equals("null") && url.length() > 0) {
            Utility.showProgressDialog(mParent, "Loading...", false);
            webView = (WebView) view.findViewById(R.id.webview);
            webView.setBackgroundColor(0x00000000);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setDomStorageEnabled(true);
            webView.setVerticalScrollBarEnabled(true);
            webView.setHorizontalScrollBarEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setSupportMultipleWindows(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.setVerticalScrollBarEnabled(true);
            webView.setHorizontalScrollBarEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view,
                                                        String url) {
                    if (url.endsWith(".pdf")) {
                        String googleDocs = "https://docs.google.com/viewer?url=";
                        view.loadUrl(googleDocs + url);
                    } else {
                        if (mParent.progressDialog != null) {
                            Utility.showProgressDialog(mParent, "Loading...", false);
                        }
                        view.loadUrl(url);
                    }
                    return true;
                }


                @Override
                public void onPageFinished(WebView view, final String url) {
                    super.onPageFinished(view, url);
                    if (mParent.progressDialog != null)
                        mParent.progressDialog.dismiss();
                }
            });

            if (url.endsWith(".pdf")) {
                String googleDocs = "https://docs.google.com/viewer?url=";
                webView.loadUrl(googleDocs + url);
            } else
                webView.loadUrl(url);

            webView.setWebChromeClient(new WebChromeClient());
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers(); //careful with this! Pauses all layout,
        //parsing, and JavaScript timers for all WebViews.
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
                    webView.destroy();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 3000);
        webView = null;
    }

}