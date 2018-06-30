package nata.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nata.com.activities.DashboardActivity;
import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class CareerCenterFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "CareerCenterFragment";
    private View rootView;

    private ImageView iv_police;
    private TextView tv_text_header;
    private TextView tv_all_over;
    private TextView tv_noof_positions;
    private TextView tv_no;
    private TextView tv_published_date;
    private TextView tv_job_role_head;
    private TextView tv_job_role;
    private TextView tv_date;
    private Button btn_submit_resume;
    private TextView tv_company_name;
    private TextView tv_company_details;
    private TextView tv_eligibility;
    private TextView tv_eligibility_details;
    private TextView tv_experience;
    private TextView tv_experience_details;
    private TextView tv_job_description;
    private TextView tv_job_description_details;
    private DashboardActivity mParent;




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
    private SimpleDateFormat dateFormatter;

    private ImageView iv_logo;
    private NativeExpressAdView mAdView;
    private VideoController mVideoController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();

        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.career_center).toUpperCase());
        rootView = inflater.inflate(R.layout.fragment_career_center, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {

        setFooterData();

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);



        tv_text_header = (TextView) rootView.findViewById(R.id.tv_text_header);

        tv_all_over = (TextView) rootView.findViewById(R.id.tv_all_over);


        tv_noof_positions = (TextView) rootView.findViewById(R.id.tv_noof_positions);
        tv_no = (TextView) rootView.findViewById(R.id.tv_no);

        tv_published_date = (TextView) rootView.findViewById(R.id.tv_published_date);
        tv_job_role_head = (TextView) rootView.findViewById(R.id.tv_job_role_head);
        tv_job_role = (TextView) rootView.findViewById(R.id.tv_job_role);
        iv_logo = (ImageView) rootView.findViewById(R.id.iv_logo);

        mAdView = (NativeExpressAdView) rootView.findViewById(R.id.adView);


        /*DATE */
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

        tv_text_header.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_all_over.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_noof_positions.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_no.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_published_date.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_job_role_head.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_job_role.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_date.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_company_name.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_company_details.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_eligibility.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_eligibility_details.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_experience.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_experience_details.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_job_description.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        tv_job_description_details.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));

        btn_submit_resume.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        btn_submit_resume.setOnClickListener(this);



    }

    private void setFooterData() {
        ll_position_one = (LinearLayout) rootView.findViewById(R.id.ll_position_one);
        ll_position_two = (LinearLayout) rootView.findViewById(R.id.ll_position_two);
        ll_position_three = (LinearLayout) rootView.findViewById(R.id.ll_position_three);
        ll_position_four = (LinearLayout) rootView.findViewById(R.id.ll_position_four);

        ll_position_one.setOnClickListener(this);
        ll_position_two.setOnClickListener(this);
        ll_position_three.setOnClickListener(this);
        ll_position_four.setOnClickListener(this);


        txt_position_one = (TextView) rootView.findViewById(R.id.txt_position_one);
        txt_position_two = (TextView) rootView.findViewById(R.id.txt_position_two);
        txt_position_three = (TextView) rootView.findViewById(R.id.txt_position_three);
        txt_position_four = (TextView) rootView.findViewById(R.id.txt_position_four);

        txt_position_one.setText(Utility.getResourcesString(getActivity(), R.string.find_job));
        txt_position_two.setText(Utility.getResourcesString(getActivity(), R.string.jobs_applied));
        txt_position_three.setText(Utility.getResourcesString(getActivity(), R.string.my_post));
        txt_position_four.setText(Utility.getResourcesString(getActivity(), R.string.filter));

        txt_position_one.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_position_two.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_position_three.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_position_four.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

        txt_position_one_icon = (TextView) rootView.findViewById(R.id.txt_position_one_icon);
        txt_position_two_icon = (TextView) rootView.findViewById(R.id.txt_position_two_icon);
        txt_position_three_icon = (TextView) rootView.findViewById(R.id.txt_position_three_icon);
        txt_position_four_icon = (TextView) rootView.findViewById(R.id.txt_position_four_icon);

        txt_position_one_icon.setText(Utility.getResourcesString(mParent, R.string.find_jobs));
        txt_position_two_icon.setText(Utility.getResourcesString(mParent, R.string.jobs_applied_icon));
        txt_position_three_icon.setText(Utility.getResourcesString(mParent, R.string.my_posts_icon));
        txt_position_four_icon.setText(Utility.getResourcesString(mParent, R.string.filter_icon));

        txt_position_one_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_position_two_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_position_three_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_position_four_icon.setTypeface(Utility.setTypeFace_Image(mParent));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_position_one:
                Utility.navigateDashBoardFragment(new FindJobsFragment(), FindJobsFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_two:
                Utility.navigateDashBoardFragment(new JobsAppliedFragment(), JobsAppliedFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_three:
                Utility.navigateDashBoardFragment(new MyPostFragment(), MyPostFragment.TAG, null, mParent);
                break;
            case R.id.ll_position_four:
                Utility.navigateDashBoardFragment(new FiltersFragment(), FiltersFragment.TAG, null, mParent);
                break;

        }

    }



}
