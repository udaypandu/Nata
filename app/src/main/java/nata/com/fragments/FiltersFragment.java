package nata.com.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.ImageAdapter;
import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiltersFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "FiltersFragment";
    private DashboardActivity mParent;
    private View rootView;



    private LinearLayout ll_position_one;
    private TextView txt_position_one;
    private TextView txt_position_one_icon;

    private LinearLayout ll_position_two;
    private TextView txt_position_two;
    private TextView txt_position_two_icon;

    private LinearLayout ll_position_three;
    private TextView txt_position_three;
    private TextView txt_position_three_icon;

    private LinearLayout ll_position_four;
    private TextView txt_position_four;
    private TextView txt_position_four_icon;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    public static int[] images = {R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
             R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
             R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
            R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
           R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,R.drawable.sponsor1, R.drawable.sponsor3,
            R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5,
            R.drawable.sponsor1, R.drawable.sponsor3,R.drawable.sponsor5
            };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.filter).toUpperCase());
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_filters, container, false);
        initUI();
        return rootView;

    }


    private void initUI() {




        ll_position_one = (LinearLayout) rootView.findViewById(R.id.ll_position_one);
        ll_position_two = (LinearLayout) rootView.findViewById(R.id.ll_position_two);
        ll_position_three = (LinearLayout) rootView.findViewById(R.id.ll_position_three);
        ll_position_four = (LinearLayout) rootView.findViewById(R.id.ll_position_four);

        ll_position_one.setOnClickListener(this);
        ll_position_two.setOnClickListener(this);
        ll_position_three.setOnClickListener(this);
        ll_position_four.setOnClickListener(this);


        setFooterData();

        GridView gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter((rootView.getContext())));

    }

    private void setFooterData() {

        txt_position_one = (TextView) rootView.findViewById(R.id.txt_position_one);
        txt_position_two = (TextView) rootView.findViewById(R.id.txt_position_two);
        txt_position_three = (TextView) rootView.findViewById(R.id.txt_position_three);
        txt_position_four = (TextView) rootView.findViewById(R.id.txt_position_four);

        txt_position_one.setText(Utility.getResourcesString(getActivity(), R.string.update_small));
        txt_position_two.setText(Utility.getResourcesString(getActivity(), R.string.services_small));
        txt_position_three.setText(Utility.getResourcesString(getActivity(), R.string.discussions_small));
        txt_position_four.setText(Utility.getResourcesString(getActivity(), R.string.members));

        txt_position_one.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_position_two.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_position_three.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_position_four.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        txt_position_one_icon = (TextView) rootView.findViewById(R.id.txt_position_one_icon);
        txt_position_two_icon = (TextView) rootView.findViewById(R.id.txt_position_two_icon);
        txt_position_three_icon = (TextView) rootView.findViewById(R.id.txt_position_three_icon);
        txt_position_four_icon = (TextView) rootView.findViewById(R.id.txt_position_four_icon);

        txt_position_one_icon.setText(Utility.getResourcesString(mParent, R.string.find_jobs));
        txt_position_two_icon.setText(Utility.getResourcesString(mParent, R.string.post_job_icon));
        txt_position_three_icon.setText(Utility.getResourcesString(mParent, R.string.jobs_applied_icon));
        txt_position_four_icon.setText(Utility.getResourcesString(mParent, R.string.my_posts_icon));

        txt_position_one_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));
        txt_position_two_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));
        txt_position_three_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));
        txt_position_four_icon.setTypeface(Utility.setTypeFace_fontawesome(mParent));




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_position_one:
                Utility.navigateDashBoardFragment(new FindJobsFragment(), FindJobsFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_two:
                Utility.navigateDashBoardFragment(new JobPostingFragment(), JobPostingFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_three:
                Utility.navigateDashBoardFragment(new JobsAppliedFragment(), JobsAppliedFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_four:
                Utility.navigateDashBoardFragment(new MyPostFragment(), MyPostFragment.TAG, null, mParent);
                break;

        }
    }

}
