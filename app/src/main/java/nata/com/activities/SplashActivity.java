package nata.com.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;

import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.LoginModel;
import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.parsers.LoginParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by Shankar Pilli on 20-10-2016.
 */
public class SplashActivity extends Activity implements IAsyncCaller {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_splash);
        // Utility.setTranslateStatusBar(this);

        Handler mSplashHandler = new Handler();
        Runnable action = new Runnable() {
            @Override
            public void run() {
               /* if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(SplashActivity.this, Constants.PREF_KEY_IS_APP_SIGNIN_OR_SIGNUP))) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {*/
                /*if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(SplashActivity.this, Constants.NUMBER_OF_APP_OPENS)) ||
                        Utility.getSharedPrefStringData(SplashActivity.this, Constants.NUMBER_OF_APP_OPENS).equalsIgnoreCase("2")) {
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.NUMBER_OF_APP_OPENS, "1");
                } else {
                    int mOpenCount = Integer.parseInt(Utility.getSharedPrefStringData(SplashActivity.this, Constants.NUMBER_OF_APP_OPENS));
                    Utility.setSharedPrefStringData(SplashActivity.this, Constants.NUMBER_OF_APP_OPENS, "" + (mOpenCount + 1));
                    Utility.showLog("mOpenCount", "mOpenCount : " + mOpenCount);
                }*/

                if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(SplashActivity.this, Constants.PREF_KEY_LOGIN_NAME))) {
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                   // generateFbKeyHash();
                    finish();
                } else {
                    callLogin();
                }
                //  }
            }
        };
        mSplashHandler.postDelayed(action, Constants.SPLASH_TIME_OUT);
    }

    private void callLogin() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("username", Utility.getSharedPrefStringData(this, Constants.PREF_KEY_LOGIN_NAME));
        paramMap.put("password", Utility.getSharedPrefStringData(this, Constants.PREF_KEY_LOGIN_PASSWORD));
        LoginParser mLoginParser = new LoginParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.LOGIN, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mLoginParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof LoginModel) {
                    LoginModel loginModel = (LoginModel) model;
                    if (loginModel.getStatus_msg().equalsIgnoreCase("failure")) {
                        logout();
                    } else {

                        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_IS_APP_SIGNIN_OR_SIGNUP, "done");
                        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_UUID, loginModel.getUuid());
                        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_EMAIL, loginModel.getEmail());
                        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_ROLE, loginModel.getRole());
                        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_ROLE_NAME, loginModel.getRole_name());
                        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_FIRST_NAME, loginModel.getFirst_name());
                        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_LAST_NAME, loginModel.getLast_name());
                        Utility.setSharedPrefStringData(this, Constants.LOGIN_SESSION_ID, loginModel.getCi_session());

                        Utility.setSharedPrefBooleanData(this, Constants.GROUP_MODERATOR, loginModel.isGroup_moderator());
                        Utility.setSharedPrefStringData(this, Constants.ADMIN_LINK, loginModel.getAdmin_link());

                        if (loginModel.getPhoto() != null)
                            Utility.setSharedPrefStringData(this, Constants.PREF_KEY_PHOTO, loginModel.getPhoto());
                        else
                            Utility.setSharedPrefStringData(this, Constants.PREF_KEY_PHOTO, "");

                        Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }
    }

    private void logout() {

        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_UUID, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_EMAIL, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_ROLE, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_IS_APP_SIGNIN_OR_SIGNUP, "");
        Utility.setSharedPrefStringData(this, Constants.LOGIN_SESSION_ID, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_LOGIN_NAME, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_LOGIN_PASSWORD, "");

        /*NAVIGATE TO LOGIN ACTIVITY*/
        navigateToDashBoard();
    }

    private void generateFbKeyHash() {
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String s = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Utility.showLog("Key Hash", "" + s);
                Log.d("KeyHash:", s);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void navigateToDashBoard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        this.finish();
    }
}