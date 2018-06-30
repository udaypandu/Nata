package nata.com.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import nata.com.nata.R;

public class HomeActivity extends Activity implements View.OnClickListener {

    private TextView txt_kapu_welfare;
    private RelativeLayout rl_careers;
    private RelativeLayout rl_ask_4_help;
    private RelativeLayout rl_financial_aid;
    private RelativeLayout rl_ourforum;
    private ImageView image_news_events;
    private TextView txt_sign_inn;
    private ImageView view_not_registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_home);
        initUI();
    }

    private void initUI() {
        txt_kapu_welfare = (TextView) findViewById(R.id.txt_kapu_welfare);
        rl_careers = (RelativeLayout) findViewById(R.id.rl_careers);
        rl_ask_4_help = (RelativeLayout) findViewById(R.id.rl_ask_4_help);
        rl_financial_aid = (RelativeLayout) findViewById(R.id.rl_financial_aid);
        rl_ourforum = (RelativeLayout) findViewById(R.id.rl_ourforum);
        image_news_events = (ImageView) findViewById(R.id.image_news_events);
        view_not_registered = (ImageView) findViewById(R.id.view_not_registered);
        txt_sign_inn = (TextView) findViewById(R.id.txt_sign_inn);

        rl_careers.setOnClickListener(this);
        rl_ask_4_help.setOnClickListener(this);
        rl_financial_aid.setOnClickListener(this);
        rl_ourforum.setOnClickListener(this);
        image_news_events.setOnClickListener(this);
        view_not_registered.setOnClickListener(this);
        txt_sign_inn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_careers:

                break;
            case R.id.rl_ask_4_help:

                break;
            case R.id.rl_financial_aid:

                break;
            case R.id.rl_ourforum:

                break;
            case R.id.image_news_events:

                break;
            case R.id.view_not_registered:
                Intent ask_intent = new Intent(this, AskForInvitationActivity.class);
                startActivity(ask_intent);
                break;
            case R.id.txt_sign_inn:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
