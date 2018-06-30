package nata.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;

import nata.com.activities.DashboardActivity;
import nata.com.models.OurForceCountryModel;
import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * Created by Shankar on 26-Dec-16.
 */

public class OurForceAdapter extends BaseAdapter {
    private DashboardActivity mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<OurForceCountryModel> ourForceCountryModels;

    public OurForceAdapter(DashboardActivity context, ArrayList<OurForceCountryModel> ourForceCountryModels) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ourForceCountryModels = ourForceCountryModels;
    }

    @Override
    public int getCount() {
        return ourForceCountryModels.size();
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
        CommunityForumViewAdapterHolder mCommunityForumViewAdapterHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.our_force_home_item,
                    null);
            mCommunityForumViewAdapterHolder = new CommunityForumViewAdapterHolder();
            mCommunityForumViewAdapterHolder.txt_our_force = (TextView) convertView.findViewById(R.id.txt_our_force);
            convertView.setTag(mCommunityForumViewAdapterHolder);
        } else {
            mCommunityForumViewAdapterHolder = (CommunityForumViewAdapterHolder) convertView.getTag();
        }

        mCommunityForumViewAdapterHolder.txt_our_force.setText(ourForceCountryModels.get(position).getCountry() + " " + "(" + ourForceCountryModels.get(position).getCount() + ")");
        mCommunityForumViewAdapterHolder.txt_our_force.setTypeface(Utility.setTypeFace_Lato_Regular(mContext));

        return convertView;
    }

    private class CommunityForumViewAdapterHolder {
        private TextView txt_our_force;
    }
}
