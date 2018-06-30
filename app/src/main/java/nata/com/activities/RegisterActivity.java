package nata.com.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by shankar on 2/4/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private TextView txt_header;
    private TextView txt_login;
    private TextView txt_register;

    private TextView tv_submit;
    private TextView txt_or;

    private EditText edt_email_optional;
    private EditText edt_password;
    private EditText edt_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_register_janasena);
        initUI();
    }

    private void initUI() {
        txt_login = (TextView) findViewById(R.id.txt_login);
        txt_header = (TextView) findViewById(R.id.txt_header);
        txt_register = (TextView) findViewById(R.id.txt_register);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        txt_or = (TextView) findViewById(R.id.txt_or);

        edt_email_optional = (EditText) findViewById(R.id.edt_email_optional);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);

        txt_header.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        txt_register.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        txt_login.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        tv_submit.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        edt_mobile.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        txt_or.setTypeface(Utility.setTypeFace_Lato_Regular(this));

        edt_email_optional.setTypeface(Utility.setTypeFace_Lato_Regular(this));
        edt_password.setTypeface(Utility.setTypeFace_Lato_Regular(this));
    }
}