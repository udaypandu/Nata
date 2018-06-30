package nata.com.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.SpinnerAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.interfaces.IUpdateSelectedFile;
import nata.com.models.CountriesModel;
import nata.com.models.Model;
import nata.com.models.PersonalDetailsModel;
import nata.com.models.SpinnerModel;
import nata.com.nata.R;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDetailsNewFragment extends Fragment implements View.OnClickListener, IAsyncCaller, IUpdateSelectedFile {

    public static String TAG = "ContactDetailsNewFragment";
    private DashboardActivity mParent;
    private View rootView;
    private static PersonalDetailsModel mPersonalDetailsModelData;

    private Typeface mLatoTypeface;
    private TextView tv_contact_details;
    private Button btn_next;
    public static EditText edt_phone;
    public static EditText edt_current_address;
    public static EditText edt_permanent_address;
    private EditText edt_state;
    private EditText edt_country;
    private EditText edt_district;
    public static EditText edt_zip_code;
    public static EditText edt_company_logo;
    private Button btn_upload;

    private CountriesModel countriesModel;

    public static File mYourFile;
    private static IUpdateSelectedFile iUpdateSelectedFile;

    public static IUpdateSelectedFile getInstance() {
        return iUpdateSelectedFile;
    }

    public static ContactDetailsNewFragment newInstance(PersonalDetailsModel mPersonalDetailsModel) {
        ContactDetailsNewFragment mContactDetailsNewFragment = new ContactDetailsNewFragment();
        mPersonalDetailsModelData = mPersonalDetailsModel;
        return mContactDetailsNewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        iUpdateSelectedFile = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        rootView = inflater.inflate(R.layout.fragment_contact_details_new, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {
        mLatoTypeface = Utility.setTypeFace_Lato_Regular(getActivity());
        tv_contact_details = (TextView) rootView.findViewById(R.id.tv_contact_details);
        tv_contact_details.setTypeface(mLatoTypeface);

        btn_next = (Button) rootView.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        edt_phone = (EditText) rootView.findViewById(R.id.edt_phone);
        edt_current_address = (EditText) rootView.findViewById(R.id.edt_current_address);
        edt_permanent_address = (EditText) rootView.findViewById(R.id.edt_permanent_address);
        edt_country = (EditText) rootView.findViewById(R.id.edt_country);
        edt_state = (EditText) rootView.findViewById(R.id.edt_state);
        edt_zip_code = (EditText) rootView.findViewById(R.id.edt_zip_code);
        edt_district = (EditText) rootView.findViewById(R.id.edt_district);
        edt_company_logo = (EditText) rootView.findViewById(R.id.edt_company_logo);

        btn_upload = (Button) rootView.findViewById(R.id.btn_upload);

        btn_upload.setOnClickListener(this);
        edt_country.setOnClickListener(this);
        edt_state.setOnClickListener(this);
        edt_district.setOnClickListener(this);

        btn_upload.setTypeface(mLatoTypeface);
        btn_next.setTypeface(mLatoTypeface);

        setOldData();

    }

    private void setOldData() {
        if (mPersonalDetailsModelData != null && mPersonalDetailsModelData.getPhone() != null)
            edt_phone.setText(mPersonalDetailsModelData.getPhone());
        edt_phone.setTypeface(mLatoTypeface);
        edt_current_address.setText(mPersonalDetailsModelData.getCurrent_address());
        edt_current_address.setTypeface(mLatoTypeface);
        edt_permanent_address.setText(mPersonalDetailsModelData.getPermanent_address());
        edt_permanent_address.setTypeface(mLatoTypeface);
        edt_state.setText(mPersonalDetailsModelData.getLocationModel().getState());
        edt_state.setTypeface(mLatoTypeface);
        edt_country.setText(mPersonalDetailsModelData.getLocationModel().getCountry());
        edt_country.setTypeface(mLatoTypeface);
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModelData.getLocationModel().getDistrict())) {
            edt_district.setText(mPersonalDetailsModelData.getLocationModel().getDistrict());
        } else {
            edt_district.setText("");
        }
        edt_district.setTypeface(mLatoTypeface);
        edt_zip_code.setText(mPersonalDetailsModelData.getZip());
        edt_zip_code.setTypeface(mLatoTypeface);
        edt_company_logo.setText(mPersonalDetailsModelData.getPhoto());
        edt_company_logo.setTypeface(mLatoTypeface);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_district:

                break;
            case R.id.edt_state:

                break;
            case R.id.edt_country:
                setDataToTheSpinner();
                break;
            case R.id.btn_upload:
                pickFile();
                break;
            case R.id.btn_next:
                EditProfileFragment.pager.setCurrentItem(2);
                break;
        }
    }

    private void pickFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mParent.startActivityForResult(Intent.createChooser(intent, "Select profile image logo"), Constants.FROM_CONTACT_DETAILS);
    }

    private void setDataToTheSpinner() {
        if (countriesModel != null)
            showSpinnerDialog(getActivity(), "Countries", edt_country, countriesModel.getCountriesSpinnerModels(), 1);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof CountriesModel) {
                    countriesModel = (CountriesModel) model;
                } else {
                    Utility.setSnackBarEnglish(mParent, btn_next, model.getMessage());
                }
            }
        }
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

    @Override
    public void updateFile(String path) {
        mYourFile = new File(path);
        edt_company_logo.setText(mYourFile.getName());
    }
}
