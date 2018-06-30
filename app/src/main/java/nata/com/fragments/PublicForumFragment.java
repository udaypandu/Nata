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
import nata.com.models.Model;
import nata.com.models.PublicForumModel;
import nata.com.nata.R;
import nata.com.parsers.PublicForumParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicForumFragment extends Fragment implements IAsyncCaller, View.OnClickListener, AbsListView.OnScrollListener,
        IUpdateNewData {

    public static final String TAG = "PublicForumFragment";
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
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_public_forum, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        listView = (ListView) rootView.findViewById(R.id.list_view);
        tv_no_posts_in_this_forum = (TextView) rootView.findViewById(R.id.tv_no_posts_in_this_forum);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        listView.setOnScrollListener(this);
        getPublicForums("1");
    }

    private void getPublicForums(String pageNo) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        PublicForumParser mPublicForumParser = new PublicForumParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.PUBLIC_TOPICS_FORUM + ForumTabDetailFragment.forumModel.getId() + "/" + pageNo + "/20"
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
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FROM, TAG);
        Utility.navigateDashBoardFragment(new PostForumFragment(), PostForumFragment.TAG, bundle, mParent);
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
