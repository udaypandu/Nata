package nata.com.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;


import java.util.ArrayList;

import nata.com.activities.DashboardActivity;
import nata.com.fragments.ForumDetailFragment;
import nata.com.models.PublicForumModel;
import nata.com.nata.R;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by madhu on 26-Dec-16.
 */

public class CommunityForumViewAdapter  extends BaseAdapter {
    private DashboardActivity mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<PublicForumModel> publicForumModels;
    private Typeface mLatoRegular;
    private Typeface mFontAwesome;
    private String mFrom;

    public CommunityForumViewAdapter(DashboardActivity context, ArrayList<PublicForumModel> publicForumModels, String mFrom) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.publicForumModels = publicForumModels;
        mLatoRegular = Utility.setTypeFace_Lato_Regular(mContext);
        mFontAwesome = Utility.setTypeFace_fontawesome(mContext);
        this.mFrom = mFrom;
    }

    @Override
    public int getCount() {
        return publicForumModels.size();
    }

    @Override
    public PublicForumModel getItem(int position) {
        return publicForumModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CommunityForumViewAdapterHolder mCommunityForumViewAdapterHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_ask_for_help,
                    null);
            mCommunityForumViewAdapterHolder = new CommunityForumViewAdapterHolder();
            mCommunityForumViewAdapterHolder.tv_skills_required = (TextView) convertView.findViewById(R.id.tv_skills_required);
            mCommunityForumViewAdapterHolder.txt_comment_icon = (TextView) convertView.findViewById(R.id.txt_comment_icon);
            mCommunityForumViewAdapterHolder.tv_it_software = (TextView) convertView.findViewById(R.id.tv_it_software);
            mCommunityForumViewAdapterHolder.txt_text = (TextView) convertView.findViewById(R.id.txt_text);
            mCommunityForumViewAdapterHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            mCommunityForumViewAdapterHolder.mAdView = (NativeExpressAdView) convertView.findViewById(R.id.adView);

            convertView.setTag(mCommunityForumViewAdapterHolder);
        } else {
            mCommunityForumViewAdapterHolder = (CommunityForumViewAdapterHolder) convertView.getTag();
        }

        PublicForumModel publicForumModel = publicForumModels.get(position);
        mCommunityForumViewAdapterHolder.tv_skills_required.setTypeface(mLatoRegular);
        mCommunityForumViewAdapterHolder.txt_text.setTypeface(mLatoRegular);
        mCommunityForumViewAdapterHolder.tv_skills_required.setText(Utility.capitalizeFirstLetter(publicForumModel.getName()));
        mCommunityForumViewAdapterHolder.txt_comment_icon.setTypeface(mFontAwesome);
        mCommunityForumViewAdapterHolder.tv_it_software.setTypeface(mLatoRegular);
        mCommunityForumViewAdapterHolder.tv_date.setTypeface(mLatoRegular);
        mCommunityForumViewAdapterHolder.tv_it_software.setText(Utility.capitalizeFirstLetter(publicForumModel.getUsername()));
        mCommunityForumViewAdapterHolder.txt_text.setText(publicForumModel.getReplies());
        mCommunityForumViewAdapterHolder.tv_date.setText(Utility.getCommunityDateTime(publicForumModel.getRecordeddate()));
        //mCommunityForumViewAdapterHolder.tv_skills_required.setText("Jana Sena leader Pawan Kalyan is taking forward the movement of '#ap demands special status");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PUBLIC_FORUM_MODEL, publicForumModels.get(position));
                bundle.putSerializable(Constants.FROM, mFrom);
                Utility.navigateDashBoardFragment(new ForumDetailFragment(), ForumDetailFragment.TAG, bundle, mContext);
            }
        });

        if (position % 5 == 0 && position != 0) {
            if (Constants.logAddsOnOrOff)
                mCommunityForumViewAdapterHolder.mAdView.setVisibility(View.VISIBLE);
            // Set its video options.
            mCommunityForumViewAdapterHolder.mAdView.setVideoOptions(new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build());

            // The VideoController can be used to get lifecycle events and info about an ad's video
            // asset. One will always be returned by getVideoController, even if the ad has no video
            // asset.
            mCommunityForumViewAdapterHolder.mVideoController = mCommunityForumViewAdapterHolder.mAdView.getVideoController();
            mCommunityForumViewAdapterHolder.mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });

            // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
            // loading.
            final CommunityForumViewAdapter.CommunityForumViewAdapterHolder finalMJobOpeningAdapterHolder = mCommunityForumViewAdapterHolder;
            mCommunityForumViewAdapterHolder.mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (finalMJobOpeningAdapterHolder.mVideoController.hasVideoContent()) {
                        Log.d("sdf", "Received an ad that contains a video asset.");
                    } else {
                        Log.d("sdf", "Received an ad that does not contain a video asset.");
                    }
                }
            });

            mCommunityForumViewAdapterHolder.mAdView.loadAd(new AdRequest.Builder().build());
        } else {
            mCommunityForumViewAdapterHolder.mAdView.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class CommunityForumViewAdapterHolder {
        private TextView tv_police;
        private TextView tv_skills_required;
        private TextView txt_comment_icon;
        private TextView tv_it_software;
        private TextView txt_text;
        private TextView tv_date;

        private NativeExpressAdView mAdView;
        private VideoController mVideoController;
    }
}
