package nata.com.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;

import java.util.ArrayList;

import nata.com.models.NewsModel;
import nata.com.nata.R;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by shankar on 11/3/2016.
 */

public class NewsDetailsAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<NewsModel> newsModels;
    private LayoutInflater mLayoutInflater;

    public NewsDetailsAdapter(Context context, ArrayList<NewsModel> newsModels) {
        mContext = context;
        this.newsModels = newsModels;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return newsModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_news_details_item, container, false);

        TextView textView = (TextView) itemView.findViewById(R.id.txt_first_article_header);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_first_article);
        ProgressBar progress = (ProgressBar) itemView.findViewById(R.id.progress);
        TextView txt_details = (TextView) itemView.findViewById(R.id.txt_details);
        LinearLayout images = (LinearLayout) itemView.findViewById(R.id.images);
        NativeExpressAdView mAdView = (NativeExpressAdView) itemView.findViewById(R.id.adView);
        final VideoController mVideoController;

        if (!Utility.isValueNullOrEmpty(newsModels.get(position).getImage_name()))
            Utility.URLProfilePicLoading(imageView,
                    newsModels.get(position).getImage_name(), null, R.drawable.place_holder);
        textView.setText(Html.fromHtml(newsModels.get(position).getTitle()));
        txt_details.setText(Html.fromHtml(newsModels.get(position).getDescription()));

        imageView.setId(position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = v.getId();
                showFitDialog(newsModels.get(position).getImage_name(), mContext);
            }
        });

        if (newsModels.get(position).getOther_images() != null && newsModels.get(position).getOther_images().size() > 0) {
            images.setVisibility(View.VISIBLE);
            for (int i = 0; i < newsModels.get(position).getOther_images().size(); i++) {
                View image_view = mLayoutInflater.inflate(R.layout.image_view, container, false);
                ImageView imageViewInner = (ImageView) image_view.findViewById(R.id.image_layout);
                if (!Utility.isValueNullOrEmpty(newsModels.get(position).getOther_images().get(i)))
                    Utility.URLProfilePicLoading(imageViewInner,
                            newsModels.get(position).getOther_images().get(i), null, R.drawable.place_holder);
                images.addView(image_view);
            }

        } else {
            images.setVisibility(View.GONE);
        }

        if (Constants.logAddsOnOrOff) {
            mAdView.setVisibility(View.VISIBLE);
            mAdView.setVideoOptions(new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build());

            // The VideoController can be used to get lifecycle events and info about an ad's video
            // asset. One will always be returned by getVideoController, even if the ad has no video
            // asset.
            mVideoController = mAdView.getVideoController();
            mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (mVideoController.hasVideoContent()) {
                        Log.d("sdf", "Received an ad that contains a video asset.");
                    } else {
                        Log.d("sdf", "Received an ad that does not contain a video asset.");
                    }
                }
            });
            mAdView.loadAd(new AdRequest.Builder().build());
        } else {
            mAdView.setVisibility(View.GONE);
        }

        container.addView(itemView);

        return itemView;
    }

    public void showFitDialog(String url, Context context) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_fitcenter);
        dialog.setCanceledOnTouchOutside(false);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.image);
        Utility.URLProfilePicLoading(imageView, url, null, R.drawable.place_holder);
        dialog.show();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
