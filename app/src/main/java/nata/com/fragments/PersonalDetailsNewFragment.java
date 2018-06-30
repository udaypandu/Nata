package nata.com.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.SpinnerAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.BloodGroupModel;
import nata.com.models.Model;
import nata.com.models.PersonalDetailsModel;
import nata.com.models.SpinnerModel;
import nata.com.nata.R;
import nata.com.parsers.BloodGroupParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;


public class PersonalDetailsNewFragment extends Fragment implements View.OnClickListener, IAsyncCaller {

    public static String TAG = "PersonalDetailsNewFragment";
    private DashboardActivity mParent;
    private View rootView;
    private static PersonalDetailsModel mPersonalDetailsModelData;

    private TextView tv_personal_details;

    private EditText edt_first_name;
    private EditText edt_last_name;
    public static EditText edt_father_name;
    public static EditText edt_gothram;
    private EditText edt_gender;
    private static EditText edt_dob;
    private static EditText edt_blood_group;

    private Button btn_next;
    private Typeface mLatoTypeface;
    private static BloodGroupModel mBloodGroupModel;

    public static PersonalDetailsNewFragment newInstance(PersonalDetailsModel mPersonalDetailsModel) {
        PersonalDetailsNewFragment mPersonalDetailsNewFragment = new PersonalDetailsNewFragment();
        mPersonalDetailsModelData = mPersonalDetailsModel;
        return mPersonalDetailsNewFragment;
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
        rootView = inflater.inflate(R.layout.fragment_personal_details_new, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {
        mLatoTypeface = Utility.setTypeFace_Lato_Regular(getActivity());

        tv_personal_details = (TextView) rootView.findViewById(R.id.tv_personal_details);
        tv_personal_details.setTypeface(mLatoTypeface);

        edt_first_name = (EditText) rootView.findViewById(R.id.edt_first_name);
        edt_first_name.setTypeface(mLatoTypeface);
        edt_last_name = (EditText) rootView.findViewById(R.id.edt_last_name);
        edt_last_name.setTypeface(mLatoTypeface);
        edt_father_name = (EditText) rootView.findViewById(R.id.edt_father_name);
        edt_father_name.setTypeface(mLatoTypeface);
        edt_gothram = (EditText) rootView.findViewById(R.id.edt_gothram);
        edt_gothram.setTypeface(mLatoTypeface);
        edt_gender = (EditText) rootView.findViewById(R.id.edt_gender);
        edt_gender.setTypeface(mLatoTypeface);
        edt_dob = (EditText) rootView.findViewById(R.id.edt_dob);
        edt_dob.setTypeface(mLatoTypeface);
        edt_blood_group = (EditText) rootView.findViewById(R.id.edt_blood_group);
        edt_blood_group.setTypeface(mLatoTypeface);

        btn_next = (Button) rootView.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        //edt_dob.setOnClickListener(this);
        edt_blood_group.setOnClickListener(this);

        setOldData();
    }

    private void setOldData() {

        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModelData.getFirstname()))
            edt_first_name.setText(mPersonalDetailsModelData.getFirstname());
        else
            edt_first_name.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModelData.getLastname()))
            edt_last_name.setText(mPersonalDetailsModelData.getLastname());
        else
            edt_last_name.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModelData.getFather_name()))
            edt_father_name.setText(mPersonalDetailsModelData.getFather_name());
        else
            edt_father_name.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModelData.getGothram()))
            edt_gothram.setText(mPersonalDetailsModelData.getGothram());
        else
            edt_gothram.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModelData.getGender()))
            edt_gender.setText(mPersonalDetailsModelData.getGender());
        else
            edt_gender.setText("");

        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModelData.getDob()))
            edt_dob.setText(mPersonalDetailsModelData.getDob());
        else
            edt_dob.setText("");

        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModelData.getBlood_group()))
            edt_blood_group.setText(mPersonalDetailsModelData.getBlood_group());
        else
            edt_blood_group.setText("");
        getBloodGroops();
    }

    private void getBloodGroops() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        BloodGroupParser mBloodGroupParser = new BloodGroupParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.GET_BLOOD_GROUPS, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mBloodGroupParser);
        Utility.execute(serverIntractorAsync);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                EditProfileFragment.pager.setCurrentItem(1);
                break;
            case R.id.edt_dob:
                showDatePickerDialog();
                break;
            case R.id.edt_blood_group:
                setDataToBloodGroup();
                break;
        }
    }

    private void setDataToBloodGroup() {
        if (mBloodGroupModel != null)
            showSpinnerDialog(getActivity(), "Blood Group", edt_blood_group, mBloodGroupModel.getSpinnerModels(), 1);
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

    public static String getBloodId() {
        String mBloodId = "";
        for (int i = 0; i < mBloodGroupModel.getBloodGroupModels().size(); i++) {
            if (mBloodGroupModel.getBloodGroupModels().get(i).getBlood_group().equals(edt_blood_group.getText().toString())) {
                mBloodId = mBloodGroupModel.getBloodGroupModels().get(i).getId();
            }
        }
        return mBloodId;
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(mParent.getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof BloodGroupModel) {
                    mBloodGroupModel = (BloodGroupModel) model;
                } else {
                    Utility.setSnackBarEnglish(mParent, btn_next, model.getMessage());
                }
            }
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            if (Utility.isValueNullOrEmpty(mPersonalDetailsModelData.getDob())) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                // Create a new instance of DatePickerDialog and return it
                return new DatePickerDialog(getActivity(), this, year, month, day);
            } else {
                try {
                    DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date datecal = originalFormat.parse(mPersonalDetailsModelData.getDob());
                    Calendar c = Calendar.getInstance();
                    c.setTime(datecal);
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    // Create a new instance of DatePickerDialog and return it
                    return new DatePickerDialog(getActivity(), this, year, month, day);
                } catch (Exception e) {
                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    // Create a new instance of DatePickerDialog and return it
                    return new DatePickerDialog(getActivity(), this, year, month, day);
                }
            }
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            edt_dob.setText("" + year + "-" + month + "-" + day);
        }
    }
}
