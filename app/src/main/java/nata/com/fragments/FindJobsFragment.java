package nata.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.NewsRecycleAdapter;
import nata.com.adapters.NewsRecycleVideoAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.Model;
import nata.com.models.NewsModel;
import nata.com.nata.R;
import nata.com.parsers.NewsesParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindJobsFragment extends Fragment implements View.OnClickListener, IAsyncCaller{

    public static final String TAG = "FindJobsFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView tv_no_news;
    private RecyclerView recycler_view;
    private RecyclerView recycler_view_video;
    private RecyclerView recycler_view_event;

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
    private TextView txt_video;
    private TextView txt_event;


    private ArrayList<NewsModel> mNewsModelsList;
    private ArrayList<NewsModel> mNewsModelsNewList;
    private boolean endScroll = false;
    private NewsRecycleAdapter newsListAdapter;
    private NewsRecycleAdapter newsListAdapterEvent;
    private NewsRecycleVideoAdapter newsListAdapterVideo;



    private String mFindJobsType = "";
    private String mFindJobsSearch = "";
    private String mFindJobsCategory = "";
    private String mFindJobsGroup = "";
    private String mFindJobsFrom = "";

    private int aaTotalCount, aaVisibleCount, aaFirstVisibleItem;
    private int mPageNumber = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        Bundle bundle = getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.app_name).toUpperCase());
        if (rootView != null) {

            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_job_openings, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recycler_view_video = (RecyclerView) rootView.findViewById(R.id.recycler_view_video);
        recycler_view_event = (RecyclerView) rootView.findViewById(R.id.recycler_view_event);
        tv_no_news = (TextView) rootView.findViewById(R.id.tv_no_news);
        txt_event = (TextView) rootView.findViewById(R.id.txt_event);
        txt_video = (TextView) rootView.findViewById(R.id.txt_video);
        getNewsData("1");
        getNewsDataEvent("1");
        getVideosData("1");
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

    private void getNewsDataEvent(String pageNo) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put(Constants.TYPE, "1");
        paramMap.put("page_no", pageNo);
        paramMap.put("page_size", "10");
        NewsesParser mFindJobParser = new NewsesParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.NEWS_HOME_URL, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mFindJobParser);
        Utility.execute(serverIntractorAsync);
    }

    private void getNewsData(String pageNo) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put(Constants.TYPE, "2");
        paramMap.put("page_no", pageNo);
        paramMap.put("page_size", "10");
        NewsesParser mFindJobParser = new NewsesParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.NEWS_HOME_URL, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mFindJobParser);
        Utility.execute(serverIntractorAsync);
    }

    private void getVideosData(String pageNo) {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        paramMap.put(Constants.TYPE, "3");
        paramMap.put("page_no", pageNo);
        paramMap.put("page_size", "10");
        NewsesParser mFindJobParser = new NewsesParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.NEWS_HOME_URL, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mFindJobParser);
        Utility.execute(serverIntractorAsync);
    }


    public void setFooterData() {


        txt_position_one = (TextView) rootView.findViewById(R.id.txt_position_one);
        txt_position_two = (TextView) rootView.findViewById(R.id.txt_position_two);
        txt_position_three = (TextView) rootView.findViewById(R.id.txt_position_three);
        txt_position_four = (TextView) rootView.findViewById(R.id.txt_position_four);

        txt_position_one.setText(Utility.getResourcesString(getActivity(), R.string.services_small));
        txt_position_two.setText(Utility.getResourcesString(getActivity(), R.string.discussions_small));
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

        txt_position_one_icon.setText(Utility.getResourcesString(mParent, R.string.post_job_icon));
        txt_position_two_icon.setText(Utility.getResourcesString(mParent, R.string.jobs_applied_icon));
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
                Utility.navigateDashBoardFragment(new JobPostingFragment(), JobPostingFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_two:
                Utility.navigateDashBoardFragment(new JobsAppliedFragment(), JobsAppliedFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_three:
                Utility.navigateDashBoardFragment(new MyPostFragment(), MyPostFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_four:
                Utility.navigateDashBoardFragment(new FiltersFragment(), FiltersFragment.TAG, null, mParent);
                break;
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof NewsModel) {
                    NewsModel mNewsModel = (NewsModel) model;
                    if (mNewsModelsList == null) {
                        if (mNewsModel.getNewsModels() == null) {
                            tv_no_news.setVisibility(View.VISIBLE);
                            recycler_view_event.setVisibility(View.GONE);
                            recycler_view_video.setVisibility(View.GONE);
                            recycler_view.setVisibility(View.GONE);
                        } else {
                            tv_no_news.setVisibility(View.GONE);
                            recycler_view.setVisibility(View.VISIBLE);
                            if (mNewsModelsList == null) {
                                mNewsModelsList = new ArrayList<>();
                            }
                            if (mNewsModelsNewList == null) {
                                mNewsModelsNewList = new ArrayList<>();
                            }
                            mNewsModelsList.addAll(mNewsModel.getNewsModels());
                            mNewsModelsNewList.addAll(mNewsModel.getNewsModels());
                            //mNewsModelsList.remove(0);
                            if (newsListAdapter == null ) {
                                setDataToList();
                            }
                            if (newsListAdapterEvent == null) {
                                setDataToListEvent();
                            }
                            if (newsListAdapterVideo==null) {
                                setDataToListVideos();
                            }
                                txt_video.setText(R.string.videos);
                                txt_event.setText(R.string.events);

                        }
                    } else {
                        recycler_view.setVisibility(View.VISIBLE);
                        tv_no_news.setVisibility(View.GONE);
                        if (mNewsModel.getNewsModels() != null && mNewsModel.getNewsModels().size() > 0) {
                            mNewsModelsList.addAll(mNewsModel.getNewsModels());
                            mNewsModelsNewList.addAll(mNewsModel.getNewsModels());
                            txt_video.setText(R.string.videos);
                            txt_event.setText(R.string.events);
                            if (newsListAdapter == null ) {
                                setDataToList();
                            }
                            if (newsListAdapterEvent == null) {
                                setDataToListEvent();
                            }
                            if (newsListAdapterVideo==null) {
                                setDataToListVideos();
                            } else {
                                newsListAdapter.notifyDataSetChanged();
                                newsListAdapterVideo.notifyDataSetChanged();
                                newsListAdapterEvent.notifyDataSetChanged();
                            }
                        } else {
                            endScroll = true;
                        }
                    }
                }
            }
        }
    }
    private void setDataToListEvent() {
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mParent.getApplicationContext());
        newsListAdapterEvent = new NewsRecycleAdapter(mParent, mNewsModelsList, TAG);
        recycler_view_event.setLayoutManager(mLayoutManager);
        recycler_view_event.setItemAnimator(new DefaultItemAnimator());
        recycler_view_event.setAdapter(newsListAdapterEvent);
        recycler_view_event.setNestedScrollingEnabled(false);
        recycler_view_event.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recycler_view_event.getLayoutManager()).findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition > 0
                        && !endScroll) {
                    mPageNumber = mPageNumber + 1;
                    getNewsData("" + mPageNumber);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setDataToListVideos() {
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mParent.getApplicationContext());
        newsListAdapterVideo = new NewsRecycleVideoAdapter(mParent, mNewsModelsList, TAG);
        recycler_view_video.setLayoutManager(mLayoutManager);
        recycler_view_video.setItemAnimator(new DefaultItemAnimator());
        recycler_view_video.setAdapter(newsListAdapterVideo);
        recycler_view_video.setNestedScrollingEnabled(false);
        recycler_view_video.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recycler_view_video.getLayoutManager()).findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition > 0
                        && !endScroll) {
                    mPageNumber = mPageNumber + 1;
                    getNewsData("" + mPageNumber);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setDataToList() {
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mParent.getApplicationContext());
        newsListAdapter = new NewsRecycleAdapter(mParent, mNewsModelsList, TAG);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(newsListAdapter);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recycler_view.getLayoutManager()).findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition > 0
                        && !endScroll) {
                    mPageNumber = mPageNumber + 1;
                    getNewsData("" + mPageNumber);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
