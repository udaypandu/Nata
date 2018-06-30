package nata.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import nata.com.activities.DashboardActivity;
import nata.com.models.JanaswaramModel;
import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * Created by madhu on 26-Dec-16.
 */

public class JoinDiscussionViewAdapter extends BaseAdapter {
    private DashboardActivity mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<JanaswaramModel> janaswaramModels;

    public JoinDiscussionViewAdapter(DashboardActivity context, ArrayList<JanaswaramModel> janaswaramModels) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.janaswaramModels = janaswaramModels;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JoinDiscussionViewAdapterHolder mJoinDiscussionViewAdapterHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.join_discussion_item,
                    null);
            mJoinDiscussionViewAdapterHolder = new JoinDiscussionViewAdapterHolder();
            mJoinDiscussionViewAdapterHolder.img_news = (ImageView) convertView.findViewById(R.id.img_news);
            mJoinDiscussionViewAdapterHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
            convertView.setTag(mJoinDiscussionViewAdapterHolder);
        } else {
            mJoinDiscussionViewAdapterHolder = (JoinDiscussionViewAdapterHolder) convertView.getTag();
        }

        mJoinDiscussionViewAdapterHolder.txt_title.setTypeface(Utility.setTypeFace_Lato_Regular(mContext));
        mJoinDiscussionViewAdapterHolder.txt_title.setText(janaswaramModels.get(position).getName());
        mJoinDiscussionViewAdapterHolder.img_news.setImageDrawable(Utility.getDrawable(mContext, janaswaramModels.get(position).getId()));

        return convertView;
    }

    private class JoinDiscussionViewAdapterHolder {
        private ImageView img_news;
        private TextView txt_title;
    }
}
