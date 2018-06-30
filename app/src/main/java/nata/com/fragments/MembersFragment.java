package nata.com.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
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
import nata.com.adapters.MembersForumViewAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.MembersListModel;
import nata.com.models.MembersModel;
import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.parsers.MembersForumParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class MembersFragment extends Fragment implements IAsyncCaller, AbsListView.OnScrollListener, View.OnClickListener {

    public static final String TAG = "MembersFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView tv_add_members;
    private TextView tv_members;
    private ListView list_members;
    private TextView tv_no_members_in_this_forum;

    private Typeface mLatoTypeFace;
    private ArrayList<MembersModel> membersModels;

    private MembersForumViewAdapter membersForumViewAdapter;
    private int aaTotalCount, aaVisibleCount, aaFirstVisibleItem;
    private int mPageNumber = 1;
    private boolean endScroll = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_members, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        mLatoTypeFace = Utility.setTypeFace_Lato_Regular(mParent);

        tv_add_members = (TextView) rootView.findViewById(R.id.tv_add_members);
        tv_add_members.setTypeface(mLatoTypeFace);
        tv_add_members.setOnClickListener(this);
        tv_members = (TextView) rootView.findViewById(R.id.tv_members);
        tv_members.setTypeface(mLatoTypeFace);
        list_members = (ListView) rootView.findViewById(R.id.list_members);
        tv_no_members_in_this_forum = (TextView) rootView.findViewById(R.id.tv_no_members_in_this_forum);
        tv_no_members_in_this_forum.setTypeface(mLatoTypeFace);

        getMembersList(1);
    }

    private void getMembersList(int pageNumber) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        MembersForumParser mMembersForumParser = new MembersForumParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.GET_MEMBERS_FORUM + ForumTabDetailFragment.forumModel.getId() + "/" + pageNumber + "/20", paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mMembersForumParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof MembersListModel) {
                    MembersListModel mMembersListModel = (MembersListModel) model;
                    tv_members.setText(mMembersListModel.getTotal());
                    if (membersModels == null) {
                        if (mMembersListModel.getMembersModels() == null) {
                            tv_no_members_in_this_forum.setVisibility(View.VISIBLE);
                            list_members.setVisibility(View.GONE);
                        } else {
                            tv_no_members_in_this_forum.setVisibility(View.GONE);
                            list_members.setVisibility(View.VISIBLE);
                            if (membersModels == null) {
                                membersModels = new ArrayList<>();
                            }
                            membersModels.addAll(mMembersListModel.getMembersModels());
                            if (membersForumViewAdapter == null) {
                                setMembersData();
                            }
                        }
                    } else {
                        list_members.setVisibility(View.VISIBLE);
                        tv_no_members_in_this_forum.setVisibility(View.GONE);
                        if (mMembersListModel.getMembersModels() != null && mMembersListModel.getMembersModels().size() > 0) {
                            membersModels.addAll(mMembersListModel.getMembersModels());
                            if (membersForumViewAdapter == null) {
                                setMembersData();
                            } else {
                                membersForumViewAdapter.notifyDataSetChanged();
                            }
                        } else {
                            endScroll = true;
                        }
                    }
                }
            }
        }
    }

    private void setMembersData() {
        if (membersModels != null && membersModels.size() > 0) {
            membersForumViewAdapter = new MembersForumViewAdapter(mParent, membersModels);
            list_members.setVisibility(View.VISIBLE);
            tv_no_members_in_this_forum.setVisibility(View.GONE);
            list_members.setAdapter(membersForumViewAdapter);
        } else {
            list_members.setVisibility(View.GONE);
            tv_no_members_in_this_forum.setVisibility(View.VISIBLE);
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
                getMembersList(mPageNumber);
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
            if (list_members.getAdapter() != null) {
                if (list_members.getLastVisiblePosition() == list_members.getAdapter().getCount() - 1 &&
                        list_members.getChildAt(list_members.getChildCount() - 1).getBottom() <= list_members.getHeight()) {
                    Utility.showToastMessage(getActivity(), Utility.getResourcesString(getActivity(),
                            R.string.no_more_data_to_display));
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_members:
                Bundle bundleWeb = new Bundle();
                bundleWeb.putString("URL", "http://kapuwelfare.com/forum/group-moderation.php?id=11");
                Utility.navigateDashBoardFragment(new WebViewFragment(), WebViewFragment.TAG, bundleWeb, mParent);
                break;
        }
    }
}
