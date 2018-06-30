package nata.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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


public class OurForceCountryFragment extends Fragment implements AdapterView.OnItemClickListener, IAsyncCaller {

    public static final String TAG = "OurForceCountryFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_our_force_counry;
    private ListView list_view;
    private OurForceCountryListModel mOurForceCountryListModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mParent = (DashboardActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.our_force));
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_our_force, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        txt_our_force_counry = (TextView) rootView.findViewById(R.id.txt_our_force);
        txt_our_force_counry.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_our_force_counry.setText("List of Countries");
        list_view = (ListView) rootView.findViewById(R.id.list_view);
        list_view.setOnItemClickListener(this);
        getCountriesList();
    }


    private void getCountriesList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        OurForceCountyParser mParser = new OurForceCountyParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.GET_MEMBERS, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.OUR_FORCE_ID, mOurForceCountryListModel.getOurForceCountryModels().get(position).getId());
        Utility.navigateDashBoardFragment(new OurForceFragment(), OurForceFragment.TAG, bundle
                , mParent);
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
    }
}
