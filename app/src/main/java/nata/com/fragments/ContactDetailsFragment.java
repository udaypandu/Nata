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

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDetailsFragment extends Fragment implements IAsyncCaller, View.OnClickListener {

    private DashboardActivity mParent;
    public static String TAG = "ContactDetailsFragment";
    private View rootView;

    private TextView tv_contact_details;

    private LinearLayout ll_edit_caps;
    private LinearLayout ll_next_caps;

    private TextView tv_mobile_value;
    private TextView tv_current_address_value;
    private TextView tv_permanent_address_value;
    private TextView tv_country_name;
    private TextView tv_state_name;
    private TextView tv_city_value;
    private TextView tv_zip_code_value;
    private TextView tv_id_proof;

    private ImageView iv_id_proof;

    private Typeface mLatoTypeface;

    public static ContactDetailsFragment newInstance() {
        ContactDetailsFragment mContactDetailsFragment = new ContactDetailsFragment();
        return mContactDetailsFragment;
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
        rootView = inflater.inflate(R.layout.fragment_contact_details, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {
        mLatoTypeface = Utility.setTypeFace_Lato_Regular(getActivity());

        tv_contact_details = (TextView) rootView.findViewById(R.id.tv_contact_details);
        tv_contact_details.setTypeface(mLatoTypeface);

        TextView tv_mobile = (TextView) rootView.findViewById(R.id.tv_mobile);
        tv_mobile_value = (TextView) rootView.findViewById(R.id.tv_mobile_value);
        TextView tv_current_address = (TextView) rootView.findViewById(R.id.tv_current_address);
        tv_current_address_value = (TextView) rootView.findViewById(R.id.tv_current_address_value);
        TextView tv_permanent_name = (TextView) rootView.findViewById(R.id.tv_permanent_name);
        tv_permanent_address_value = (TextView) rootView.findViewById(R.id.tv_permanent_address_value);
        TextView tv_country = (TextView) rootView.findViewById(R.id.tv_country);
        tv_country_name = (TextView) rootView.findViewById(R.id.tv_country_name);
        TextView tv_state = (TextView) rootView.findViewById(R.id.tv_state);
        tv_state_name = (TextView) rootView.findViewById(R.id.tv_state_name);
        TextView tv_city = (TextView) rootView.findViewById(R.id.tv_city);
        tv_city_value = (TextView) rootView.findViewById(R.id.tv_city_value);
        TextView tv_zip_code = (TextView) rootView.findViewById(R.id.tv_zip_code);
        tv_zip_code_value = (TextView) rootView.findViewById(R.id.tv_zip_code_value);
        TextView tv_id_proof = (TextView) rootView.findViewById(R.id.tv_id_proof);
        iv_id_proof = (ImageView) rootView.findViewById(R.id.iv_id_proof);

        TextView tv_edit_icon = (TextView) rootView.findViewById(R.id.tv_edit_icon);
        TextView tv_edit = (TextView) rootView.findViewById(R.id.tv_edit);
        TextView tv_next = (TextView) rootView.findViewById(R.id.tv_next);
        TextView tv_next_icon = (TextView) rootView.findViewById(R.id.tv_next_icon);

        tv_mobile.setTypeface(mLatoTypeface);
        tv_current_address.setTypeface(mLatoTypeface);
        tv_permanent_name.setTypeface(mLatoTypeface);
        tv_country.setTypeface(mLatoTypeface);
        tv_state.setTypeface(mLatoTypeface);
        tv_city.setTypeface(mLatoTypeface);
        tv_zip_code.setTypeface(mLatoTypeface);
        tv_id_proof.setTypeface(mLatoTypeface);

        tv_mobile_value.setTypeface(mLatoTypeface);
        tv_current_address_value.setTypeface(mLatoTypeface);
        tv_permanent_address_value.setTypeface(mLatoTypeface);
        tv_country_name.setTypeface(mLatoTypeface);
        tv_state_name.setTypeface(mLatoTypeface);
        tv_city_value.setTypeface(mLatoTypeface);
        tv_zip_code_value.setTypeface(mLatoTypeface);

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
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getPhone()))
            tv_mobile_value.setText(mPersonalDetailsModel.getPhone());
        else
            tv_mobile_value.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getCurrent_address()))
            tv_current_address_value.setText(mPersonalDetailsModel.getCurrent_address());
        else
            tv_current_address_value.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getPermanent_address()))
            tv_permanent_address_value.setText(mPersonalDetailsModel.getPermanent_address());
        else
            tv_permanent_address_value.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getCountry()))
            tv_country_name.setText(mPersonalDetailsModel.getCountry());
        else
            tv_country_name.setText("");
        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getState()))
            tv_state_name.setText(mPersonalDetailsModel.getState());
        else
            tv_state_name.setText("");

        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getLocationModel().getCity())) {
            tv_city_value.setText(mPersonalDetailsModel.getLocationModel().getCity());
        } else {
            if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getLocationModel().getDistrict())) {
                tv_city_value.setText(mPersonalDetailsModel.getLocationModel().getDistrict());
            }
        }
        tv_zip_code_value.setText(mPersonalDetailsModel.getZip());

        if (!Utility.isValueNullOrEmpty(mPersonalDetailsModel.getId_proof()))
            Utility.URLProfilePicLoading(iv_id_proof,
                    mPersonalDetailsModel.getId_proof(), null, R.drawable.place_holder);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_caps:
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.EDIT_PAGE, 2);
                Utility.navigateDashBoardFragment(new EditProfileFragment(), EditProfileFragment.TAG, bundle, mParent);
                break;
            case R.id.ll_next_caps:
                ViewProfileFragment.pager.setCurrentItem(2);
                break;
        }
    }
}
