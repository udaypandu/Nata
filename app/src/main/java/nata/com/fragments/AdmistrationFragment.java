package nata.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nata.com.activities.DashboardActivity;
import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by Shankar Pilli on 1/3/2017
 */
public class AdmistrationFragment extends Fragment {

    public static final String TAG = "AdmistrationFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_dashboard;
    private TextView txt_navigation;

    private TextView txt_add_user_icon;
    private TextView txt_add_user;
    private TextView txt_pending_approvals_icon;
    private TextView txt_pending_approvals;

    private TextView txt_approve_jobs;
    private TextView txt_approve_jobs_icon;

    private TextView txt_send_sms_icon;
    private TextView txt_send_sms;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.administration).toUpperCase());
        rootView = inflater.inflate(R.layout.fragment_admistration, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
       // txt_dashboard = (TextView)rootView.findViewById(R.id.txt_dashboard);
        //txt_navigation = (TextView)rootView.findViewById(R.id.txt_navigation);

        txt_add_user_icon = (TextView)rootView.findViewById(R.id.txt_add_user_icon);
        txt_pending_approvals_icon = (TextView)rootView.findViewById(R.id.txt_pending_approvals_icon);
        //txt_send_sms_icon = (TextView)rootView.findViewById(R.id.txt_send_sms_icon);
        txt_approve_jobs_icon = (TextView)rootView.findViewById(R.id.txt_approve_jobs_icon);

        txt_add_user = (TextView)rootView.findViewById(R.id.txt_add_user);
        txt_pending_approvals = (TextView)rootView.findViewById(R.id.txt_pending_approvals);
        txt_pending_approvals.setText("Moderation");
        txt_approve_jobs = (TextView)rootView.findViewById(R.id.txt_approve_jobs);
        txt_approve_jobs.setText("Role Change");
        //txt_send_sms = (TextView)rootView.findViewById(R.id.txt_send_sms);

        txt_add_user.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_pending_approvals.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_approve_jobs.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_send_sms.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));

        txt_dashboard.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_navigation.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));

        txt_add_user_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_pending_approvals_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_approve_jobs_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_send_sms_icon.setTypeface(Utility.setTypeFace_Image(mParent));
    }
}
