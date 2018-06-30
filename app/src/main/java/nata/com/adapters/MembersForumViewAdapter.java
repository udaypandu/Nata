package nata.com.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nata.com.activities.DashboardActivity;
import nata.com.customviews.CircleTransform;
import nata.com.models.MembersModel;
import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * Created by madhu on 26-Dec-16.
 */

public class MembersForumViewAdapter extends BaseAdapter {
    private DashboardActivity mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<MembersModel> membersModels;
    private Typeface mLatoRegular;

    public MembersForumViewAdapter(DashboardActivity context, ArrayList<MembersModel> membersModels) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.membersModels = membersModels;
        mLatoRegular = Utility.setTypeFace_Lato_Regular(mContext);
    }

    @Override
    public int getCount() {
        return membersModels.size();
    }

    @Override
    public MembersModel getItem(int position) {
        return membersModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MembersForumViewAdapterHolder mMembersForumViewAdapterHolder = null;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.members_item,
                    null);
            mMembersForumViewAdapterHolder = new MembersForumViewAdapterHolder();
            mMembersForumViewAdapterHolder.img_member = (ImageView) convertView.findViewById(R.id.img_member);
            mMembersForumViewAdapterHolder.tv_member_name = (TextView) convertView.findViewById(R.id.tv_member_name);
            mMembersForumViewAdapterHolder.tv_member_email = (TextView) convertView.findViewById(R.id.tv_member_email);
            convertView.setTag(mMembersForumViewAdapterHolder);
        } else {
            mMembersForumViewAdapterHolder = (MembersForumViewAdapterHolder) convertView.getTag();
        }
        MembersModel membersModel = membersModels.get(position);

        if (!Utility.isValueNullOrEmpty(membersModel.getPhoto()))
            Picasso.with(mContext).load(membersModel.getPhoto()).transform(new CircleTransform()).
                    placeholder(R.drawable.avatar_image).into(mMembersForumViewAdapterHolder.img_member);
        mMembersForumViewAdapterHolder.tv_member_name.setText(Utility.capitalizeFirstLetter(membersModel.getFirstname()));
        mMembersForumViewAdapterHolder.tv_member_name.setTypeface(mLatoRegular);
        mMembersForumViewAdapterHolder.tv_member_email.setTypeface(mLatoRegular);
        mMembersForumViewAdapterHolder.tv_member_email.setText(Utility.capitalizeFirstLetter(membersModel.getCompany()));

        return convertView;
    }

    private class MembersForumViewAdapterHolder {
        private ImageView img_member;
        private TextView tv_member_name;
        private TextView tv_member_email;
    }
}
