package nata.com.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nata.com.activities.DashboardActivity;
import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by shankar on 2/3/17.
 */

public class RaiseConcernDetialFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "RaiseConcernDetialFragment";
    private DashboardActivity mParent;
    private View rootView;

    private FloatingActionButton fab;

    private TextView tv_skills_required;
    private TextView tv_it_software;
    private TextView tv_date;
    private TextView txt_replies;

    private LinearLayout tv_replies;
    private String mFrom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mFrom = getArguments().getString("from");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mFrom.equalsIgnoreCase("JoinDiscussionViewFragment")) {
            mParent.getSupportActionBar().setTitle("AP Special Staus");
        } else
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.raise_a_concern));
        rootView = inflater.inflate(R.layout.fragment_ask_for_help_view, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        tv_skills_required = (TextView) rootView.findViewById(R.id.tv_skills_required);
        tv_it_software = (TextView) rootView.findViewById(R.id.tv_it_software);
        tv_date = (TextView) rootView.findViewById(R.id.tv_date);
        txt_replies = (TextView) rootView.findViewById(R.id.txt_replies);

        tv_replies = (LinearLayout) rootView.findViewById(R.id.tv_replies);

        if(mFrom.equalsIgnoreCase("JoinDiscussionViewFragment")) {
            tv_it_software.setText("");
            tv_skills_required.setText("Pawan Kalyan is taking forward the movement of AP demands special status");
            tv_date.setText("12/12");
        } else {
            tv_skills_required.setText("Pawan Kalyan is taking forward the movement of AP demands special status");
            tv_it_software.setText("AP Special Staus");
            tv_date.setText("12/12");
        }

        tv_skills_required.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        tv_it_software.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        tv_date.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_replies.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        getComments();
    }

    private void getComments() {
        tv_replies.removeAllViews();
        for (int i = 0; i < 3; i++) {
            LinearLayout child = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.raise_a_consern_item, null);
            TextView tv_name = (TextView) child.findViewById(R.id.tv_name);
            TextView tv_date = (TextView) child.findViewById(R.id.tv_date);
            TextView tv_comment = (TextView) child.findViewById(R.id.tv_comment);

            tv_name.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
            tv_date.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
            tv_comment.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));

            tv_name.setText("Shankar");
            tv_date.setText("12/12");
            tv_comment.setText("The actor cum politician expressed his displeasure saying that North Indian Politician leaders don't know how many languages there in South? and they treat all the south people as Madrasis.");

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Utility.navigateDashBoardFragment(new PartyUpdateDetailFragment(), PartyUpdateDetailFragment.TAG, null, mParent);
                }
            });

            tv_replies.addView(child);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Utility.navigateDashBoardFragment(new AskForHelpQuickReplyFragment(), AskForHelpQuickReplyFragment.TAG, null, mParent);
                break;
        }
    }

}
