package nata.com.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import nata.com.adapters.SpinnerAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.CitiesModel;
import nata.com.models.CountriesModel;
import nata.com.models.DistrictsModel;
import nata.com.models.MandalsModel;
import nata.com.models.Model;
import nata.com.models.ProfessionModel;
import nata.com.models.SelectIdProofModel;
import nata.com.models.SpinnerModel;
import nata.com.models.StatesModel;
import nata.com.models.SuccessModel;
import nata.com.models.VillagesModel;
import nata.com.nata.R;
import nata.com.parsers.AskForInvitationParser;
import nata.com.parsers.CitiesParser;
import nata.com.parsers.CountriesParser;
import nata.com.parsers.DistrictsParser;
import nata.com.parsers.MandalsParser;
import nata.com.parsers.ProfessionParser;
import nata.com.parsers.SelectIdProofParser;
import nata.com.parsers.StatesParser;
import nata.com.parsers.VillagesParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

public class AskForInvitationActivity extends AppCompatActivity implements View.OnClickListener, IAsyncCaller {
    private EditText edt_first_name;
    private EditText edt_last_name;
    private EditText edt_email_id;
    private EditText edt_confirm_email_id;
    private EditText edt_phone_no;
    private EditText edt_country;
    private EditText edt_profession;
    private EditText edt_state;
    private EditText edt_city;
    private EditText edt_district;
    private EditText edt_mandal;
    private EditText edt_village;
    private EditText edt_zip_code;
    private EditText edt_select_id;
    private EditText edt_enter_id_proof;
    private EditText edt_company_name;

    private TextView tv_submit;

    private ImageView img_close;

    private ProfessionModel mProfessionModel;
    private CountriesModel countriesModel;
    private StatesModel statesModel;
    private DistrictsModel districtsModel;
    private CitiesModel citiesModel;
    private MandalsModel mandalsModel;
    private VillagesModel villagesModel;
    private SelectIdProofModel mSelectIdProofModel;
    private SuccessModel mSuccessModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_ask_for_invitation_janasena);
        initUI();
    }

    private void initUI() {
        edt_first_name = (EditText) findViewById(R.id.edt_first_name);
        edt_last_name = (EditText) findViewById(R.id.edt_last_name);
        edt_email_id = (EditText) findViewById(R.id.edt_email_id);
        edt_confirm_email_id = (EditText) findViewById(R.id.edt_confirm_email_id);
        edt_phone_no = (EditText) findViewById(R.id.edt_phone_no);
        edt_country = (EditText) findViewById(R.id.edt_country);
        edt_profession = (EditText) findViewById(R.id.edt_profession);
        edt_state = (EditText) findViewById(R.id.edt_state);
        edt_select_id = (EditText) findViewById(R.id.edt_select_id);
        edt_city = (EditText) findViewById(R.id.edt_city);
        edt_district = (EditText) findViewById(R.id.edt_district);
        edt_mandal = (EditText) findViewById(R.id.edt_mandal);
        edt_village = (EditText) findViewById(R.id.edt_village);
        edt_zip_code = (EditText) findViewById(R.id.edt_zip_code);
        edt_enter_id_proof = (EditText) findViewById(R.id.edt_enter_id_proof);
        edt_company_name = (EditText) findViewById(R.id.edt_company_name);

        tv_submit = (TextView) findViewById(R.id.tv_submit);


        getProfessionList();

    }

    private void getProfessionList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        ProfessionParser mParser = new ProfessionParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.GET_PROFESSIONS, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }

    private void getCountriesList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        CountriesParser mParser = new CountriesParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.COUNTRIES_URL, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }

    private void getStatesList(String mCountryID) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        StatesParser mParser = new StatesParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.STATES_URL + mCountryID, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }

    private void getCitiesList(String mStateID) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        CitiesParser mParser = new CitiesParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.CITIES_URL + mStateID, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }

    private void getDistrictsList(String mStateID) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        DistrictsParser mParser = new DistrictsParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.DISTRICTS_URL + mStateID, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }


    private void getMandalsList(String mDistrictID) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        MandalsParser mParser = new MandalsParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.MANDALS_URL + mDistrictID, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }

    private void getVillagesList(String mMandalID) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        VillagesParser mParser = new VillagesParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.VILLAGES_URL + mMandalID, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }



    @Override
   public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_country:
                setDataToTheSpinner();
                break;
            case R.id.edt_state:
                setDataToTheStates();
                break;
            case R.id.edt_profession:
                setDataToProfession();
                break;
            case R.id.edt_district:
                setDataToTheDistricts();
                break;
            case R.id.edt_city:
                setDataToTheCities();
                break;
            case R.id.edt_mandal:
                setDataToTheMandals();
                break;
            case R.id.edt_select_id:
                setSelectIdProofData();
                break;
            case R.id.edt_village:
                setDataToTheVillages();
                break;
            case R.id.edt_zip_code:
                break;
            case R.id.tv_submit:
                if (isValidFields()) {
                    sendAskForInvitationDetails();
                }
                break;
            case R.id.tv_already_have_an_account_sign_in:
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;

        }
    }
    private void sendAskForInvitationDetails() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        paramMap.put("firstname", edt_first_name.getText().toString());
        paramMap.put("lastname", edt_last_name.getText().toString());
        paramMap.put("email", edt_email_id.getText().toString());
        paramMap.put("phone", edt_phone_no.getText().toString());
        paramMap.put("profession", getProfessionId(edt_profession.getText().toString()));
        paramMap.put("country", getCountryId(edt_country.getText().toString()));
        paramMap.put("state", getStateId(edt_state.getText().toString()));
        if (edt_city.getVisibility() == View.VISIBLE)
            paramMap.put("city", getCiteId(edt_city.getText().toString()));
        else
            paramMap.put("city", "");
        paramMap.put("zip", edt_zip_code.getText().toString());

        if (!Utility.isValueNullOrEmpty(edt_select_id.getText().toString().trim())) {
            paramMap.put("id_proof", edt_select_id.getText().toString());
            paramMap.put("id_value", edt_enter_id_proof.getText().toString());
        }
        if (edt_district.getVisibility() == View.VISIBLE)
            paramMap.put("district", getDistrictId(edt_district.getText().toString()));
        else
            paramMap.put("district", "");

        if (edt_mandal.getVisibility() == View.VISIBLE)
            paramMap.put("mandal", getMandalId(edt_mandal.getText().toString()));
        else
            paramMap.put("mandal", "");

        if (edt_village.getVisibility() == View.VISIBLE)
            paramMap.put("village", getVillageId(edt_mandal.getText().toString()));
        else
            paramMap.put("village", "");

        paramMap.put("others", "");
        paramMap.put("company", "");

        AskForInvitationParser mAskForInvitationParser = new AskForInvitationParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.ASK_INVITE, paramMap,
                APIConstants.REQUEST_TYPE.POST, this, mAskForInvitationParser);
        Utility.execute(serverIntractorAsync);

    }

    private String getProfessionId(String s) {
        String mProfessionId = "";
        for (int i = 0; i < mProfessionModel.getSelectIdProofModels().size(); i++) {
            if (mProfessionModel.getSelectIdProofModels().get(i).getValue().equals(s)) {
                mProfessionId = mProfessionModel.getSelectIdProofModels().get(i).getId();
            }
        }
        return mProfessionId;
    }

    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(edt_first_name.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_first_name, "Please enter your first name");
            edt_first_name.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_last_name.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_last_name, "Please enter your last name");
            edt_last_name.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_email_id.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_email_id, "Please enter Email id");
            edt_email_id.requestFocus();
        } else if (!edt_email_id.getText().toString().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$")) {
            Utility.setSnackBarEnglish(this, edt_email_id, "Please enter valid email");
            edt_email_id.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_confirm_email_id.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_confirm_email_id, "Please enter Confirm Email id");
            edt_confirm_email_id.requestFocus();
        } else if (!edt_email_id.getText().toString().trim().equalsIgnoreCase(edt_confirm_email_id.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_confirm_email_id, "Email ids not match");
            edt_confirm_email_id.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_phone_no.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_phone_no, "Please enter Phone number");
            edt_phone_no.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_profession.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_profession, "Please enter Profession");
            edt_profession.requestFocus();
        }/* else if (Utility.isValueNullOrEmpty(edt_company_name.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_company_name, "Please enter Company Name");
            edt_company_name.requestFocus();
        } */else if (Utility.isValueNullOrEmpty(edt_country.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_country, "Please select Country");
            edt_country.requestFocus();
        } /*else if (Utility.isValueNullOrEmpty(edt_state.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_state, "Please select State");
            edt_state.requestFocus();
        } */ else if (edt_country.getText().toString().trim().equalsIgnoreCase("India") && Utility.isValueNullOrEmpty(edt_state.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_state, "Please select State");
            edt_state.requestFocus();
        } else if (edt_country.getText().toString().trim().equalsIgnoreCase("India") && edt_city.getVisibility() == View.VISIBLE && Utility.isValueNullOrEmpty(edt_city.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_city, "Please select Cities");
            edt_district.requestFocus();
        } else if (edt_district.getVisibility() == View.VISIBLE && Utility.isValueNullOrEmpty(edt_district.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_district, "Please select District");
            edt_district.requestFocus();
        } else if (edt_district.getVisibility() == View.VISIBLE && Utility.isValueNullOrEmpty(edt_mandal.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_mandal, "Please select Mandal");
            edt_mandal.requestFocus();
        } else if (edt_district.getVisibility() == View.VISIBLE && Utility.isValueNullOrEmpty(edt_village.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_village, "Please select Village");
            edt_village.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_zip_code.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_zip_code, "Please enter Pincode");
            edt_zip_code.requestFocus();
        } /*else if (Utility.isValueNullOrEmpty(edt_select_id.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_select_id, "Please select Id Proof");
            edt_select_id.requestFocus();
        } else if (Utility.isValueNullOrEmpty(edt_enter_id_proof.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edt_enter_id_proof, "Please Enter Id Proof");
            edt_enter_id_proof.requestFocus();
        } */ else {
            isValidated = true;
        }
        return isValidated;
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof ProfessionModel) {
                    mProfessionModel = (ProfessionModel) model;
                } else if (model instanceof CountriesModel) {
                    countriesModel = (CountriesModel) model;
                } else if (model instanceof StatesModel) {
                    statesModel = (StatesModel) model;
                } else if (model instanceof CitiesModel) {
                    citiesModel = (CitiesModel) model;
                } else if (model instanceof DistrictsModel) {
                    districtsModel = (DistrictsModel) model;
                } else if (model instanceof MandalsModel) {
                    mandalsModel = (MandalsModel) model;
                } else if (model instanceof VillagesModel) {
                    villagesModel = (VillagesModel) model;
                } else if (model instanceof SelectIdProofModel) {
                    mSelectIdProofModel = (SelectIdProofModel) model;
                } else if (model instanceof SuccessModel) {
                    mSuccessModel = (SuccessModel) model;
                    finish();
                    Intent intent = new Intent(this, AskForInvitationSuccessActivity.class);
                    startActivity(intent);
                }
            } else {
                Utility.setSnackBarEnglish(this, tv_submit, model.getMessage());
            }
        }
    }

    private void setDataToTheSpinner() {
        if (countriesModel != null)
            showSpinnerDialog(this, "Countries", edt_country, countriesModel.getCountriesSpinnerModels(), 1);
    }

    private void setDataToTheStates() {
        if (statesModel != null)
            showSpinnerDialog(this, "States", edt_state, statesModel.getStatesSpinnerModels(), 1);
    }

    private void setSelectIdProofData() {
        if (mSelectIdProofModel != null)
            showSpinnerDialog(this, "Select Id Proof", edt_select_id, mSelectIdProofModel.getSpinnerModels(), 1);
    }

    private void setDataToProfession() {
        if (mProfessionModel != null)
            showSpinnerDialog(this, "Profession", edt_profession, mProfessionModel.getSpinnerModels(), 1);
    }

    private void setDataToTheCities() {
        if (citiesModel != null)
            showSpinnerDialog(this, "Cities", edt_city, citiesModel.getCitiesSpinnerModels(), 1);
    }

    private void setDataToTheDistricts() {
        if (districtsModel != null)
            showSpinnerDialog(this, "Districts", edt_district, districtsModel.getDistrictsSpinnerModels(), 1);
    }

    private void setDataToTheMandals() {
        if (mandalsModel != null)
            showSpinnerDialog(this, "Mandals", edt_mandal, mandalsModel.getMandalsSpinnerModels(), 1);
    }

    private void setDataToTheVillages() {
        if (villagesModel != null)
            showSpinnerDialog(this, "Villages", edt_village, villagesModel.getVillagesSpinnerModels(), 1);
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
                            if (title.equalsIgnoreCase("Profession")) {
                                getCountriesList();
                            } else if (title.equalsIgnoreCase("Countries")) {
                                getStatesList(getCountryId(text));
                                edt_district.setVisibility(View.GONE);
                                edt_city.setVisibility(View.GONE);
                                edt_village.setVisibility(View.GONE);
                                edt_mandal.setVisibility(View.GONE);
                                edt_district.setText("");
                                edt_city.setText("");
                                edt_mandal.setText("");
                                edt_village.setText("");
                                edt_state.setText("");
                            } else if (title.equalsIgnoreCase("States")) {
                                if (edt_state.getText().toString().equalsIgnoreCase("Andhra Pradesh")
                                        || edt_state.getText().toString().equalsIgnoreCase("Telangana")) {
                                    edt_district.setVisibility(View.VISIBLE);
                                    edt_city.setVisibility(View.GONE);
                                    getDistrictsList(getStateId(text));
                                } else {
                                    edt_district.setVisibility(View.GONE);
                                    edt_city.setVisibility(View.VISIBLE);
                                    getCitiesList(getStateId(text));
                                }
                                edt_village.setVisibility(View.GONE);
                                edt_mandal.setVisibility(View.GONE);
                                edt_mandal.setText("");
                                edt_village.setText("");
                                edt_district.setText("");
                                edt_city.setText("");
                            } else if (title.equalsIgnoreCase("Cities")) {
                                edt_district.setVisibility(View.GONE);
                                getSelectIDList();
                            } else if (title.equalsIgnoreCase("Districts")) {
                                getMandalsList(getDistrictId(text));
                                edt_mandal.setVisibility(View.VISIBLE);
                                edt_city.setVisibility(View.GONE);
                            } else if (title.equalsIgnoreCase("Mandals")) {
                                getVillagesList(getMandalId(text));
                                edt_village.setVisibility(View.VISIBLE);
                            } else if (title.equalsIgnoreCase("Villages")) {
                                getSelectIDList();
                            }
                        }
                    }
                });
        builderSingle.show();
    }

    private void getSelectIDList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        SelectIdProofParser mSelectIdProofParser = new SelectIdProofParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(this, Utility.getResourcesString(this,
                R.string.please_wait), true,
                APIConstants.GET_ID_PROOFS, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mSelectIdProofParser);
        Utility.execute(serverIntractorAsync);
    }

    private String getCountryId(String s) {
        String mCountryID = "";
        for (int i = 0; i < countriesModel.getCountriesSpinnerModels().size(); i++) {
            if (countriesModel.getCountriesModels().get(i).getCountry_name().equals(s)) {
                mCountryID = countriesModel.getCountriesModels().get(i).getId();
            }
        }
        return mCountryID;
    }

    private String getStateId(String s) {
        String mStateID = "";
        for (int i = 0; i < statesModel.getStatesSpinnerModels().size(); i++) {
            if (statesModel.getStatesModels().get(i).getState_name().equals(s)) {
                mStateID = statesModel.getStatesModels().get(i).getId();
            }
        }
        return mStateID;
    }

    private String getCiteId(String s) {
        String mCitiID = "";
        for (int i = 0; i < citiesModel.getCitiesSpinnerModels().size(); i++) {
            if (citiesModel.getCitiesModels().get(i).getCity_name().equals(s)) {
                mCitiID = citiesModel.getCitiesModels().get(i).getId();
            }
        }
        return mCitiID;
    }


    private String getDistrictId(String s) {
        String mDistrictID = "";
        for (int i = 0; i < districtsModel.getDistrictsSpinnerModels().size(); i++) {
            if (districtsModel.getDistrictsModels().get(i).getDistrict_name().equals(s)) {
                mDistrictID = districtsModel.getDistrictsModels().get(i).getId();
            }
        }
        return mDistrictID;
    }

    private String getMandalId(String s) {
        String mMandalId = "";
        for (int i = 0; i < mandalsModel.getMandalsSpinnerModels().size(); i++) {
            if (mandalsModel.getMandalsModels().get(i).getMandal_name().equals(s)) {
                mMandalId = mandalsModel.getMandalsModels().get(i).getId();
            }
        }
        return mMandalId;
    }

    private String getVillageId(String s) {
        String mVillageId = "";
        for (int i = 0; i < villagesModel.getVillagesSpinnerModels().size(); i++) {
            if (villagesModel.getVillagesModels().get(i).getVillage_name().equals(s)) {
                mVillageId = villagesModel.getVillagesModels().get(i).getId();
            }
        }
        return mVillageId;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String filePath = data.getDataString();
        Utility.showLog("filePath", "" + filePath);
        super.onActivityResult(requestCode, resultCode, data);

    }
}
