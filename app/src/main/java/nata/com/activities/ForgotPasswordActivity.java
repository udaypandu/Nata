package nata.com.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.util.LinkedHashMap;

import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.JobPostingModel;
import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.parsers.JobPostingParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener, IAsyncCaller {

    private TextView txt_login;
    private EditText edit_email;
    private TextView txt_reset_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.forgot_password);
        initUI();
    }

    private void initUI() {
        txt_login = (TextView) findViewById(R.id.tv_login);
        edit_email = (EditText) findViewById(R.id.edit_email);
        txt_reset_password = (TextView) findViewById(R.id.txt_reset_password);
        txt_login.setPaintFlags(txt_login.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        txt_login.setOnClickListener(this);
        txt_reset_password.setOnClickListener(this);


    }

   @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                super.onBackPressed();
                break;
            case R.id.txt_reset_password:
                if (isValidFields()) {
                    forgotUserPassword();
                }
                break;
        }
    }

    private void forgotUserPassword() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        paramMap.put("email", edit_email.getText().toString());
        JobPostingParser mJobPostingParser = new JobPostingParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(ForgotPasswordActivity.this,
                Utility.getResourcesString(ForgotPasswordActivity.this,
                        R.string.please_wait), true,
                APIConstants.FORGOT_PASSWORD,
                paramMap,
                APIConstants.REQUEST_TYPE.POST,
                this, mJobPostingParser);
        Utility.execute(serverIntractorAsync);

    }

    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(edit_email.getText().toString().trim())) {
            Utility.setSnackBarEnglish(this, edit_email, "Please enter Email ID ");
            edit_email.requestFocus();
        } else if (!edit_email.getText().toString().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$")) {
            Utility.setSnackBarEnglish(this, edit_email, "Please enter valid user email");
            edit_email.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                JobPostingModel jobPostingModel = (JobPostingModel) model;
                Utility.showToastMessage(ForgotPasswordActivity.this, jobPostingModel.getMessage());
            } else {
                Utility.showToastMessage(ForgotPasswordActivity.this, model.getMessage());
            }
        }
    }

}
