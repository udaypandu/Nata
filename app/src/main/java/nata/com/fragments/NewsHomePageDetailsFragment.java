package nata.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.NewsDetailsAdapter;
import nata.com.models.NewsModel;
import nata.com.nata.R;
import nata.com.utility.Constants;
import nata.com.utility.Utility;


/**
 * Created by Shankar Pilli on 11/4/2016
 */
public class NewsHomePageDetailsFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "NewsDetailsFragment";
    private DashboardActivity mParent;
    private View rootView;
    private ViewPager viewpager;

    private TextView txt_left_icon;
    private TextView txt_right_icon;

    /*private LinearLayout ll_careers;
    private LinearLayout ll_ask_help_footer;
    private LinearLayout ll_financial_aid;
    private LinearLayout ll_forum;*/

    private int mSelectedPosition = -1;
    private NewsModel newsModel;
    private NewsDetailsAdapter newsDetailsAdapter;
    private String mFrom = "";

    /*private TextView txt_careers_icon;
    private TextView txt_askforhelp_icon;
    private TextView txt_financial_aid_icon;
    private TextView txt_our_forum_icon;

    private TextView txt_careers;
    private TextView txt_askforhelp;
    private TextView txt_financial_aid;
    private TextView txt_our_forum;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mSelectedPosition = getArguments().getInt("current_selected_position");
        mFrom = getArguments().getString("from");
        if (mFrom.equalsIgnoreCase("News"))
            newsModel = (NewsModel) getArguments().getSerializable(Constants.PREF_KEY_NEWS_MODEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.latest_news));
        rootView = inflater.inflate(R.layout.fragment_news_details, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        txt_left_icon = (TextView) rootView.findViewById(R.id.txt_left_icon);
        txt_right_icon = (TextView) rootView.findViewById(R.id.txt_right_icon);

        viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        /*ll_careers = (LinearLayout) rootView.findViewById(R.id.ll_careers);
        ll_ask_help_footer = (LinearLayout) rootView.findViewById(R.id.ll_ask_help_footer);
        ll_financial_aid = (LinearLayout) rootView.findViewById(R.id.ll_financial_aid);
        ll_forum = (LinearLayout) rootView.findViewById(R.id.ll_forum);*/

        txt_right_icon.setTypeface(Utility.setTypeFace_fontawesome(getActivity()));
        txt_left_icon.setTypeface(Utility.setTypeFace_fontawesome(getActivity()));

        /*txt_careers_icon = (TextView) rootView.findViewById(R.id.txt_careers_icon);
        txt_askforhelp_icon = (TextView) rootView.findViewById(R.id.txt_askforhelp_icon);
        txt_our_forum_icon = (TextView) rootView.findViewById(R.id.txt_our_forum_icon);
        txt_financial_aid_icon = (TextView) rootView.findViewById(R.id.txt_financial_aid_icon);

        txt_careers = (TextView) rootView.findViewById(R.id.txt_careers);
        txt_askforhelp = (TextView) rootView.findViewById(R.id.txt_askforhelp);
        txt_financial_aid = (TextView) rootView.findViewById(R.id.txt_financial_aid);
        txt_our_forum = (TextView) rootView.findViewById(R.id.txt_our_forum);

        txt_careers_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_our_forum_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_financial_aid_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_askforhelp_icon.setTypeface(Utility.setTypeFace_Image(mParent));

        txt_careers.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_askforhelp.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_financial_aid.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_our_forum.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));

        ll_careers.setOnClickListener(this);
        ll_ask_help_footer.setOnClickListener(this);
        ll_financial_aid.setOnClickListener(this);
        ll_forum.setOnClickListener(this);*/

        txt_right_icon.setOnClickListener(this);
        txt_left_icon.setOnClickListener(this);

        if (mFrom.equalsIgnoreCase("News")) {
            newsDetailsAdapter = new NewsDetailsAdapter(getActivity(), newsModel.getNewsModels());
            viewpager.setAdapter(newsDetailsAdapter);
        }

        viewpager.setCurrentItem(mSelectedPosition);
        updateUI(mSelectedPosition);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mSelectedPosition = position;
                viewpager.setCurrentItem(mSelectedPosition);
                updateUI(mSelectedPosition);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void updateLeftAdapter() {
        mSelectedPosition = mSelectedPosition - 1;
        viewpager.setCurrentItem(mSelectedPosition);
        updateUI(mSelectedPosition);
    }

    private void updateRightAdapter() {
        mSelectedPosition = mSelectedPosition + 1;
        viewpager.setCurrentItem(mSelectedPosition);
        updateUI(mSelectedPosition);
    }


    private void updateUI(int selectedPosition) {
        if (mFrom.equalsIgnoreCase("News")) {
            if (selectedPosition == 0 && newsModel.getNewsModels().size() == 1) {
                txt_left_icon.setVisibility(View.INVISIBLE);
                txt_right_icon.setVisibility(View.INVISIBLE);
            } else if (selectedPosition == 0) {
                txt_left_icon.setVisibility(View.INVISIBLE);
                txt_right_icon.setVisibility(View.VISIBLE);
            } else if (selectedPosition == newsModel.getNewsModels().size() - 1) {
                txt_left_icon.setVisibility(View.VISIBLE);
                txt_right_icon.setVisibility(View.INVISIBLE);
            } else {
                txt_left_icon.setVisibility(View.VISIBLE);
                txt_right_icon.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.ll_careers:
                if (Utility.isLogin(getActivity())) {
                    Utility.navigateDashBoardFragment(new CareersFragment(), CareersFragment.TAG, null, mParent);
                } else {
                    Utility.navigateTOLogin(getActivity());
                }
                break;
            case R.id.ll_ask_help_footer:
                if (Utility.isLogin(getActivity())) {
                    Utility.navigateDashBoardFragment(new AskForHelpFragment(), AskForHelpFragment.TAG, null, mParent);
                } else {
                    Utility.navigateTOLogin(getActivity());
                }
                break;
            case R.id.ll_financial_aid:
                if (Utility.isLogin(getActivity())) {
                    Utility.navigateDashBoardFragment(new FinancialAidFragment(), FinancialAidFragment.TAG, null, mParent);
                } else {
                    Utility.navigateTOLogin(getActivity());
                }
                break;
            case R.id.ll_forum:
                if (Utility.isLogin(getActivity())) {
                    Utility.navigateDashBoardFragment(new PopularOurForumFragment(), PopularOurForumFragment.TAG, null, mParent);
                } else {
                    Utility.navigateTOLogin(getActivity());
                }
                break;*/
            case R.id.txt_right_icon:
                updateRightAdapter();
                break;
            case R.id.txt_left_icon:
                updateLeftAdapter();
                break;
        }
    }
}
