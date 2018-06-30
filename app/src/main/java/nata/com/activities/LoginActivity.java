package nata.com.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Collections;
import java.util.LinkedHashMap;

import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.designes.MaterialDialog;
import nata.com.location.FetchAddressIntentService;
import nata.com.models.JobPostingModel;
import nata.com.models.LoginModel;
import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.parsers.JobPostingParser;
import nata.com.parsers.LoginParser;
import nata.com.permisions.Permissions;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by shankar on 2/4/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, IAsyncCaller,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private EditText edt_username;
    private EditText edt_password;
    private TextView txt_get_started;
    private LinearLayout ll_create_account;
    private TextView txt_forgot_password;
    private TextView txt_register;
    private TextView tv_show;
    private TextView tv_fb_icon;
    private TextView tv_google_icon;


    private LinearLayout im_google_plus;
    private LinearLayout im_facebook;
    public CallbackManager callbackManager;

    protected boolean mAddressRequested = false;


    /**
     * Provides the entry point to Google Play services.
     */

  //  private AddressResultReceiver mResultReceiver;

    protected GoogleApiClient mGoogleApiClient;
    protected GoogleApiClient mMGoogleApiClient;
    protected LocationRequest mLocationRequest;
    private AddressResultReceiver mResultReceiver;

    private String location = "";
    protected Location mCurrentLocation;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login_janasena);
        initUI();
        mResultReceiver = new AddressResultReceiver(new Handler());
        buildGoogleApiClient();
        buildLocationSettingsRequest();
        if (Utility.isMarshmallowOS()) {
            getLocationPermission();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mMGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
               .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
             .build();
    }

  protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    protected void buildLocationSettingsRequest() {
        if (Utility.isMarshmallowOS()) {
            PackageManager pm = this.getPackageManager();
            int hasWritePerm = pm.checkPermission(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    this.getPackageName());
            if (hasWritePerm == PackageManager.PERMISSION_GRANTED) {
                Utility.showLog("Setting Location update", "Setting Location update");
                createLocationRequest();
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
                builder.addLocationRequest(mLocationRequest);
                mLocationSettingsRequest = builder.build();
                startLocationUpdates();
            }
        }
    }

    private void getLocationPermission() {
        Permissions.getInstance().setActivity(this);
        CheckForPermissions(this,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET);
    }

    private void CheckForPermissions(final Context mContext, final String... mPermissions) {
        // A request for two permissions
        Permissions.getInstance().requestPermissions(new Permissions.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permissions.ResultSet resultSet) {

                if (!resultSet.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    final MaterialDialog denyDialog = new MaterialDialog(mContext, Permissions.TITLE,
                            Permissions.MESSAGE);
                    //Positive
                    denyDialog.setAcceptButton("RE-TRY");
                    denyDialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckForPermissions(mContext, mPermissions);
                        }
                    });
                    denyDialog.show();
                }
            }

            @Override
            public void onRationaleRequested(Permissions.IOnRationaleProvided callback, String... permissions) {
                Permissions.getInstance().showRationaleInDialog(Permissions.TITLE,
                        Permissions.MESSAGE, "RE-TRY", callback);
            }
        }, mPermissions);
    }


    private void startLocationUpdates() {
        LocationServices.SettingsApi.checkLocationSettings(
                mGoogleApiClient,
                mLocationSettingsRequest
        ).setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("", "All location settings are satisfied.");
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                mGoogleApiClient, mLocationRequest, LoginActivity.this);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("", "Location settings are not satisfied. Attempting to upgrade " +
                                "location settings ");
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            status.startResolutionForResult(LoginActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        String errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in Settings.";
                        Log.e("", errorMessage);
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            getCityAndCountryData();
        }
        if (mCurrentLocation != null) {
            Utility.showLog("Lat", "" + mCurrentLocation.getLatitude());
            Utility.showLog("Lng", "" + mCurrentLocation.getLongitude());
        } else {
            //Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
    }

    private void getCityAndCountryData() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mCurrentLocation);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Utility.showLog("requestCode", "" + requestCode);
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createLocationRequest();
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
                    builder.addLocationRequest(mLocationRequest);
                    mLocationSettingsRequest = builder.build();
                    startLocationUpdates();
                    getCityAndCountryData();
                }
                return;
            }
        }
    }

    private void initUI() {
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        txt_get_started = (TextView) findViewById(R.id.txt_get_started);
        ll_create_account = (LinearLayout) findViewById(R.id.ll_create_account);
        txt_forgot_password = (TextView) findViewById(R.id.txt_forgot_password);
        txt_register = (TextView) findViewById(R.id.txt_register);
        tv_show = (TextView) findViewById(R.id.tv_show);
        tv_fb_icon = (TextView) findViewById(R.id.tv_fb_icon);
        tv_google_icon = (TextView) findViewById(R.id.tv_google_icon);

        im_google_plus = (LinearLayout) findViewById(R.id.im_google_plus);
        im_facebook = (LinearLayout) findViewById(R.id.im_facebook);

        tv_google_icon.setTypeface(Utility.setTypeFace_fontawesome(this));
        tv_fb_icon.setTypeface(Utility.setTypeFace_fontawesome(this));


        txt_get_started.setOnClickListener(this);
        ll_create_account.setOnClickListener(this);
        txt_forgot_password.setOnClickListener(this);
        im_google_plus.setOnClickListener(this);
        im_facebook.setOnClickListener(this);
        txt_register.setOnClickListener(this);
        tv_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_forgot_password:
                Intent intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_create_account:
                Intent ask_intent = new Intent(this, RegistrationActivity.class);
                startActivity(ask_intent);
                break;
            case R.id.txt_register:
                Intent register = new Intent(this, RegistrationActivity.class);
                startActivity(register);

                break;

            case R.id.txt_get_started:
                if (isValidFields()) {
                    loginData();
                }
                break;
            case R.id.im_google_plus:
                signIn();
                break;
            case R.id.im_facebook:
                setUpFacebookLogin(true);
                break;

            case R.id.tv_show:
                if (tv_show.getText().toString().equalsIgnoreCase("Show")) {
                    tv_show.setText("Hide");
                    edt_password.setTransformationMethod(null);
                } else {
                    tv_show.setText("Show");
                    edt_password.setTransformationMethod(new PasswordTransformationMethod());
                }
                    break;
        }
    }

    private void setUpFacebookLogin(boolean b) {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Collections.singletonList("email"));
        LoginActivity.this.callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(LoginActivity.this.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String id = object.getString("id");

                                    String token = loginResult.getAccessToken().getToken();
                                    Utility.showLog("name", "name" + name);
                                    Utility.showLog("token", "token" + token);
                                    loginAuth(id, email, "facebook");
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

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mMGoogleApiClient);
        LoginActivity.this.startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //Utility.showLog("TAG", "handleSignInResult:" + result.isSuccess());
        if (result != null && result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Utility.showLog("Logging Success", "Logging Success" + acct.getDisplayName() + " " + acct.getId() + " " + acct.getEmail());
            //updateUI(true);
            loginAuth(acct.getId(), acct.getEmail(), "google");
        } else {
            Utility.showLog("Logging error", "Logging error");
        }
    }

    private void loginAuth(String id, String email, String type) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("username", email);
        paramMap.put("auth_type", type);
        paramMap.put("auth_token", id);
        LoginParser mLoginParser = new LoginParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.LOGIN, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mLoginParser);
        Utility.execute(serverIntractorAsync);
    }


    /**
     * Fetch user Location
     * Navigate to DashBoardActivity
     **/
    protected void getCurrentLocation() {
        if (Utility.isLocationEnabled(this)) {
            if (Utility.isNetworkAvailable(this)) {
                getCurrentAddress();
            } else {
                Utility.showSettingDialog(
                        this,
                        Utility.getResourcesString(this,
                                R.string.no_internet_msg),
                        Utility.getResourcesString(this,
                                R.string.no_internet_title),
                        Utility.NO_INTERNET_CONNECTION).show();
            }
        } else {
            createLocationRequest();
        }
    }

    private void getCurrentAddress() {
        Utility.showLog("getCurrentAddress", "getCurrentAddress");
        mAddressRequested = true;
        mGoogleApiClient.connect();
        //checkPermissionsAndCallServiceToGetCityBasedOnLatAndLng();
    }


    private void loginData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("username", edt_username.getText().toString());
        paramMap.put("password", edt_password.getText().toString());
        LoginParser mLoginParser = new LoginParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.LOGIN, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mLoginParser);
        Utility.execute(serverIntractorAsync);

        Utility.showLog("USER NAME", "" + edt_username.getText().toString());
        Utility.showLog("PASSWORD ", "" + edt_password.getText().toString());

    }

    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(edt_username.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_username, "Please enter Email ID ");
            edt_username.requestFocus();
        } else if (!edt_username.getText().toString().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$")) {
            Utility.setSnackBarEnglish(this, edt_username, "Please enter valid user email");
            edt_username.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_password.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_password, "Please enter Password");
            edt_password.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof LoginModel) {
                    LoginModel loginModel = (LoginModel) model;
                    if (loginModel.getStatus_msg().equalsIgnoreCase("failure")) {
                        Utility.showToastMessage(LoginActivity.this, loginModel.getMessage());
                    } else {
                        // Utility.showToastMessage(this, loginModel.getMessage());
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

                        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_LOGIN_NAME, edt_username.getText().toString());
                        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_LOGIN_PASSWORD, edt_password.getText().toString());

                        if (loginModel.getPhoto() != null)
                            Utility.setSharedPrefStringData(this, Constants.PREF_KEY_PHOTO, loginModel.getPhoto());
                        else
                            Utility.setSharedPrefStringData(this, Constants.PREF_KEY_PHOTO, "");
                        /*Intent intent = new Intent(this, com.kapuwelfare.activities.DashboardActivity.class);
                        startActivity(intent);
                        finish();*/
                        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(LoginActivity.this, Constants.DEVICE_TOKEN))) {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                                Intent intent = new Intent(this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            if (mGoogleApiClient != null)
                                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                            updateDeviceData();
                        } else {
                            Intent intent = new Intent(this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else if (model instanceof JobPostingModel) {
                    Intent intent = new Intent(this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    private void updateDeviceData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("device_type", Constants.DEVICE_TYPE);
        paramMap.put("token", Utility.getSharedPrefStringData(LoginActivity.this, Constants.DEVICE_TOKEN));
        if (mCurrentLocation != null)
            paramMap.put("latitude", "" + mCurrentLocation.getLatitude());
        else
            paramMap.put("latitude", "0.0");
        if (mCurrentLocation != null)
            paramMap.put("longitude", "" + mCurrentLocation.getLongitude());
        else
            paramMap.put("longitude", "0.0");
        paramMap.put("city", location);

        JobPostingParser mJobPostingParser = new JobPostingParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.UPDATE_DEVICE, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mJobPostingParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        //updateDeviceData();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
            case Constants.RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
                break;
        }
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            location = resultData.getString(Constants.RESULT_DATA_KEY);
            Utility.showLog("Location Address", "location" + location);
        }
    }
}
