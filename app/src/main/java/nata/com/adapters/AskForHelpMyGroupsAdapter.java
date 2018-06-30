package nata.com.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import nata.com.activities.DashboardActivity;
import nata.com.fragments.AskForHelpMyModerateListFragment;
import nata.com.models.ForumModel;
import nata.com.nata.R;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by Shankar on 03/12/2017.
 */

public class AskForHelpMyGroupsAdapter extends BaseAdapter {
    private DashboardActivity mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ForumModel> forumModels;
    private Typeface typefaceLatoRegular;

    public AskForHelpMyGroupsAdapter(DashboardActivity context, ArrayList<ForumModel> forumModels) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.forumModels = forumModels;
        typefaceLatoRegular = Utility.setTypeFace_Lato_Regular(mContext);
    }

    @Override
    public int getCount() {
        return forumModels.size();
    }

    @Override
    public Object getItem(int position) {
        return forumModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        AskForHelpMyGroupsAdapter.PopularOurForumAdapterHolder mPopularOurForumAdapterHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.ourforum_grid_item,
                    null);
            mPopularOurForumAdapterHolder = new AskForHelpMyGroupsAdapter.PopularOurForumAdapterHolder();
            mPopularOurForumAdapterHolder.txt_popular_name = (TextView) convertView.findViewById(R.id.txt_popular_name);
            mPopularOurForumAdapterHolder.img_popular_our_forum = (ImageView) convertView.findViewById(R.id.img_popular_our_forum);
            mPopularOurForumAdapterHolder.txt_popular_name.setTypeface(typefaceLatoRegular);
            convertView.setTag(mPopularOurForumAdapterHolder);
        } else {
            mPopularOurForumAdapterHolder = (AskForHelpMyGroupsAdapter.PopularOurForumAdapterHolder) convertView.getTag();
        }

        final ForumModel forumModel = forumModels.get(position);
        mPopularOurForumAdapterHolder.txt_popular_name.setText(forumModel.getName());
        if (!Utility.isValueNullOrEmpty(forumModel.getImage())) {
            Utility.URLProfilePicLoading(mPopularOurForumAdapterHolder.img_popular_our_forum,
                    forumModel.getImage(), null, R.drawable.place_holder);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ID, forumModels.get(position).getId());
                bundle.putString(Constants.POST_FROM, forumModels.get(position).getName());
                bundle.putString(Constants.POST_IMAGE, forumModels.get(position).getImage());
                Utility.navigateDashBoardFragment(new AskForHelpMyModerateListFragment(), AskForHelpMyModerateListFragment.TAG, bundle, mContext);
            }
        });

        return convertView;
    }


    private class PopularOurForumAdapterHolder {
        private TextView txt_popular_name;
        private ImageView img_popular_our_forum;
    }
}
