package nata.com.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.Model;
import nata.com.models.PersonalDetailsModel;
import nata.com.nata.R;
import nata.com.parsers.ProfileDetailsParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

public class LoginInformationFragment extends Fragment implements IAsyncCaller {

    private DashboardActivity mParent;
    public static String TAG = "LoginInformationFragment";
    private View rootView;

    private TextView tv_login_information;
    private TextView tv_email;
    private TextView tv_email_name;
    private TextView tv_other;
    private TextView tv_occupation;
    private TextView tv_occupation_value;
    private TextView tv_company;
    private TextView tv_company_value;

    private Typeface mLatoTypeface;

    public static LoginInformationFragment newInstance() {
        LoginInformationFragment mLoginInformationFragment = new LoginInformationFragment();
        return mLoginInformationFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        rootView = inflater.inflate(R.layout.fragment_login_information, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {
        mLatoTypeface = Utility.setTypeFace_Lato_Regular(getActivity());

        tv_login_information = (TextView) rootView.findViewById(R.id.tv_login_information);
        tv_email = (TextView) rootView.findViewById(R.id.tv_email);
        tv_email_name = (TextView) rootView.findViewById(R.id.tv_email_name);
        tv_other = (TextView) rootView.findViewById(R.id.tv_other);

        tv_occupation = (TextView) rootView.findViewById(R.id.tv_occupation);
        tv_occupation_value = (TextView) rootView.findViewById(R.id.tv_occupation_value);
        tv_company = (TextView) rootView.findViewById(R.id.tv_company);
        tv_company_value = (TextView) rootView.findViewById(R.id.tv_company_value);

        tv_login_information.setTypeface(mLatoTypeface);
        tv_email.setTypeface(mLatoTypeface);
        tv_email_name.setTypeface(mLatoTypeface);
        tv_other.setTypeface(mLatoTypeface);
        tv_occupation.setTypeface(mLatoTypeface);
        tv_occupation_value.setTypeface(mLatoTypeface);
        tv_company.setTypeface(mLatoTypeface);
        tv_company_value.setTypeface(mLatoTypeface);

        getPersonalData();
    }

    private void getPersonalData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        ProfileDetailsParser mProfileDetailsParser = new ProfileDetailsParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.GET_PROFILE, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mProfileDetailsParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof PersonalDetailsModel) {
                    PersonalDetailsModel mPersonalDetailsModel = (PersonalDetailsModel) model;
                    setPersonalDetails(mPersonalDetailsModel);
                }
            } else {
                Utility.showToastMessage(mParent, "OOPS..! Some problem with the API");
            }
        }
    }

    private void setPersonalDetails(PersonalDetailsModel mPersonalDetailsModel) {
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getEmail()))
            tv_email_name.setText(mPersonalDetailsModel.getEmail());
        else
            tv_email_name.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getOccu()))
            tv_occupation_value.setText(mPersonalDetailsModel.getOccu());
        else
            tv_occupation_value.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getCompany()))
            tv_company_value.setText(mPersonalDetailsModel.getCompany());
        else
            tv_company_value.setText("");
    }
}
