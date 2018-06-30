package nata.com.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class PersonalDetailsFragment extends Fragment implements View.OnClickListener, IAsyncCaller {
    public static String TAG = "PersonalDetailsFragment";
    private DashboardActivity mParent;
    private View rootView;

    private LinearLayout ll_edit_caps;
    private LinearLayout ll_next_caps;

    private TextView tv_personal_details;

    private TextView tv_first_name_value;
    private TextView tv_last_name_value;
    private TextView tv_father_name_value;
    private TextView tv_gothram_name;
    private TextView tv_gender_name;
    private TextView tv_dob_value;
    private TextView tv_blood_group_name;

    private ImageView iv_photo_upload;

    private Typeface mLatoTypeface;
    private PersonalDetailsModel mPersonalDetailsModel;

    public static PersonalDetailsFragment newInstance() {
        PersonalDetailsFragment personalDetailsFragment = new PersonalDetailsFragment();
        return personalDetailsFragment;
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
        rootView = inflater.inflate(R.layout.fragment_personal_details, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {
        mLatoTypeface = Utility.setTypeFace_Lato_Regular(getActivity());

        tv_personal_details = (TextView) rootView.findViewById(R.id.tv_personal_details);
        tv_personal_details.setTypeface(mLatoTypeface);

        TextView tv_first_name = (TextView) rootView.findViewById(R.id.tv_first_name);
        tv_first_name_value = (TextView) rootView.findViewById(R.id.tv_first_name_value);
        TextView tv_last_name = (TextView) rootView.findViewById(R.id.tv_last_name);
        tv_last_name_value = (TextView) rootView.findViewById(R.id.tv_last_name_value);
        TextView tv_father_name = (TextView) rootView.findViewById(R.id.tv_father_name);
        tv_father_name_value = (TextView) rootView.findViewById(R.id.tv_father_name_value);
        TextView tv_gothram = (TextView) rootView.findViewById(R.id.tv_gothram);
        tv_gothram_name = (TextView) rootView.findViewById(R.id.tv_gothram_name);
        TextView tv_gender = (TextView) rootView.findViewById(R.id.tv_gender);
        tv_gender_name = (TextView) rootView.findViewById(R.id.tv_gender_name);
        TextView tv_dob = (TextView) rootView.findViewById(R.id.tv_dob);
        tv_dob_value = (TextView) rootView.findViewById(R.id.tv_dob_value);
        TextView tv_blood = (TextView) rootView.findViewById(R.id.tv_blood);
        tv_blood_group_name = (TextView) rootView.findViewById(R.id.tv_blood_group_name);
        TextView tv_photo = (TextView) rootView.findViewById(R.id.tv_photo);

        iv_photo_upload = (ImageView) rootView.findViewById(R.id.iv_photo_upload);

        TextView tv_edit_icon = (TextView) rootView.findViewById(R.id.tv_edit_icon);
        TextView tv_edit = (TextView) rootView.findViewById(R.id.tv_edit);
        TextView tv_next = (TextView) rootView.findViewById(R.id.tv_next);
        TextView tv_next_icon = (TextView) rootView.findViewById(R.id.tv_next_icon);

        tv_first_name.setTypeface(mLatoTypeface);
        tv_last_name.setTypeface(mLatoTypeface);
        tv_father_name.setTypeface(mLatoTypeface);
        tv_gothram.setTypeface(mLatoTypeface);
        tv_gender.setTypeface(mLatoTypeface);
        tv_dob.setTypeface(mLatoTypeface);
        tv_blood.setTypeface(mLatoTypeface);
        tv_photo.setTypeface(mLatoTypeface);
        tv_first_name_value.setTypeface(mLatoTypeface);
        tv_last_name_value.setTypeface(mLatoTypeface);
        tv_father_name_value.setTypeface(mLatoTypeface);
        tv_gothram_name.setTypeface(mLatoTypeface);
        tv_gender_name.setTypeface(mLatoTypeface);
        tv_dob_value.setTypeface(mLatoTypeface);
        tv_blood_group_name.setTypeface(mLatoTypeface);

        tv_edit.setTypeface(mLatoTypeface);
        tv_edit_icon.setTypeface(Utility.setTypeFace_Image(getActivity()));
        tv_next_icon.setTypeface(Utility.setTypeFace_Image(getActivity()));
        tv_next.setTypeface(mLatoTypeface);

        getPersonalData();
        ll_edit_caps = (LinearLayout) rootView.findViewById(R.id.ll_edit_caps);
        ll_next_caps = (LinearLayout) rootView.findViewById(R.id.ll_next_caps);
        ll_edit_caps.setOnClickListener(this);
        ll_next_caps.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_caps:
                gotoEditProfile();
                break;
            case R.id.ll_next_caps:
                ViewProfileFragment.pager.setCurrentItem(1);
                break;
        }
    }

    private void gotoEditProfile() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EDIT_PAGE, 1);
        if (mPersonalDetailsModel != null) {
            bundle.putSerializable(Constants.PERSONAL_DETAILS_DATA, mPersonalDetailsModel);
        }
        Utility.navigateDashBoardFragment(new EditProfileFragment(), EditProfileFragment.TAG, bundle, mParent);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof PersonalDetailsModel) {
                    mPersonalDetailsModel = (PersonalDetailsModel) model;
                    setPersonalDetails();
                }
            } else {
                Utility.showToastMessage(mParent, "OOPS..! Some problem with the API");
            }
        }
    }

    private void setPersonalDetails() {
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getFirstname()))
            tv_first_name_value.setText(mPersonalDetailsModel.getFirstname());
        else
            tv_first_name_value.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getLastname()))
            tv_last_name_value.setText(mPersonalDetailsModel.getLastname());
        else
            tv_last_name_value.setText("");

        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getFather_name()))
            tv_father_name_value.setText(mPersonalDetailsModel.getFather_name());
        else
            tv_father_name_value.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getGothram()))
            tv_gothram_name.setText(mPersonalDetailsModel.getGothram());
        else
            tv_gothram_name.setText("");

        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getGender()))
            tv_gender_name.setText(mPersonalDetailsModel.getGender());
        else
            tv_gender_name.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getDob()))
            tv_dob_value.setText(mPersonalDetailsModel.getDob());
        else
            tv_dob_value.setText("");

        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getBlood_group()))
            tv_blood_group_name.setText(mPersonalDetailsModel.getBlood_group());
        else
            tv_blood_group_name.setText("");

        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getPhoto()))
            Utility.URLProfilePicLoading(iv_photo_upload,
                    mPersonalDetailsModel.getPhoto(), null, R.drawable.place_holder);
    }
}

