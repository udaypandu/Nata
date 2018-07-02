package nata.com.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.squareup.picasso.Picasso;

import nata.com.asynctask.IAsyncCaller;
import nata.com.customviews.CircleTransform;
import nata.com.customviews.FilePath;
import nata.com.fragments.AccountSettingsFragment;
import nata.com.fragments.AskForHelpMyModeratePostForumFragment;
import nata.com.fragments.ContactDetailsNewFragment;
import nata.com.fragments.FindJobsFragment;
import nata.com.fragments.ForumDetailFragment;
import nata.com.fragments.HomeFragment;

import nata.com.fragments.PostAskForHelpFragment;
import nata.com.fragments.PostForumFragment;
import nata.com.fragments.ViewProfileFragment;
import nata.com.fragments.WebViewFragment;
import nata.com.models.Model;
import nata.com.models.RegistrationModel;
import nata.com.nata.R;
import nata.com.utility.Constants;
import nata.com.utility.Utility;


public class DashboardActivity extends BaseActivity implements IAsyncCaller {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_dashboard);
        initUI();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(this, Constants.PREF_KEY_IS_APP_SIGNIN_OR_SIGNUP))) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setHomeButtonEnabled(true); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(true); // remove the icon
        } else {
            getSupportActionBar().setHomeButtonEnabled(false); // disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false); // remove the icon
        }
        initNavigationDrawer();
    }

    public void initNavigationDrawer() {

        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, DashboardActivity.this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //navigationView.setItemIconTintList(null);

        if (Utility.getSharedPrefStringData(this, Constants.PREF_KEY_ROLE_NAME).
                equalsIgnoreCase("End User")) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.administration).setVisible(false);
        } else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.administration).setVisible(true);
        }
        if (Utility.getSharedPrefStringData(this, Constants.IS_FB_LOGIN).equalsIgnoreCase(Constants.NATA)) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.accounts_settings).setVisible(true);
        } else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.accounts_settings).setVisible(false);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.homepage:
                        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
                                    .getBackStackEntryAt(
                                            getSupportFragmentManager()
                                                    .getBackStackEntryCount() - 1);
                            String tagName = backEntry.getName();
                            if (!tagName.equals(HomeFragment.TAG)) {
                                Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, DashboardActivity.this);
                            }
                        }
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.administration:
                        //Utility.navigateDashBoardFragment(new AdministrationFragment(), AdministrationFragment.TAG, null, DashboardActivity.this);
                        Bundle bundleWeb = new Bundle();
                        if (Utility.getSharedPrefStringData(DashboardActivity.this, Constants.PREF_KEY_ROLE_NAME).
                                equalsIgnoreCase("Super Admin")) {
                            bundleWeb.putString("URL", Constants.URL);
                        } else {
                            bundleWeb.putString("URL", Utility.getSharedPrefStringData(DashboardActivity.this, Constants.ADMIN_LINK));
                        }
                        Utility.navigateDashBoardFragment(new WebViewFragment(), WebViewFragment.TAG, bundleWeb, DashboardActivity.this);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.profile:
                        Utility.navigateDashBoardFragment(new ViewProfileFragment(), ViewProfileFragment.TAG, null, DashboardActivity.this);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.accounts_settings:
                        Utility.navigateDashBoardFragment(new AccountSettingsFragment(), AccountSettingsFragment.TAG, null, DashboardActivity.this);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        logout();
                        break;
                    case R.id.New_homepage:
                        Utility.navigateDashBoardFragment(new FindJobsFragment(),FindJobsFragment.TAG,null,DashboardActivity.this);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        ImageView img_user_image = (ImageView) header.findViewById(R.id.img_user_image);
        TextView txt_name = (TextView) header.findViewById(R.id.txt_name);
        TextView txt_user_designation = (TextView) header.findViewById(R.id.txt_user_designation);
        txt_user_designation.setText(Utility.getSharedPrefStringData(DashboardActivity.this, Constants.PREF_KEY_ROLE_NAME));
        txt_name.setText(Utility.capitalizeFirstLetter(Utility.getSharedPrefStringData(DashboardActivity.this, Constants.PREF_KEY_FIRST_NAME)) + " "
                + Utility.capitalizeFirstLetter(Utility.getSharedPrefStringData(DashboardActivity.this, Constants.PREF_KEY_LAST_NAME)));
        if (!Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(DashboardActivity.this, Constants.PREF_KEY_PHOTO)))
            Picasso.with(this).load(Utility.getSharedPrefStringData(DashboardActivity.this, Constants.PREF_KEY_PHOTO)).transform(new CircleTransform())
                    .placeholder(R.drawable.place_holder).into(img_user_image);

        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        final View.OnClickListener originalToolbarListener = actionBarDrawerToggle.getToolbarNavigationClickListener();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();
                        }
                    });
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(originalToolbarListener);
                }
            }
        });

    }

    private void logout() {

        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_UUID, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_EMAIL, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_ROLE, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_IS_APP_SIGNIN_OR_SIGNUP, "");
        Utility.setSharedPrefStringData(this, Constants.LOGIN_SESSION_ID, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_LOGIN_NAME, "");
        Utility.setSharedPrefStringData(this, Constants.PREF_KEY_LOGIN_PASSWORD, "");
        Utility.setSharedPrefStringData(this, Constants.IS_FB_LOGIN, "");

        Utility.setSharedPrefBooleanData(this, Constants.GROUP_MODERATOR, false);
        Utility.setSharedPrefStringData(this, Constants.ADMIN_LINK, "");

        /*NAVIGATE TO LOGIN ACTIVITY*/
        navigateToDashBoard();
    }

    private void navigateToDashBoard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.FROM_FILE_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                if (path.contains(".pdf") || path.contains(".PDF")) {
                    //ApplyJobsFragment.getInstance().updateFile(path);
                } else {
                    Utility.showToastMessage(this, "Please select only pdf");
                }
            }
        }   else if (requestCode == Constants.FROM_CONTACT_DETAILS) {
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    String path = FilePath.getPath(this, selectedImageUri);
                    ContactDetailsNewFragment.getInstance().updateFile(path);
                }
        } else if (requestCode == Constants.FROM_POSTING_LOGO_FILE_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
               // JobPostingFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.FROM_EDIT_LOGO_FILE_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
               // MyPostsEditFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.FROM_POST_FORUM_GALLERY_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                PostForumFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.FROM_POST_ASK_FOR_HELP_GALLERY_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
               PostAskForHelpFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.FROM_POST_ASK_FOR_HELP_MODERATE_GALLERY_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                AskForHelpMyModeratePostForumFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.FROM_POST_FORUM_CAMERA_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get(Utility.getResourcesString(this, R.string.data));
                String selectedImgPath = Utility.saveBitmap(bmp);
               PostForumFragment.getInstance().updateFile(selectedImgPath);
            }
        } else if (requestCode == Constants.FROM_POST_ASK_FOR_HELP_MODERATE_CAMERA_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get(Utility.getResourcesString(this, R.string.data));
                String selectedImgPath = Utility.saveBitmap(bmp);
               AskForHelpMyModeratePostForumFragment.getInstance().updateFile(selectedImgPath);
            }
        } else if (requestCode == Constants.FROM_POST_ASK_FOR_HELP_CAMERA_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get(Utility.getResourcesString(this, R.string.data));
                String selectedImgPath = Utility.saveBitmap(bmp);
                PostAskForHelpFragment.getInstance().updateFile(selectedImgPath);
            }
        } else if (requestCode == Constants.FROM_REPLY_ID) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                ForumDetailFragment.getInstance().updateFile(path);
            }
        } else if (requestCode == Constants.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            Utility.showLog("statusCode : ", "statusCode " + statusCode);
            handleSignInResult(result);
        }
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Utility.showLog("TAG", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Utility.showLog("Logging Success", "Logging Success" + acct.getDisplayName() + " " + acct.getId() + " " + acct.getEmail());
            //updateUI(true);
            saveDetailsInDb(acct.getId(), acct.getEmail(), acct.getDisplayName());
        } else {
            // Signed out, show unauthenticated UI.
            Utility.showLog("Logging error", "Logging error");
        }
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
        paramMap.put("auth_type", "google");
        RegistrationParser mRegistrationParser = new RegistrationParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.REGISTER_URL, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mRegistrationParser);
        Utility.execute(serverIntractorAsync);*/
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra(Constants.REGISTRATION_EMAIL, email);
        intent.putExtra(Constants.AUTH_TOKEN, auth_token);
        intent.putExtra(Constants.AUTH_TYPE, "google");
        this.startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
                            .getBackStackEntryAt(
                                    getSupportFragmentManager()
                                            .getBackStackEntryCount() - 1);
                    String tagName = backEntry.getName();
                    if (!tagName.equals(HomeFragment.TAG)) {
                        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, null, DashboardActivity.this);
                    }
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry backEntry = getSupportFragmentManager()
                    .getBackStackEntryAt(
                            getSupportFragmentManager()
                                    .getBackStackEntryCount() - 1);
            String tagName = backEntry.getName();
            if (tagName.equals(HomeFragment.TAG)) {
                for (int i = 0; i < menu.size(); i++)
                    menu.getItem(i).setVisible(false);
            } else {
                for (int i = 0; i < menu.size(); i++)
                    menu.getItem(i).setVisible(true);
            }
        } else {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }
        return true;
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof RegistrationModel) {
                RegistrationModel registrationModel = (RegistrationModel) model;
                if (registrationModel.getStatus().equalsIgnoreCase("error")) {
                    Utility.showToastMessage(this, registrationModel.getMsg());
                    onBackPressed();
                } else {
                    Utility.showToastMessage(this, registrationModel.getMsg());
                    onBackPressed();
                }
            }
        }

    }
}
