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
 * Created by shankar on 2/3/17.
 */

public class RecentConcernsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String TAG = "RecentConcernsFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_forum_founders;
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
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.raise_a_concern));
        rootView = inflater.inflate(R.layout.fragment_recent_concerns, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        txt_forum_founders = (TextView) rootView.findViewById(R.id.txt_forum_founders);
        txt_forum_founders.setText("Recent Concerns");
        txt_forum_founders.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        listView = (SwipeMenuListView) rootView.findViewById(R.id.listView);

        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);

        //mCommunityForumViewAdapter = new CommunityForumViewAdapter(mParent);

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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putString("from", "" + TAG);
        Utility.navigateDashBoardFragment(new RaiseConcernDetialFragment(), RaiseConcernDetialFragment.TAG, bundle, mParent);
    }

}
