package nata.com.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.CommunityForumViewAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.interfaces.IUpdateNewData;
import nata.com.models.ForumListModel;
import nata.com.models.ForumModel;
import nata.com.models.Model;
import nata.com.models.PublicForumModel;
import nata.com.nata.R;
import nata.com.parsers.ForumParser;
import nata.com.parsers.PublicForumParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by Shankar Pilli on 11/4/2016
 */
public class AskForHelpFragment extends Fragment implements IAsyncCaller, View.OnClickListener, AbsListView.OnScrollListener, IUpdateNewData
{

    public static final String TAG = "AskForHelpFragment";
    private DashboardActivity mParent;
    private View rootView;

    private ListView listView;
    private TextView tv_no_posts_in_this_forum;
    private ArrayList<PublicForumModel> publicForumModels;

    private FloatingActionButton fab;

    private CommunityForumViewAdapter communityForumViewAdapter;

    private int aaTotalCount, aaVisibleCount, aaFirstVisibleItem;
    private int mPageNumber = 1;
    private boolean endScroll = false;
    private static IUpdateNewData iUpdateNewData;

    private TextView tv_moderate;
    private TextView tv_my_quires;

    public static IUpdateNewData getInstance() {
        return iUpdateNewData;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iUpdateNewData = this;
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.services));
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_ask_for_help, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        listView = (ListView) rootView.findViewById(R.id.list_view);
        tv_no_posts_in_this_forum = (TextView) rootView.findViewById(R.id.tv_no_posts_in_this_forum);
        tv_my_quires = (TextView) rootView.findViewById(R.id.tv_my_quires);
        tv_moderate = (TextView) rootView.findViewById(R.id.tv_moderate);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        tv_moderate.setOnClickListener(this);
        tv_my_quires.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        tv_moderate.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        getPublicForums("1");
        getModerateGroupList();
    }

    private void getModerateGroupList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        ForumParser mForumParser = new ForumParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.ASK_MY_HELP_MODERATE_GROUPS
                , paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mForumParser);
        Utility.execute(serverIntractorAsync);
    }

    private void getPublicForums(String pageNo) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        PublicForumParser mPublicForumParser = new PublicForumParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.ASK_MY_HELP_LIST + "/" + pageNo + "/20"
                , paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mPublicForumParser);
        Utility.execute(serverIntractorAsync);
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof ForumListModel) {
                    ForumListModel mForumListModel = (ForumListModel) model;
                    if (publicForumModels == null) {
                        if (mForumListModel.getPublicForumModels() == null) {
                            tv_no_posts_in_this_forum.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        } else {
                            tv_no_posts_in_this_forum.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            if (publicForumModels == null) {
                                publicForumModels = new ArrayList<>();
                            }
                            publicForumModels.addAll(mForumListModel.getPublicForumModels());
                            if (communityForumViewAdapter == null) {
                                setDataToTheList();
                            }
                        }
                    } else {
                        listView.setVisibility(View.VISIBLE);
                        tv_no_posts_in_this_forum.setVisibility(View.GONE);
                        if (mForumListModel.getPublicForumModels() != null && mForumListModel.getPublicForumModels().size() > 0) {
                            publicForumModels.addAll(mForumListModel.getPublicForumModels());
                            if (communityForumViewAdapter == null) {
                                setDataToTheList();
                            } else {
                                communityForumViewAdapter.notifyDataSetChanged();
                            }
                        } else {
                            endScroll = true;
                        }
                    }
                } else if (model instanceof ForumModel) {
                    ForumModel mForumModel = (ForumModel) model;
                    if (mForumModel != null && mForumModel.getForumModels().size() > 0) {
                        tv_moderate.setVisibility(View.VISIBLE);
                    } else {
                        tv_moderate.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    private void setDataToTheList() {
        if (publicForumModels != null && publicForumModels.size() > 0) {
            communityForumViewAdapter = new CommunityForumViewAdapter(mParent, publicForumModels, TAG);
            listView.setVisibility(View.VISIBLE);
            tv_no_posts_in_this_forum.setVisibility(View.GONE);
            listView.setAdapter(communityForumViewAdapter);
        } else {
            listView.setVisibility(View.GONE);
            tv_no_posts_in_this_forum.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_moderate:
                Utility.navigateDashBoardFragment(new AskForHelpMyModerateFragment(), AskForHelpMyModerateFragment.TAG, null, mParent);
                break;
            case R.id.fab:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FROM, TAG);
                Utility.navigateDashBoardFragment(new PostAskForHelpFragment(), PostAskForHelpFragment.TAG, bundle, mParent);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            isScrollCompleted();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        aaTotalCount = totalItemCount;
        aaVisibleCount = visibleItemCount;
        aaFirstVisibleItem = firstVisibleItem;
    }

    private void isScrollCompleted() {
        if (aaTotalCount == (aaFirstVisibleItem + aaVisibleCount) && !endScroll) {
            if (Utility.isNetworkAvailable(getActivity())) {
                mPageNumber = mPageNumber + 1;
                getPublicForums("" + mPageNumber);
                Utility.showLog("mPageNumber", "mPageNumber : " + mPageNumber);
            } else {
                Utility.showSettingDialog(
                        getActivity(),
                        getActivity().getResources().getString(
                                R.string.no_internet_msg),
                        getActivity().getResources().getString(
                                R.string.no_internet_title),
                        Utility.NO_INTERNET_CONNECTION).show();
            }
        } else {
            if (listView.getAdapter() != null) {
                if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1 &&
                        listView.getChildAt(listView.getChildCount() - 1).getBottom() <= listView.getHeight()) {
                    Utility.showToastMessage(getActivity(), Utility.getResourcesString(getActivity(),
                            R.string.no_more_data_to_display));
                }
            }
        }
    }

    @Override
    public void updateNewData() {
        mPageNumber = 1;
        publicForumModels = null;
        communityForumViewAdapter = null;
        getPublicForums("1");
    }
}