package nata.com.adapters;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;


import java.util.ArrayList;

import nata.com.activities.DashboardActivity;
import nata.com.fragments.NewsHomePageDetailsFragment;
import nata.com.fragments.NewsHomePageVideosFragment;
import nata.com.models.NewsModel;
import nata.com.nata.R;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by Shankar on 10/26/2017.
 */

public class NewsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<NewsModel> mNewsModelsList;
    private DashboardActivity dashboardActivity;
    private Typeface mLatoRegular;
    private String mFrom;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public NewsRecycleAdapter(DashboardActivity dashboardActivity, ArrayList<NewsModel> mNewsModelsList, String mFrom) {
        this.dashboardActivity = dashboardActivity;
        this.mNewsModelsList = mNewsModelsList;
        mLatoRegular = Utility.setTypeFace_Lato_Regular(dashboardActivity);
        this.mFrom = mFrom;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        if (viewType == TYPE_ITEM) {
            layout = R.layout.news_home_page_item;
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(layout, parent, false);
            return new ViewHolderItem(view);
        } else if (viewType == TYPE_HEADER) {
            layout = R.layout.news_item_layout;
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(layout, parent, false);
            return new ViewHolderHeader(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderItem) {
            final ViewHolderItem mNewsListHolder = (ViewHolderItem) holder;
            mNewsListHolder.txt_date.setTypeface(mLatoRegular);
            mNewsListHolder.txt_matter.setTypeface(mLatoRegular);

            NewsModel mNewsModel = getItem(position);
            if (mFrom.equalsIgnoreCase(NewsHomePageVideosFragment.TAG)) {
                mNewsListHolder.img_video.setVisibility(View.VISIBLE);
            } else {
                mNewsListHolder.img_video.setVisibility(View.GONE);
            }

            if (!Utility.isValueNullOrEmpty(mNewsModel.getImage_name()))
               Utility.URLProfilePicLoading(mNewsListHolder.img_news_home,
                        mNewsModel.getImage_name(), null, R.drawable.place_holder);
            try {
                String response = new String(mNewsModel.getDescription());
                mNewsListHolder.txt_matter.setText(Html.fromHtml(response));
                mNewsListHolder.txt_matter.setTypeface(mLatoRegular);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mNewsListHolder.txt_date.setText(Utility.getRequiredDateTime(mNewsModel.getCreated()));
            mNewsListHolder.txt_date.setTypeface(mLatoRegular);

            if (position % 5 == 0 && position != 0) {
                if (Constants.logAddsOnOrOff)
                    mNewsListHolder.mAdView.setVisibility(View.VISIBLE);
                // Set its video options.
                mNewsListHolder.mAdView.setVideoOptions(new VideoOptions.Builder()
                        .setStartMuted(true)
                        .build());

                mNewsListHolder.mVideoController = mNewsListHolder.mAdView.getVideoController();
                mNewsListHolder.mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                    @Override
                    public void onVideoEnd() {
                        super.onVideoEnd();
                    }
                });

                mNewsListHolder.mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        if (mNewsListHolder.mVideoController.hasVideoContent()) {
                            Log.d("sdf", "Received an ad that contains a video asset.");
                        } else {
                            Log.d("sdf", "Received an ad that does not contain a video asset.");
                        }
                    }
                });

                mNewsListHolder.mAdView.loadAd(new AdRequest.Builder().build());
            } else {
                mNewsListHolder.mAdView.setVisibility(View.GONE);
            }

        } else if (holder instanceof ViewHolderHeader) {
            ViewHolderHeader viewHolderHeader = (ViewHolderHeader) holder;
            NewsModel newsModel = getItem(position);
            if (newsModel != null && !Utility.isValueNullOrEmpty(newsModel.getImage_name()))
                Utility.URLProfilePicLoading(viewHolderHeader.img_news_home,
                      newsModel.getImage_name(), null, R.drawable.place_holder);
            viewHolderHeader.txt_home_heading.setText(newsModel.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mNewsModelsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private NewsModel getItem(int position) {
        return mNewsModelsList.get(position);
    }

    private class ViewHolderItem extends RecyclerView.ViewHolder {
        private ImageView img_news_home;
        private TextView txt_date;
        private TextView txt_matter;
        private ImageView img_video;
        private LinearLayout ll_total_layout;

        private NativeExpressAdView mAdView;
        private VideoController mVideoController;

        public ViewHolderItem(View itemView) {
            super(itemView);

            img_news_home = (ImageView) itemView.findViewById(R.id.img_news_home);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            txt_matter = (TextView) itemView.findViewById(R.id.txt_matter);
            img_video = (ImageView) itemView.findViewById(R.id.img_video);
            ll_total_layout = (LinearLayout) itemView.findViewById(R.id.ll_total_layout);

            ll_total_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("current_selected_position", getAdapterPosition());
                    bundle.putString("from", "News");
                    NewsModel newsModel = new NewsModel();
                    newsModel.setNewsModels(mNewsModelsList);
                    bundle.putSerializable(Constants.PREF_KEY_NEWS_MODEL, newsModel);
                    Utility.navigateDashBoardFragment(new NewsHomePageDetailsFragment(), NewsHomePageDetailsFragment.TAG, bundle,
                            dashboardActivity);
                }
            });

            mAdView = (NativeExpressAdView) itemView.findViewById(R.id.adView);
        }
    }

    private class ViewHolderHeader extends RecyclerView.ViewHolder {
        private ImageView img_news_home;
        private TextView txt_home_heading;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            img_news_home = (ImageView) itemView.findViewById(R.id.img_news_home);
            txt_home_heading = (TextView) itemView.findViewById(R.id.txt_home_heading);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("current_selected_position", getAdapterPosition());
                    bundle.putString("from", "News");
                    NewsModel newsModel = new NewsModel();
                    newsModel.setNewsModels(mNewsModelsList);
                    bundle.putSerializable(Constants.PREF_KEY_NEWS_MODEL, newsModel);
                    Utility.navigateDashBoardFragment(new NewsHomePageDetailsFragment(), NewsHomePageDetailsFragment.TAG, bundle,
                            dashboardActivity);
                }
            });
        }
    }

}
