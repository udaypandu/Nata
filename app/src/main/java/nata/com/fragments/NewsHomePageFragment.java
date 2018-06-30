package nata.com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.NewsRecycleAdapter;
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
 * Created by Shankar Pilli
 */

public class NewsHomePageFragment extends Fragment implements IAsyncCaller {
    public static final String TAG = "NewsHomePageFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView tv_no_news;
    private RecyclerView recycler_view;

    private ArrayList<NewsModel> mNewsModelsList;
    private ArrayList<NewsModel> mNewsModelsNewList;
    private boolean endScroll = false;
    private NewsRecycleAdapter newsListAdapter;

    private int mPageNumber = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
      //  mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.Latest_updates));
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_news_home_page_recycle, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {
        recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        tv_no_news = (TextView) rootView.findViewById(R.id.tv_no_news);
        getNewsData("1");
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

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof NewsModel) {
                    NewsModel mNewsModel = (NewsModel) model;
                    if (mNewsModelsList == null) {
                        if (mNewsModel.getNewsModels() == null) {
                            tv_no_news.setVisibility(View.VISIBLE);
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
                            if (newsListAdapter == null) {
                                setDataToList();
                            }
                        }
                    } else {
                        recycler_view.setVisibility(View.VISIBLE);
                        tv_no_news.setVisibility(View.GONE);
                        if (mNewsModel.getNewsModels() != null && mNewsModel.getNewsModels().size() > 0) {
                            mNewsModelsList.addAll(mNewsModel.getNewsModels());
                            mNewsModelsNewList.addAll(mNewsModel.getNewsModels());
                            if (newsListAdapter == null) {
                                setDataToList();
                            } else {
                                newsListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            endScroll = true;
                        }
                    }
                }
            }
        }
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
