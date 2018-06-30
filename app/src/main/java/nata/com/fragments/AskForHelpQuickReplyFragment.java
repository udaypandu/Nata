package nata.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import nata.com.activities.DashboardActivity;
import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by Shankar Pilli on 11/4/2016
 */
public class AskForHelpQuickReplyFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "AskForHelpQuickReplyFragment";
    private DashboardActivity mParent;
    private View rootView;

    private EditText et_message;
    private EditText et_supporting_doc;
    private Button btn_reply;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.quick_reply).toUpperCase());
        rootView = inflater.inflate(R.layout.fragment_ask_quick_reply, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        et_message = (EditText) rootView.findViewById(R.id.et_message);
        et_supporting_doc = (EditText) rootView.findViewById(R.id.et_supporting_doc);
        et_message.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        et_supporting_doc.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        btn_reply = (Button) rootView.findViewById(R.id.btn_reply);
        btn_reply.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        btn_reply.setText("Submit");
        btn_reply.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reply:
                replyToTheMessage();
                break;
        }
    }

    private void replyToTheMessage() {
        if (!Utility.isValueNullOrEmpty(et_message.getText().toString())) {

        } else {
            Utility.setSnackBarEnglish(mParent, et_message, "Enter you message");
        }
    }
}