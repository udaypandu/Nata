package nata.com.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.CommunityForumViewAdapter;
import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by shankar on 2/4/2017.
 */

public class JoinDiscussionViewFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String TAG = "JoinDiscussionViewFragment";
    private DashboardActivity mParent;
    private View rootView;


    private TextView txt_forum_founders;
    private LinearLayout ll_founders;
    private TextView txt_group;
    private TextView txt_tech;

    private SwipeMenuListView listView;

    private FloatingActionButton mFloatingActionButton;
    private CommunityForumViewAdapter mCommunityForumViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.janaswaram));
        rootView = inflater.inflate(R.layout.fragment_our_forum_view, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        txt_forum_founders = (TextView) rootView.findViewById(R.id.txt_forum_founders);
        ll_founders = (LinearLayout) rootView.findViewById(R.id.ll_founders);
        txt_group = (TextView) rootView.findViewById(R.id.txt_group);
        txt_tech = (TextView) rootView.findViewById(R.id.txt_tech);

        txt_forum_founders.setText("Team Incharges");
        txt_group.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_forum_founders.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_group.setText("AP Special Status");
        txt_group.setVisibility(View.GONE);

        txt_tech.setText("AP Special Status");
        txt_tech.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        listView = (SwipeMenuListView) rootView.findViewById(R.id.listView);

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);

       // mCommunityForumViewAdapter = new CommunityForumViewAdapter(mParent, );

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x63, 0x63,
                        0x63)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set item title
                deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                deleteItem.setIcon(R.drawable.ic_delete_sweep_white_24dp);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);
        listView.setAdapter(mCommunityForumViewAdapter);

        listView.setOnItemClickListener(this);

        setDataToTheEventsLayout();
    }

    private void setDataToTheEventsLayout() {
        ll_founders.removeAllViews();
        //if (mEventsModel != null && mEventsModel.getEventsModels().size() > 0)
        //for (int i = 0; i < mEventsModel.getEventsModels().size(); i++) {
        for (int i = 0; i < 4; i++) {
            LinearLayout child = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.founders_item, null);
            ImageView img_thumbnail = (ImageView) child.findViewById(R.id.img_thumbnail);
            ProgressBar progress = (ProgressBar) child.findViewById(R.id.progress);
            TextView txt_name = (TextView) child.findViewById(R.id.txt_name);
            TextView txt_mail = (TextView) child.findViewById(R.id.txt_mail);
            img_thumbnail.setImageDrawable(Utility.getDrawable(getActivity(), R.drawable.image_modo));
            txt_name.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
            txt_mail.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

            if (i==0){
                txt_name.setText("Varaprasad Yalla");
                txt_mail.setText("yallaveraprasad@gmail.com");
            } else if (i==1){
                txt_name.setText("Ravi Kiran Koya");
                txt_mail.setText("ravikiran.koya@gmail.com");
            } else if (i==2){
                txt_name.setText("Shiva Poiala");
                txt_mail.setText("shiva_poiala@gmail.com");
            } else if (i==3){
                txt_name.setText("Uday Koya");
                txt_mail.setText("udaykoya@gmail.com");
            }

                /*Utility.URLProfilePicLoading(img_thumbnail, "http://www.kapuwelfare.com/" + mEventsModel.getEventsModels().get(i).getImage(),
                        progress, R.drawable.avatar_image_flat);*/

            ll_founders.addView(child);
        }
        //}
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Bundle bundle = new Bundle();
                bundle.putString("from", "" + TAG);
                Utility.navigateDashBoardFragment(new AskQuestionFragment(), AskQuestionFragment.TAG, bundle, mParent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putString("from", "" + TAG);
        Utility.navigateDashBoardFragment(new RaiseConcernDetialFragment(), RaiseConcernDetialFragment.TAG, bundle, mParent);
    }
}
