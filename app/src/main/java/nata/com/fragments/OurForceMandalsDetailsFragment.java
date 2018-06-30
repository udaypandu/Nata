package nata.com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;



import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.OurForceAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.Model;
import nata.com.models.OurForceCountryListModel;
import nata.com.nata.R;
import nata.com.parsers.OurForceCountyParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * Created by shankar on 2/4/2017.
 */

public class OurForceMandalsDetailsFragment extends Fragment implements IAsyncCaller {
    public static final String TAG = "OurForceFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_andhra_pradesh;
    private ListView list_view;
    private LinearLayout ll_founders;
    private String mMandalId = "";
    private String mMandalName = "";

    private OurForceCountryListModel mOurForceCountryListModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mMandalId = getArguments().getString(Constants.OUR_FORCE_ID);
        mMandalName = getArguments().getString(Constants.OUR_FORCE_NAME);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.members_caps));
        rootView = inflater.inflate(R.layout.fragment_our_force_details, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        txt_andhra_pradesh = (TextView) rootView.findViewById(R.id.txt_andhra_pradesh);
        ll_founders = (LinearLayout) rootView.findViewById(R.id.ll_founders);
        txt_andhra_pradesh.setText(mMandalName + " - Mandal Team Incharges");
        txt_andhra_pradesh.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        list_view = (ListView) rootView.findViewById(R.id.listView);
        getMandalList();
    }

    private void getMandalList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        OurForceCountyParser mParser = new OurForceCountyParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.GET_OUR_FORCE_VILLAGE + mMandalId, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }


    private void setDataToTheEventsLayout() {
        ll_founders.removeAllViews();
        //if (mEventsModel != null && mEventsModel.getEventsModels().size() > 0)
        //for (int i = 0; i < mEventsModel.getEventsModels().size(); i++) {
        for (int i = 0; i < 4; i++) {
            LinearLayout child = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.founders_item, null);
            ImageView img_thumbnail = (ImageView) child.findViewById(R.id.img_thumbnail);
            ProgressBar progress = (ProgressBar) child.findViewById(R.id.progress);
            TextView txt_name = (TextView) child.findViewById(R.id.txt_name);
            TextView txt_mail = (TextView) child.findViewById(R.id.txt_mail);
            img_thumbnail.setImageDrawable(Utility.getDrawable(getActivity(), R.drawable.place_holder));
            txt_name.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
            txt_mail.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

            if (i == 0) {
                txt_name.setText("Varaprasad Yalla");
                txt_mail.setText("yallaveraprasad@gmail.com");
            } else if (i == 1) {
                txt_name.setText("Ravi Kiran Koya");
                txt_mail.setText("ravikiran.koya@gmail.com");
            } else if (i == 2) {
                txt_name.setText("Shiva Poiala");
                txt_mail.setText("shiva_poiala@gmail.com");
            } else if (i == 3) {
                txt_name.setText("Uday Koya");
                txt_mail.setText("udaykoya@gmail.com");
            }

                /*Utility.URLProfilePicLoading(img_thumbnail, "http://www.kapuwelfare.com/" + mEventsModel.getEventsModels().get(i).getImage(),
                        progress, R.drawable.avatar_image_flat);*/

            ll_founders.addView(child);
        }
        //}
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof OurForceCountryListModel) {
                    mOurForceCountryListModel = (OurForceCountryListModel) model;
                    setDataToList();
                }
            }
        }
    }

    /**
     * This method is used to show the data
     */
    private void setDataToList() {
        OurForceAdapter ourForceAdapter = new OurForceAdapter(mParent, mOurForceCountryListModel.getOurForceCountryModels());
        list_view.setAdapter(ourForceAdapter);
        setDataToTheEventsLayout();
    }

}
