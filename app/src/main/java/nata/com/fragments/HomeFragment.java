package nata.com.fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nata.com.activities.DashboardActivity;
import nata.com.activities.LoginActivity;
import nata.com.designes.MaterialDialog;
import nata.com.nata.R;
import nata.com.permisions.Permissions;
import nata.com.utility.Constants;
import nata.com.utility.Utility;


/**
 * Created by Shankar Pilli
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "HomeFragment";
    private DashboardActivity mParent;
    private View rootView;

    private ImageView view_not_registered;
    private TextView txt_sign_inn;
    private LinearLayout ll_sign_in;

    private RelativeLayout rl_party_updates;
    private RelativeLayout rl_raise_a_concern;
    private RelativeLayout rl_join_discussion;
    private RelativeLayout rl_our_force;

    private View view_sign_in;
    private ImageView img_manam_manakosam;
    private ImageView img_center;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.app_name));
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {
        view_not_registered = (ImageView) rootView.findViewById(R.id.view_not_registered);
        txt_sign_inn = (TextView) rootView.findViewById(R.id.txt_sign_inn);
        ll_sign_in = (LinearLayout) rootView.findViewById(R.id.ll_sign_in);
        view_sign_in = (View) rootView.findViewById(R.id.view_sign_in);
        img_center = (ImageView) rootView.findViewById(R.id.img_center);
        img_manam_manakosam = (ImageView) rootView.findViewById(R.id.img_manam_manakosam);
        if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(getActivity(), Constants.PREF_KEY_IS_APP_SIGNIN_OR_SIGNUP))) {
            view_not_registered.setVisibility(View.VISIBLE);
            txt_sign_inn.setVisibility(View.VISIBLE);
            ll_sign_in.setVisibility(View.VISIBLE);
            view_sign_in.setVisibility(View.VISIBLE);
            img_manam_manakosam.setVisibility(View.GONE);
        } else {
            view_not_registered.setVisibility(View.GONE);
            txt_sign_inn.setVisibility(View.GONE);
            ll_sign_in.setVisibility(View.GONE);
            view_sign_in.setVisibility(View.GONE);
            img_manam_manakosam.setVisibility(View.VISIBLE);
        }

        rl_party_updates = (RelativeLayout) rootView.findViewById(R.id.rl_party_updates);
        rl_raise_a_concern = (RelativeLayout) rootView.findViewById(R.id.rl_raise_a_concern);
        rl_join_discussion = (RelativeLayout) rootView.findViewById(R.id.rl_join_discussion);
        rl_our_force = (RelativeLayout) rootView.findViewById(R.id.rl_our_force);
        view_not_registered = (ImageView) rootView.findViewById(R.id.view_not_registered);
        txt_sign_inn = (TextView) rootView.findViewById(R.id.txt_sign_inn);


        // rl_careers.setTypeface(Utility.setTypeFace_Lato_Regular(this));

        rl_party_updates.setOnClickListener(this);
        rl_raise_a_concern.setOnClickListener(this);
        rl_join_discussion.setOnClickListener(this);
        rl_our_force.setOnClickListener(this);
        view_not_registered.setOnClickListener(this);
        txt_sign_inn.setOnClickListener(this);
        ll_sign_in.setOnClickListener(this);
        img_center.setOnClickListener(this);

        checkForRunTimePermissions();
    }

    private void checkForRunTimePermissions() {
        if (Utility.isMarshmallowOS()) {
            Permissions.getInstance().setActivity(getActivity());
            CheckForPermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_not_registered:
                Utility.navigateDashBoardFragment(new RegistrationFragment(), RegistrationFragment.TAG, null, mParent);
                break;
            case R.id.img_center:
                String url = "http://www.nata2018.org/";
                Intent intet = new Intent(Intent.ACTION_VIEW);
                intet.setData(Uri.parse(url));
                startActivity(intet);


                break;
            case R.id.txt_sign_inn:
            case R.id.ll_sign_in:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_party_updates:
                Utility.navigateDashBoardFragment(new PartyUpdatesTabBarFragment(), PartyUpdatesTabBarFragment.TAG, null, mParent);
                break;
            case R.id.rl_raise_a_concern:
                if (Utility.isLogin(getActivity())) {
                    Utility.navigateDashBoardFragment(new AskForHelpFragment(), AskForHelpFragment.TAG, null, mParent);
              } else {
                    Utility.navigateTOLogin(getActivity());
               }
                break;
            case R.id.rl_join_discussion:
               if (Utility.isLogin(getActivity())) {
                    Utility.navigateDashBoardFragment(new ForumTabFragment(), ForumTabFragment.TAG, null, mParent);
                } else {
                 Utility.navigateTOLogin(getActivity());
                }
                break;
            case R.id.rl_our_force:
               if (Utility.isLogin(getActivity())) {
                    Utility.navigateDashBoardFragment(new OurForceCountryFragment(), OurForceCountryFragment.TAG, null, mParent);
               } else {
                    Utility.navigateTOLogin(getActivity());
               }
                break;
        }
    }
    private void CheckForPermissions(final Context mContext, final String... mPermissions) {
        // A request for two permissions
        Permissions.getInstance().requestPermissions(new Permissions.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permissions.ResultSet resultSet) {

                if (!resultSet.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Utility.isMarshmallowOS()) {
            CheckForPermissions(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
}

