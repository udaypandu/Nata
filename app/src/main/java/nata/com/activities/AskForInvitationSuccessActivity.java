package nata.com.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by Shankar on 1/15/2017.
 */
public class AskForInvitationSuccessActivity extends Activity implements View.OnClickListener{

    private TextView txt_invitation_request_is_sent_successfully;
    private TextView txt_invitation_request_is_sent_successfully_first;
    private TextView txt_invitation_request_is_sent_successfully_second;
    private TextView tv_click_here_for_homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_askforinvation_success);
        initUI();
    }

    private void initUI() {
        txt_invitation_request_is_sent_successfully = (TextView) findViewById(R.id.txt_invitation_request_is_sent_successfully);
        txt_invitation_request_is_sent_successfully_first = (TextView) findViewById(R.id.txt_invitation_request_is_sent_successfully_first);
        txt_invitation_request_is_sent_successfully_second = (TextView) findViewById(R.id.txt_invitation_request_is_sent_successfully_second);
        tv_click_here_for_homepage = (TextView) findViewById(R.id.tv_click_here_for_homepage);

        txt_invitation_request_is_sent_successfully.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        txt_invitation_request_is_sent_successfully_first.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        txt_invitation_request_is_sent_successfully_second.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        tv_click_here_for_homepage.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        tv_click_here_for_homepage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_click_here_for_homepage:
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
