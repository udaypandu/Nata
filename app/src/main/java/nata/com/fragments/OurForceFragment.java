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

/**
 * Created by shankar on 2/4/2017.
 */

public class OurForceFragment extends Fragment implements AdapterView.OnItemClickListener, IAsyncCaller {
    public static final String TAG = "OurForceFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_our_force;
    private ListView list_view;
    private OurForceCountryListModel mOurForceCountryListModel;
    private String mCountryId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        mCountryId = getArguments().getString(Constants.OUR_FORCE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.members_caps));
        rootView = inflater.inflate(R.layout.fragment_our_force, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        txt_our_force = (TextView) rootView.findViewById(R.id.txt_our_force);
        txt_our_force.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_our_force.setText("List of States");
        list_view = (ListView) rootView.findViewById(R.id.list_view);
        list_view.setOnItemClickListener(this);
        getStatesList();
    }


    private void getStatesList() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        OurForceCountyParser mParser = new OurForceCountyParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.GET_OUR_FORCE_STATE + mCountryId, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mParser);
        Utility.execute(serverIntractorAsync);
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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.OUR_FORCE_ID, mOurForceCountryListModel.getOurForceCountryModels().get(position).getId());
        bundle.putString(Constants.OUR_FORCE_NAME, mOurForceCountryListModel.getOurForceCountryModels().get(position).getCountry());
        Utility.navigateDashBoardFragment(new OurForceDetailsFragment(), OurForceDetailsFragment.TAG, bundle
                , mParent);
    }
}
