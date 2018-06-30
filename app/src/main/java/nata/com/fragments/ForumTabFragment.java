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
 * Created by Shankar on 03/12/2017.
 */

public class ForumTabFragment extends Fragment {

    public static final String TAG = "ForumTabFragment";
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
        mParent.invalidateOptionsMenu();
        // getActivity().setTheme(R.style.AppTheme_NoActionBar);
        //mParent.rl_layout.setVisibility(View.GONE);
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.community_forum));
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
        }
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_forum_tab, container, false);
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
}
