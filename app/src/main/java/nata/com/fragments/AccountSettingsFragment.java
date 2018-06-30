package nata.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.AccountSettingModel;
import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.parsers.AccountSettingParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by Shankar Pilli on 20/03/2017
 */
public class AccountSettingsFragment extends Fragment implements View.OnClickListener, IAsyncCaller {

    public static final String TAG = "AccountSettingsFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView tv_email;
    private TextView tv_email_original;
    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_confirm_new_password;
    private Button bt_update;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(),
                R.string.accounts_settings).toUpperCase());
        rootView = inflater.inflate(R.layout.fragment_account_settings, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        tv_email = (TextView) rootView.findViewById(R.id.tv_email);
        tv_email_original = (TextView) rootView.findViewById(R.id.tv_email_original);
        et_old_password = (EditText) rootView.findViewById(R.id.et_old_password);
        et_new_password = (EditText) rootView.findViewById(R.id.et_new_password);
        et_confirm_new_password = (EditText) rootView.findViewById(R.id.et_confirm_new_password);
        bt_update = (Button) rootView.findViewById(R.id.bt_update);

        tv_email.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        tv_email_original.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        tv_email_original.setText(Utility.getSharedPrefStringData(getActivity(), Constants.PREF_KEY_EMAIL));
        et_old_password.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        et_new_password.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        et_confirm_new_password.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        bt_update.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        bt_update.setOnClickListener(this);
    }



    private boolean isValidateFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(et_old_password.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, et_old_password, "Please enter old password");
            et_old_password.requestFocus();
        } else if (Utility.isValueNullOrEmpty(et_new_password.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, et_new_password, "Please enter new password");
            et_new_password.requestFocus();
        } else if (et_new_password.getText().toString().trim()
                .equalsIgnoreCase(et_old_password.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, et_new_password, "Old password and New password are same");
            et_confirm_new_password.requestFocus();
        } else if (Utility.isValueNullOrEmpty(et_confirm_new_password.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, et_confirm_new_password, "Please enter confirm password");
            et_confirm_new_password.requestFocus();
        } else if (!et_new_password.getText().toString().trim()
                .equalsIgnoreCase(et_confirm_new_password.getText().toString().trim())) {
            Utility.setSnackBarEnglish(mParent, et_confirm_new_password, "New password and Confirm password not same");
            et_confirm_new_password.requestFocus();
        } /*else if (et_new_password.getText().toString().trim().length()<6) {
            Utility.setSnackBarEnglish(mParent, et_new_password, "New password should be minimum 6 characters");
            et_new_password.requestFocus();
        } */ else {
            isValidated = true;
        }
        return isValidated;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_update:
                if (isValidateFields()) {
                    changePassword();
                }
                break;
        }
    }

    private void changePassword() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("current_password", et_old_password.getText().toString().trim());
        paramMap.put("new_password", et_new_password.getText().toString().trim());
        paramMap.put("confirm_password", et_confirm_new_password.getText().toString().trim());
        AccountSettingParser mAccountSettingParser = new AccountSettingParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.CHANGE_PASSWORD, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mAccountSettingParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof AccountSettingModel) {
                AccountSettingModel mAccountSettingModel = (AccountSettingModel) model;
                if (mAccountSettingModel.isStatus()) {
                    setStatus(mAccountSettingModel);
                } else {
                    Utility.showToastMessage(mParent, mAccountSettingModel.getMessage());
                }
            }
        } else {
            Utility.showToastMessage(mParent, "OOPS..! Some problem with the API");
        }
    }

    private void setStatus(AccountSettingModel mAccountSettingModel) {
        Utility.showToastMessage(getActivity(), mAccountSettingModel.getMessage());
        et_old_password.setText("");
        et_confirm_new_password.setText("");
        et_new_password.setText("");
    }
}

