package nata.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.ViewProfileEditAdapter;
import nata.com.models.PersonalDetailsModel;
import nata.com.nata.R;
import nata.com.utility.Constants;
import nata.com.utility.Utility;


/**
 * Created by Shankar on 1/6/2017.
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener {
    public static String TAG = "EditProfileFragment";
    public static ViewPager pager;
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_one;
    private TextView txt_two;
    private TextView txt_three;
    private int mFromPosition = -1;

    private PersonalDetailsModel mPersonalDetailsModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle.containsKey(Constants.EDIT_PAGE)) {
            mFromPosition = bundle.getInt(Constants.EDIT_PAGE);
        }
        if (bundle.containsKey(Constants.PERSONAL_DETAILS_DATA)) {
            mPersonalDetailsModel = (PersonalDetailsModel) bundle.getSerializable(Constants.PERSONAL_DETAILS_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.edit_profile).toUpperCase());
        rootView = inflater.inflate(R.layout.fragment_view_profile, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {

        //Set the pager with an adapter
        txt_one = (TextView) rootView.findViewById(R.id.txt_one);
        txt_two = (TextView) rootView.findViewById(R.id.txt_two);
        txt_three = (TextView) rootView.findViewById(R.id.txt_three);

        pager = (ViewPager) rootView.findViewById(R.id.viewPager);
        pager.setAdapter(new ViewProfileEditAdapter(mParent.getSupportFragmentManager(), mPersonalDetailsModel));

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setCurrentViewPosition(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        txt_one.setOnClickListener(this);
        txt_two.setOnClickListener(this);
        txt_three.setOnClickListener(this);

        if (mFromPosition == 1) {
            pager.setCurrentItem(0);
        } else if (mFromPosition == 2) {
            pager.setCurrentItem(1);
        }
    }

    private void setCurrentViewPosition(int position) {
        switch (position) {
            case 0:
                txt_one.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.view_profile_selection));
                txt_two.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.view_profile_unselection));
                txt_three.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.view_profile_unselection));
                break;
            case 1:

                txt_one.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.view_profile_unselection));
                txt_two.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.view_profile_selection));
                txt_three.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.view_profile_unselection));

                break;
            case 2:
                txt_one.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.view_profile_unselection));
                txt_two.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.view_profile_unselection));
                txt_three.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.view_profile_selection));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_one:
                pager.setCurrentItem(0);
                break;
            case R.id.txt_two:
                pager.setCurrentItem(1);
                break;
            case R.id.txt_three:
                pager.setCurrentItem(2);
                break;
        }
    }
}