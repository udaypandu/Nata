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
import nata.com.adapters.ViewProfileAdapter;
import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by Shankar on 1/4/2017.
 */

public class ViewProfileFragment extends Fragment implements View.OnClickListener {
    public static String TAG = "ViewProfileFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_one;
    private TextView txt_two;
    private TextView txt_three;

    public static ViewPager pager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.view_profile).toUpperCase());
        if (rootView != null) {
            return rootView;
        }
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
        pager.setAdapter(new ViewProfileAdapter(mParent.getSupportFragmentManager()));

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