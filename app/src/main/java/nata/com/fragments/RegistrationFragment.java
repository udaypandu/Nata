package nata.com.fragments;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import nata.com.activities.AskForInvitationActivity;
import nata.com.activities.DashboardActivity;
import nata.com.activities.RegistrationActivity;
import nata.com.nata.R;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "RegistrationFragment";
    private DashboardActivity mParent;
    private View rootView;


    private TextView im_join_us;
    private LinearLayout im_facebook;
    private LinearLayout im_google_plus;
    private LinearLayout ll_login;
    private TextView tv_fb_icon;
    private TextView tv_google_icon;

    private String mEmainId = "";
    private String mFaceBookUniqueId = "";

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();

        try {
            PackageInfo info = mParent.getPackageManager().getPackageInfo(
                    "com.kapuwelfare",
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
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(mParent)
                    .enableAutoManage(mParent /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.app_name));
        rootView = inflater.inflate(R.layout.fragment_janasena_registration, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {
        im_join_us = (TextView) rootView.findViewById(R.id.im_join_us);
        im_facebook = (LinearLayout) rootView.findViewById(R.id.im_facebook);
        im_google_plus = (LinearLayout) rootView.findViewById(R.id.im_google_plus);
        ll_login = (LinearLayout) rootView.findViewById(R.id.ll_login);
        tv_fb_icon = (TextView) rootView.findViewById(R.id.tv_fb_icon);
        tv_google_icon = (TextView) rootView.findViewById(R.id.tv_google_icon);


        tv_google_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));
        tv_fb_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));


        im_join_us.setOnClickListener(this);
        im_facebook.setOnClickListener(this);
        im_google_plus.setOnClickListener(this);
        ll_login.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_facebook:
                setUpFacebookLogin(true);
                break;
            case R.id.im_join_us:
                Intent ask_intent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(ask_intent);
                break;
            case R.id.im_google_plus:
                signIn();
                break;
            case R.id.ll_login:
                Utility.navigateTOLogin(getActivity());
                break;
        }
    }

    private void setUpFacebookLogin(boolean isLogin) {
        LoginManager.getInstance().logInWithReadPermissions(getActivity(),
                Collections.singletonList("email"));
        mParent.callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mParent.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String name = "";
                                    String email = "";
                                    if (object.has("name"))
                                        name = object.getString("name");
                                    if (object.has("email"))
                                        email = object.getString("email");
                                    mFaceBookUniqueId = object.getString("id");

                                    String token = loginResult.getAccessToken().getToken();
                                    Utility.showLog("name", "name" + name);
                                    Utility.showLog("token", "token" + token);
                                    saveDetailsInDb(mFaceBookUniqueId, email, name);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                if (error instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
            }
        });
    }

    /**
     * This method is used to save details in the DB
     */
    private void saveDetailsInDb(String auth_token, String email, String name) {
        /*LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("firstname", name);
        paramMap.put("lastname", "");
        paramMap.put("email", email);
        paramMap.put("auth_token", auth_token);
        paramMap.put("auth_type", "facebook");
        RegistrationParser mRegistrationParser = new RegistrationParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                R.string.please_wait), true,
                APIConstants.REGISTER_URL, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mRegistrationParser);
        Utility.execute(serverIntractorAsync);*/
        Intent intent = new Intent(mParent, RegistrationActivity.class);
        intent.putExtra(Constants.REGISTRATION_EMAIL, email);
        intent.putExtra(Constants.AUTH_TOKEN, auth_token);
        intent.putExtra(Constants.AUTH_TYPE, "facebook");
        mParent.startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mParent.startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

}
