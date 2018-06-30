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
import android.widget.TextView;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.ViewPagerAdapter;
import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by Shankar on 2/7/2017.
 */

public class PartyUpdatesTabBarFragment extends Fragment {

    public static final String TAG = "PartyUpdatesTabBarFragment";
    private DashboardActivity mParent;
    private Toolbar mToolbar;
    private View rootView;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTheme(R.style.AppTheme_NoActionBar);
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
        }
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_party_updates_tab, container, false);
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
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new NewsHomePageFragment(), "ONE");
        adapter.addFrag(new NewsHomePageVideosFragment(), "TWO");
        adapter.addFrag(new NewsHomePageEventsFragment(), "THREE");
        adapter.addFrag(new TwitterFragment(), "FOUR");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView textview = (TextView) tabOne.findViewById(R.id.txt_image);
        textview.setText("News");
        textview.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        textview.setTextColor(getResources().getColor(R.color.white));
        tabOne.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_image));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView text_view_two = (TextView) tabTwo.findViewById(R.id.txt_image);
        text_view_two.setText("Videos");
        text_view_two.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        tabTwo.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabThree = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView text_view_three = (TextView) tabThree.findViewById(R.id.txt_image);
        text_view_three.setText("Events");
        text_view_three.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        tabThree.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        tabLayout.getTabAt(2).setCustomView(tabThree);

        LinearLayout tabFour = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        TextView text_view_four = (TextView) tabFour.findViewById(R.id.txt_image);
        text_view_four.setText("Twitter");
        text_view_four.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        tabFour.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        tabLayout.getTabAt(3).setCustomView(tabFour);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout selectedTab = (LinearLayout) tab.getCustomView();
                /*selectedTab.setLayoutParams(new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 45));*/
                TextView textview = (TextView) selectedTab.findViewById(R.id.txt_image);
                textview.setTextColor(getResources().getColor(R.color.white));
                selectedTab.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_image));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout selectedTab = (LinearLayout) tab.getCustomView();
                /*selectedTab.setLayoutParams(new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 45));*/
                TextView textview = (TextView) selectedTab.findViewById(R.id.txt_image);
                textview.setTextColor(getResources().getColor(R.color.blackColor));
                selectedTab.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
