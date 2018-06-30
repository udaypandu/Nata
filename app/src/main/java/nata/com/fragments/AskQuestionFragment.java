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
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.SpinnerAdapter;
import nata.com.models.SpinnerModel;
import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * Created by Madhu Toto on 27/12/2016
 */
public class AskQuestionFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AskQuestionFragment";
    private DashboardActivity mParent;
    private View rootView;

    private EditText et_select_category;
    private EditText et_topic_name;
    private EditText et_message;
    private EditText et_supporting_doc;

    private Button btn_submit;

    private String mFrom;
    private ArrayList<SpinnerModel> itemsList ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mFrom = getArguments().getString("from");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*if (mFrom.equalsIgnoreCase(CommunityForumViewFragment.TAG)) {
            mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.create_topic).toUpperCase());
        } else*/ if(mFrom.equalsIgnoreCase("JoinDiscussionViewFragment")) {
            mParent.getSupportActionBar().setTitle("JANASWARAM");
        } else {
            mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.raise_a_concern).toUpperCase());
        }
        rootView = inflater.inflate(R.layout.fragment_ask_question, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        et_select_category = (EditText) rootView.findViewById(R.id.et_select_category);
        et_select_category.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        if (mFrom.equalsIgnoreCase("JoinDiscussionViewFragment")) {
            et_select_category.setVisibility(View.GONE);
        } else {
            et_select_category.setVisibility(View.VISIBLE);
        }
        et_topic_name = (EditText) rootView.findViewById(R.id.et_topic_name);
        et_topic_name.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        et_message = (EditText) rootView.findViewById(R.id.et_message);
        et_supporting_doc = (EditText) rootView.findViewById(R.id.et_supporting_doc);
        et_message.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        et_supporting_doc.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        btn_submit.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        btn_submit.setOnClickListener(this);

        itemsList = new ArrayList<>();
        SpinnerModel spinnerModel = new SpinnerModel("Category 1");
        itemsList.add(spinnerModel);
        SpinnerModel spinnerModel1 = new SpinnerModel("Category 2");
        itemsList.add(spinnerModel1);
        SpinnerModel spinnerModel2 = new SpinnerModel("Category 3");
        itemsList.add(spinnerModel2);
        SpinnerModel spinnerModel3 = new SpinnerModel("Category 4");
        itemsList.add(spinnerModel3);
        SpinnerModel spinnerModel5 = new SpinnerModel("Category 5");
        itemsList.add(spinnerModel5);
        SpinnerModel spinnerModel6 = new SpinnerModel("Category 6");
        itemsList.add(spinnerModel6);
        et_select_category.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                submitYourDataForAsk4Help();
                break;
            case R.id.et_select_category:
                showSpinnerDialog(getActivity(), "Category", et_select_category, itemsList, 1);
                break;
        }
    }

    private void submitYourDataForAsk4Help() {

    }

    private void setDataToCategory(ArrayList<SpinnerModel> itemsList) {
            showSpinnerDialog(getActivity(), "Category", et_select_category, itemsList, 1);
    }

    public void showSpinnerDialog(final Context context, final String title, final EditText et_spinner,
                                  ArrayList<SpinnerModel> itemsList, final int id) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);

        /*CUSTOM TITLE*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_dialog_title, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_alert_dialog_title);
        RelativeLayout dialog_back_ground = (RelativeLayout) view.findViewById(R.id.dialog_back_ground);
        dialog_back_ground.setBackgroundColor(context.getResources().getColor(R.color.themeColor));
        tv_title.setText(title);
        tv_title.setTextColor(context.getResources().getColor(R.color.blackColor));
        builderSingle.setCustomTitle(view);


        final SpinnerAdapter adapter = new SpinnerAdapter(context, itemsList);
        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpinnerModel mData = (SpinnerModel) adapter.getItem(which);
                        if (id == 1) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                        }
                    }
                });
        builderSingle.show();
    }

}
