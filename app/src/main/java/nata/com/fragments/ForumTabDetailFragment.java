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


import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.ViewPagerAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.ForumModel;
import nata.com.models.IsMemberModel;
import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.parsers.IsMemberParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by Shankar on 03/12/2017.
 */

public class ForumTabDetailFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = "ForumTabDetailFragment";
    private DashboardActivity mParent;
    private Toolbar mToolbar;
    private View rootView;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static ForumModel forumModel;
    private IsMemberModel mIsMemberModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.FORUM_MODEL)) {
            forumModel = (ForumModel) bundle.getSerializable(Constants.FORUM_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        // getActivity().setTheme(R.style.AppTheme_NoActionBar);
        mParent.getSupportActionBar().setTitle(forumModel.getName().toUpperCase() + " " + Utility.getResourcesString(getActivity(), R.string.forum_caps));
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
        checkIsMember();
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(0);
        /*viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();*/
    }

    private void checkIsMember() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        IsMemberParser mIsMemberParser = new IsMemberParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.IS_MEMBER_FORUM + ForumTabDetailFragment.forumModel.getId(), paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mIsMemberParser);
        Utility.execute(serverIntractorAsync);
    }


    private void setupTabIcons(boolean isMember) {

        if (isMember) {
            LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            TextView textview = (TextView) tabOne.findViewById(R.id.txt_image);
            textview.setText(Utility.getResourcesString(mParent, R.string.public_name));
            textview.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
            textview.setTextColor(getResources().getColor(R.color.colorPrimary));
            textview.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
            tabLayout.getTabAt(0).setCustomView(tabOne);

            LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            TextView text_view_two = (TextView) tabTwo.findViewById(R.id.txt_image);
            text_view_two.setText(Utility.getResourcesString(mParent, R.string.private_name));
            text_view_two.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
            text_view_two.setTextColor(Utility.getColor(mParent, R.color.white));
            tabTwo.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
            tabLayout.getTabAt(1).setCustomView(tabTwo);

            LinearLayout tabThree = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            TextView text_view_three = (TextView) tabThree.findViewById(R.id.txt_image);
            text_view_three.setText(Utility.getResourcesString(mParent, R.string.moderators));
            text_view_three.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
            text_view_three.setTextColor(Utility.getColor(mParent, R.color.white));
            tabThree.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
            tabLayout.getTabAt(2).setCustomView(tabThree);

            LinearLayout tabFour = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            TextView text_view_Four = (TextView) tabFour.findViewById(R.id.txt_image);
            text_view_Four.setText(Utility.getResourcesString(mParent, R.string.members));
            text_view_Four.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
            text_view_Four.setTextColor(Utility.getColor(mParent, R.color.white));
            tabFour.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
            tabLayout.getTabAt(3).setCustomView(tabFour);
        } else {
            LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            TextView textview = (TextView) tabOne.findViewById(R.id.txt_image);
            textview.setText(Utility.getResourcesString(mParent, R.string.public_name));
            textview.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
            textview.setTextColor(getResources().getColor(R.color.colorPrimary));
            tabOne.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
            tabLayout.getTabAt(0).setCustomView(tabOne);

            LinearLayout tabThree = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            TextView text_view_three = (TextView) tabThree.findViewById(R.id.txt_image);
            text_view_three.setText(Utility.getResourcesString(mParent, R.string.moderators));
            text_view_three.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
            text_view_three.setTextColor(Utility.getColor(mParent, R.color.white));
            tabThree.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
            tabLayout.getTabAt(1).setCustomView(tabThree);

        }

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

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof IsMemberModel) {
                    mIsMemberModel = (IsMemberModel) model;
                    setupViewPager(viewPager, mIsMemberModel.isMember());
                } else {
                    setupViewPager(viewPager, false);
                }
            } else {
                setupViewPager(viewPager, false);
            }
        } else {
            setupViewPager(viewPager, false);
        }
    }

    private void setupViewPager(ViewPager viewPager, boolean isMember) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        if (isMember) {
            adapter.addFrag(new PublicForumFragment(), "ONE");
            adapter.addFrag(new PrivateForumFragment(), "TWO");
            adapter.addFrag(new ModeratorsFragment(), "THREE");
            adapter.addFrag(new MembersFragment(), "FOUR");
        } else {
            adapter.addFrag(new PublicForumFragment(), "ONE");
            adapter.addFrag(new ModeratorsFragment(), "TWO");
        }
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(isMember);
    }
}
