package nata.com.fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.OurForceAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.Model;
import nata.com.models.OurForceCountryListModel;
import nata.com.nata.R;
import nata.com.parsers.OurForceCountyParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

public class MyPostFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener, IAsyncCaller {
    public static final String TAG = "MyPostFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_our_force_counry;
    private ListView list_view;
    private OurForceCountryListModel mOurForceCountryListModel;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.members).toUpperCase());
        rootView = inflater.inflate(R.layout.fragment_my_post, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {

        txt_our_force_counry = (TextView) rootView.findViewById(R.id.txt_our_force);
        txt_our_force_counry.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_our_force_counry.setText("List of Countries");
        list_view = (ListView) rootView.findViewById(R.id.list_view);
        list_view.setOnItemClickListener(this);
        getCountriesList();
        //mSwipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);

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

    private void getCountriesList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        OurForceCountyParser mParser = new OurForceCountyParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.GET_MEMBERS, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.OUR_FORCE_ID, mOurForceCountryListModel.getOurForceCountryModels().get(position).getId());
        Utility.navigateDashBoardFragment(new OurForceFragment(), OurForceFragment.TAG, bundle
                , mParent);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof OurForceCountryListModel) {
                    mOurForceCountryListModel = (OurForceCountryListModel) model;
                    setDataToList();
                }
            }
        }
    }

    /**
     * This method is used to show the data
     */
    private void setDataToList() {
        OurForceAdapter ourForceAdapter = new OurForceAdapter(mParent, mOurForceCountryListModel.getOurForceCountryModels());
        list_view.setAdapter(ourForceAdapter);
    }

    public void setFooterData() {
        txt_position_one = (TextView) rootView.findViewById(R.id.txt_position_one);
        txt_position_two = (TextView) rootView.findViewById(R.id.txt_position_two);
        txt_position_three = (TextView) rootView.findViewById(R.id.txt_position_three);
        txt_position_four = (TextView) rootView.findViewById(R.id.txt_position_four);

        txt_position_one.setText(Utility.getResourcesString(getActivity(), R.string.update_small));
        txt_position_two.setText(Utility.getResourcesString(getActivity(), R.string.services_small));
        txt_position_three.setText(Utility.getResourcesString(getActivity(), R.string.discussions_small));
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
        txt_position_three_icon.setText(Utility.getResourcesString(mParent, R.string.jobs_applied_icon));
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
                Utility.navigateDashBoardFragment(new JobsAppliedFragment(), JobsAppliedFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_four:
                Utility.navigateDashBoardFragment(new FiltersFragment(), FiltersFragment.TAG, null, mParent);
                break;
        }
    }

}
