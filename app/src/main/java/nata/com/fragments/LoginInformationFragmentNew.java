package nata.com.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.SpinnerAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.JobPostingModel;
import nata.com.models.LoginModel;
import nata.com.models.Model;
import nata.com.models.PersonalDetailsModel;
import nata.com.models.ProfessionModel;
import nata.com.models.SpinnerModel;
import nata.com.nata.R;
import nata.com.parsers.JobPostingParser;
import nata.com.parsers.LoginParser;
import nata.com.parsers.ProfessionParser;
import nata.com.parsers.ProfileDetailsParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginInformationFragmentNew extends Fragment implements IAsyncCaller, View.OnClickListener {

    public static String TAG = "LoginInformationFragmentNew";
    private DashboardActivity mParent;
    private View rootView;

    private TextView tv_login_information;
    private EditText edt_email;

    private TextView tv_other;
    private static EditText edt_occupation;
    private EditText edt_company;

    private TextView tv_update;


    private static ProfessionModel mProfessionModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    public static LoginInformationFragmentNew newInstance() {
        LoginInformationFragmentNew mLoginInformationFragmentNew = new LoginInformationFragmentNew();
        return mLoginInformationFragmentNew;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        rootView = inflater.inflate(R.layout.fragment_login_fragment_new, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {
        tv_login_information = (TextView) rootView.findViewById(R.id.tv_login_information);
        edt_email = (EditText) rootView.findViewById(R.id.edt_email);
        tv_login_information.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        edt_email.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        tv_other = (TextView) rootView.findViewById(R.id.tv_other);
        tv_other.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        edt_occupation = (EditText) rootView.findViewById(R.id.edt_occupation);
        edt_occupation.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        edt_company = (EditText) rootView.findViewById(R.id.edt_company);
        edt_company.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        tv_update = (TextView) rootView.findViewById(R.id.tv_update);
        tv_update.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        edt_occupation.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        getProfessionList();
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

    private void getProfessionList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        ProfessionParser mParser = new ProfessionParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.GET_PROFESSIONS, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof ProfessionModel) {
                    mProfessionModel = (ProfessionModel) model;
                } else if (model instanceof JobPostingModel) {
                    JobPostingModel jobPostingModel = (JobPostingModel) model;
                    Utility.showToastMessage(getActivity(), jobPostingModel.getMessage());
                    callLoginDataAgain();
                } else if (model instanceof PersonalDetailsModel) {
                    PersonalDetailsModel mPersonalDetailsModel = (PersonalDetailsModel) model;
                    setPersonalDetails(mPersonalDetailsModel);
                } else if (model instanceof LoginModel) {
                    LoginModel loginModel = (LoginModel) model;
                    Utility.setSharedPrefStringData(mParent, Constants.PREF_KEY_IS_APP_SIGNIN_OR_SIGNUP, "done");
                    Utility.setSharedPrefStringData(mParent, Constants.PREF_KEY_UUID, loginModel.getUuid());
                    Utility.setSharedPrefStringData(mParent, Constants.PREF_KEY_EMAIL, loginModel.getEmail());
                    Utility.setSharedPrefStringData(mParent, Constants.PREF_KEY_ROLE, loginModel.getRole());
                    Utility.setSharedPrefStringData(mParent, Constants.PREF_KEY_ROLE_NAME, loginModel.getRole_name());
                    Utility.setSharedPrefStringData(mParent, Constants.PREF_KEY_FIRST_NAME, loginModel.getFirst_name());
                    Utility.setSharedPrefStringData(mParent, Constants.PREF_KEY_LAST_NAME, loginModel.getLast_name());

                    if (loginModel.getPhoto() != null)
                        Utility.setSharedPrefStringData(mParent, Constants.PREF_KEY_PHOTO, loginModel.getPhoto());
                    else
                        Utility.setSharedPrefStringData(mParent, Constants.PREF_KEY_PHOTO, "");
                }
            }
        }
    }

    private void callLoginDataAgain() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put("username", Utility.getSharedPrefStringData(mParent, Constants.PREF_KEY_LOGIN_NAME));
        paramMap.put("password", Utility.getSharedPrefStringData(mParent, Constants.PREF_KEY_LOGIN_PASSWORD));
        LoginParser mLoginParser = new LoginParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(mParent, Utility.getResourcesString(mParent,
                R.string.please_wait), true,
                APIConstants.LOGIN, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mLoginParser);
        Utility.execute(serverIntractorAsync);
    }

    private void setPersonalDetails(PersonalDetailsModel mPersonalDetailsModel) {
        edt_email.setText(mPersonalDetailsModel.getEmail());
        edt_occupation.setText(mPersonalDetailsModel.getOccu());
        edt_company.setText(mPersonalDetailsModel.getCompany());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_occupation:
                setDataToProfession();
                break;

            case R.id.tv_update:
                updateData();
                break;
        }
    }

    private void updateData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        paramMap.put("gothram", PersonalDetailsNewFragment.edt_gothram.getText().toString());
        paramMap.put("blood_group_id", PersonalDetailsNewFragment.getBloodId());
        paramMap.put("occupation", getOccupationId());
        paramMap.put("father_name", PersonalDetailsNewFragment.edt_father_name.getText().toString());
        paramMap.put("company", edt_company.getText().toString());
        paramMap.put("phone", ContactDetailsNewFragment.edt_phone.getText().toString());
        paramMap.put("zip", ContactDetailsNewFragment.edt_zip_code.getText().toString());
        paramMap.put("current_address", ContactDetailsNewFragment.edt_current_address.getText().toString());
        paramMap.put("permanent_address", ContactDetailsNewFragment.edt_permanent_address.getText().toString());
        if (ContactDetailsNewFragment.mYourFile != null) {
            paramMap.put("photo_name", ContactDetailsNewFragment.edt_company_logo.getText().toString());
            paramMap.put("photo", Utility.convertFileToByteArray(ContactDetailsNewFragment.mYourFile));
        }

        //paramMap.put("Id_proof", edt_company.getText().toString());
        //paramMap.put("Id_value", edt_company.getText().toString());
        //paramMap.put("Photo", edt_company.getText().toString());
        //paramMap.put("Photo_name", edt_company.getText().toString());
        JobPostingParser mJobPostingParser = new JobPostingParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(),
                Utility.getResourcesString(getActivity(),
                        R.string.please_wait), true,
                APIConstants.UPDATE_PROFILE,
                paramMap,
                APIConstants.REQUEST_TYPE.POST,
                this, mJobPostingParser);
        Utility.execute(serverIntractorAsync);
    }

    public static String getOccupationId() {
        String mBloodId = "";
        for (int i = 0; i < mProfessionModel.getSelectIdProofModels().size(); i++) {
            if (mProfessionModel.getSelectIdProofModels().get(i).getValue().equals(edt_occupation.getText().toString())) {
                mBloodId = mProfessionModel.getSelectIdProofModels().get(i).getId();
            }
        }
        return mBloodId;
    }

    private void setDataToProfession() {
        if (mProfessionModel != null)
            showSpinnerDialog(getActivity(), "Occupation", edt_occupation, mProfessionModel.getSpinnerModels(), 1);
    }

    public void showSpinnerDialog(final Context context, final String title, final EditText et_spinner,
                                  ArrayList<SpinnerModel> itemsList, final int id) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);

        /*CUSTOM TITLE*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_dialog_title, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_alert_dialog_title);
        RelativeLayout dialog_back_ground = (RelativeLayout) view.findViewById(R.id.dialog_back_ground);
        dialog_back_ground.setBackgroundColor(context.getResources().getColor(R.color.themeColor));
        tv_title.setText(title);
        tv_title.setTextColor(context.getResources().getColor(R.color.blackColor));
        builderSingle.setCustomTitle(view);

        final SpinnerAdapter adapter = new SpinnerAdapter(context, itemsList);
        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpinnerModel mData = (SpinnerModel) adapter.getItem(which);
                        if (id == 1) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                        }
                    }
                });
        builderSingle.show();
    }
}
