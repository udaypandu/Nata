package nata.com.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.ViewPagerAdapter;
import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewHomePageFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "NewHomePageFragment";
    private DashboardActivity mParent;
    private View rootView;
    private Toolbar mToolbar;

    private ImageView iv_find_job;
    private ImageView iv_post_job_vecancey;
    private ImageView iv_jobs_applied;
    private ImageView iv_my_posted_job;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.app_name).toUpperCase());
        rootView = inflater.inflate(R.layout.fragment_new_home_page, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        Utility.showLog("iv_find_job", "inIT");
        iv_find_job = (ImageView) rootView.findViewById(R.id.iv_find_job);
        iv_find_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showLog("iv_find_job", "Clicked");
                Utility.navigateDashBoardFragment(new CareerCenterFragment(), CareerCenterFragment.TAG, null, mParent);
            }
        });
        iv_post_job_vecancey = (ImageView) rootView.findViewById(R.id.iv_post_job_vecancey);
        iv_jobs_applied = (ImageView) rootView.findViewById(R.id.iv_jobs_applied);
        iv_my_posted_job = (ImageView) rootView.findViewById(R.id.iv_my_posted_job);
        /*mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        iv_find_job.setOnClickListener(this);
        iv_post_job_vecancey.setOnClickListener(this);
        iv_jobs_applied.setOnClickListener(this);
        iv_my_posted_job.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // if (Utility.isLogin(getActivity())) {
        switch (v.getId()) {
            case R.id.iv_find_job:
                Utility.navigateDashBoardFragment(new FindJobsFragment(), FindJobsFragment.TAG, null, mParent);
                break;
            case R.id.iv_post_job_vecancey:
                Utility.navigateDashBoardFragment(new JobPostingFragment(), JobPostingFragment.TAG, null, mParent);
                break;
            case R.id.iv_jobs_applied:
                Utility.navigateDashBoardFragment(new JobsAppliedFragment(), JobsAppliedFragment.TAG, null, mParent);
                break;
            case R.id.iv_my_posted_job:
                Utility.navigateDashBoardFragment(new MyPostFragment(), MyPostFragment.TAG, null, mParent);
                break;
            default:
                break;
        }
        /*} else {
            Utility.loginDialog(getActivity());
        }*/
    }
}

