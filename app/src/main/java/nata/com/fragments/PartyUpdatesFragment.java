package nata.com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import nata.com.activities.DashboardActivity;
import nata.com.asynctask.IAsyncCaller;
import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by Shankar Pilli
 */

public class PartyUpdatesFragment extends Fragment implements IAsyncCaller {
    public static final String TAG = "PartyUpdatesFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_home_heading;
    private ImageView img_news_home;
    private LinearLayout ll_news;


    private LinearLayout ll_careers;
    private LinearLayout ll_ask_help_footer;
    private LinearLayout ll_financial_aid;
    private LinearLayout ll_forum;

    private TextView txt_careers_icon;
    private TextView txt_askforhelp_icon;
    private TextView txt_financial_aid_icon;
    private TextView txt_our_forum_icon;

    private TextView txt_careers;
    private TextView txt_askforhelp;
    private TextView txt_financial_aid;
    private TextView txt_our_forum;

    private LinearLayout mLayoutDashBoardFootter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.party_updates).toUpperCase());
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_news_home_page, container, false);
        initUI();
        return rootView;

    }

    private void initUI() {

        txt_home_heading = (TextView) rootView.findViewById(R.id.txt_home_heading);
        img_news_home = (ImageView) rootView.findViewById(R.id.img_news_home);
        ll_news = (LinearLayout) rootView.findViewById(R.id.ll_news);

        ll_careers = (LinearLayout) rootView.findViewById(R.id.ll_careers);
        ll_ask_help_footer = (LinearLayout) rootView.findViewById(R.id.ll_ask_help_footer);
        ll_financial_aid = (LinearLayout) rootView.findViewById(R.id.ll_financial_aid);
        ll_forum = (LinearLayout) rootView.findViewById(R.id.ll_forum);

        txt_careers_icon = (TextView) rootView.findViewById(R.id.txt_careers_icon);
        txt_askforhelp_icon = (TextView) rootView.findViewById(R.id.txt_askforhelp_icon);
        txt_our_forum_icon = (TextView) rootView.findViewById(R.id.txt_our_forum_icon);
        txt_financial_aid_icon = (TextView) rootView.findViewById(R.id.txt_financial_aid_icon);

        txt_careers = (TextView) rootView.findViewById(R.id.txt_careers);
        txt_askforhelp = (TextView) rootView.findViewById(R.id.txt_askforhelp);
        txt_financial_aid = (TextView) rootView.findViewById(R.id.txt_financial_aid);
        txt_our_forum = (TextView) rootView.findViewById(R.id.txt_our_forum);

        mLayoutDashBoardFootter = (LinearLayout) rootView.findViewById(R.id.LayoutDashBoardFootter);
        mLayoutDashBoardFootter.setVisibility(View.GONE);

        txt_careers_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_our_forum_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_financial_aid_icon.setTypeface(Utility.setTypeFace_Image(mParent));
        txt_askforhelp_icon.setTypeface(Utility.setTypeFace_Image(mParent));

        txt_careers.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_askforhelp.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_financial_aid.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
        txt_our_forum.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));

        txt_home_heading.setTypeface(Utility.setTypeFace_Lato_Bold(mParent));

        getNewsData();
    }

    private void getNewsData() {

        /*ArrayList<NewsModel> newsModels = new ArrayList<>();
        NewsModel newsModelone = new NewsModel();
        newsModelone.setText("AP Demands special status | Pawan kalyan speak to national media");
        newsModelone.setImage_id(R.drawable.news_one);
        newsModelone.setDate("26th Jan 2017");
        newsModels.add(newsModelone);

        NewsModel newsModelTwo = new NewsModel();
        newsModelTwo.setText("Janasena to launch its 'protest musical album' on Jena sena youtube channel");
        newsModelTwo.setImage_id(R.drawable.news_two);
        newsModelTwo.setDate("24th Jan 2017");
        newsModels.add(newsModelTwo);

        NewsModel newsModelThree = new NewsModel();
        newsModelThree.setText("Jenasena's Chief Pawan Kalyan talking to the formers for Amaravathi");
        newsModelThree.setImage_id(R.drawable.news_three);
        newsModelThree.setDate("18th Jan 2017");
        newsModels.add(newsModelThree);

        NewsModel newsModelFour = new NewsModel();
        newsModelFour.setText("Pawan Kalyan with the core team of the doctors n activists working on Uddanam");
        newsModelFour.setImage_id(R.drawable.news_four);
        newsModelFour.setDate("3rd Jan 2017");
        newsModels.add(newsModelFour);

        NewsModel newsModelFive = new NewsModel();
        newsModelFive.setText("Pawan Kalyan during road show at ichhapuram");
        newsModelFive.setImage_id(R.drawable.news_five);
        newsModelFive.setDate("3rd Jan 2017");
        newsModels.add(newsModelFive);*/

        // LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();

        ll_news.removeAllViews();
        //if (mEventsModel != null && mEventsModel.getEventsModels().size() > 0)
        /*for (int i = 0; i < newsModels.size(); i++) {
            LinearLayout child = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.news_home_page_item, null);
            TextView txt_date = (TextView) child.findViewById(R.id.txt_date);
            TextView txt_matter = (TextView) child.findViewById(R.id.txt_matter);
            ImageView img_news_home = (ImageView) child.findViewById(R.id.img_news_home);

            txt_date.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
            txt_matter.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));

            img_news_home.setImageDrawable(Utility.getDrawable(getActivity(), newsModels.get(i).getImage_id()));
            txt_matter.setText(newsModels.get(i).getText());
            txt_date.setText(newsModels.get(i).getDate());

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.navigateDashBoardFragment(new PartyUpdateDetailFragment(), PartyUpdateDetailFragment.TAG, null, mParent);
                }
            });

            ll_news.addView(child);
        }*/
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {

            }
        }
    }
}
