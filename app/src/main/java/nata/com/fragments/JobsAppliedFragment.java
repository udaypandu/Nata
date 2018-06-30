package nata.com.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.ViewPagerAdapter;
import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsAppliedFragment extends Fragment implements View.OnClickListener {


    public static final String TAG = "JobsAppliedFragment";
    private DashboardActivity mParent;
    private View rootView;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private Toolbar mToolbar;


    private LinearLayout ll_position_one;
    private TextView txt_position_one;
    private TextView txt_position_one_icon;

    private LinearLayout ll_position_two;
    private TextView txt_position_two;
    private TextView txt_position_two_icon;

    private LinearLayout ll_position_three;
    private TextView txt_position_three;
    private TextView txt_position_three_icon;

    private LinearLayout ll_position_four;
    private TextView txt_position_four;
    private TextView txt_position_four_icon;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.community_forum).toUpperCase());
        rootView = inflater.inflate(R.layout.fragment_jobs_applied, container, false);
        initUI();
        return rootView;
    }


    private void initUI() {
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        setFooterData();
        ll_position_one = (LinearLayout) rootView.findViewById(R.id.ll_position_one);
        ll_position_two = (LinearLayout) rootView.findViewById(R.id.ll_position_two);
        ll_position_three = (LinearLayout) rootView.findViewById(R.id.ll_position_three);
        ll_position_four = (LinearLayout) rootView.findViewById(R.id.ll_position_four);

        ll_position_one.setOnClickListener(this);
        ll_position_two.setOnClickListener(this);
        ll_position_three.setOnClickListener(this);
        ll_position_four.setOnClickListener(this);


    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new PopularOurForumFragment(), "ONE");
        adapter.addFrag(new PopularOurForumFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView textview = (TextView) tabOne.findViewById(R.id.txt_image);
        textview.setText(Utility.getResourcesString(mParent, R.string.custom));
        textview.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        textview.setTextColor(getResources().getColor(R.color.colorPrimary));
        textview.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView text_view_two = (TextView) tabTwo.findViewById(R.id.txt_image);
        text_view_two.setText(Utility.getResourcesString(mParent, R.string.popular));
        text_view_two.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        text_view_two.setTextColor(Utility.getColor(mParent, R.color.white));
        tabTwo.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout selectedTab = (LinearLayout) tab.getCustomView();
                /*selectedTab.setLayoutParams(new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 45));*/
                TextView textview = (TextView) selectedTab.findViewById(R.id.txt_image);
                textview.setTextColor(getResources().getColor(R.color.colorPrimary));
                textview.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout selectedTab = (LinearLayout) tab.getCustomView();
                /*selectedTab.setLayoutParams(new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 45));*/
                TextView textview = (TextView) selectedTab.findViewById(R.id.txt_image);
                textview.setTextColor(getResources().getColor(R.color.white));
                textview.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void setFooterData() {


        txt_position_one = (TextView) rootView.findViewById(R.id.txt_position_one);
        txt_position_two = (TextView) rootView.findViewById(R.id.txt_position_two);
        txt_position_three = (TextView) rootView.findViewById(R.id.txt_position_three);
        txt_position_four = (TextView) rootView.findViewById(R.id.txt_position_four);

        txt_position_one.setText(Utility.getResourcesString(getActivity(), R.string.update_small));
        txt_position_two.setText(Utility.getResourcesString(getActivity(), R.string.services_small));
        txt_position_three.setText(Utility.getResourcesString(getActivity(), R.string.members));
        txt_position_four.setText(Utility.getResourcesString(getActivity(), R.string.filter));

        txt_position_one.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_position_two.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_position_three.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_position_four.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        txt_position_one_icon = (TextView) rootView.findViewById(R.id.txt_position_one_icon);
        txt_position_two_icon = (TextView) rootView.findViewById(R.id.txt_position_two_icon);
        txt_position_three_icon = (TextView) rootView.findViewById(R.id.txt_position_three_icon);
        txt_position_four_icon = (TextView) rootView.findViewById(R.id.txt_position_four_icon);

        txt_position_one_icon.setText(Utility.getResourcesString(mParent, R.string.find_jobs));
        txt_position_two_icon.setText(Utility.getResourcesString(mParent, R.string.post_job_icon));
        txt_position_three_icon.setText(Utility.getResourcesString(mParent, R.string.my_posts_icon));
        txt_position_four_icon.setText(Utility.getResourcesString(mParent, R.string.filter_icon));

        txt_position_one_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));
        txt_position_two_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));
        txt_position_three_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));
        txt_position_four_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_position_one:
                Utility.navigateDashBoardFragment(new FindJobsFragment(), FindJobsFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_two:
                Utility.navigateDashBoardFragment(new JobPostingFragment(), JobPostingFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_three:
                Utility.navigateDashBoardFragment(new MyPostFragment(), MyPostFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_four:
                Utility.navigateDashBoardFragment(new FiltersFragment(), FiltersFragment.TAG, null, mParent);
                break;
        }
    }

}
