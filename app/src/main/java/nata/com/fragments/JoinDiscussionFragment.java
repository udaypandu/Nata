package nata.com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;



import java.util.ArrayList;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.JoinDiscussionViewAdapter;
import nata.com.models.JanaswaramModel;
import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * Created by shankar on 2/4/2017.
 */

public class JoinDiscussionFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG = "JoinDiscussionFragment";
    private DashboardActivity mParent;
    private View rootView;

    private GridView grid_view;
    private JoinDiscussionViewAdapter joinDiscussionViewAdapter;
    private ArrayList<JanaswaramModel> janaswaramModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.janaswaram));
        rootView = inflater.inflate(R.layout.fragment_join_discussion, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        grid_view = (GridView) rootView.findViewById(R.id.grid_view);
        janaswaramModels = new ArrayList<>();

        JanaswaramModel janaswaramModel = new JanaswaramModel();
        janaswaramModel.setId(R.drawable.ap_splcial_status);
        janaswaramModel.setName("AP Special Status");
        janaswaramModels.add(janaswaramModel);

        JanaswaramModel janaswaramModel1 = new JanaswaramModel();
        janaswaramModel1.setId(R.drawable.jallikattu);
        janaswaramModel1.setName("Jallikattu");
        janaswaramModels.add(janaswaramModel1);

        JanaswaramModel janaswaramModel2 = new JanaswaramModel();
        janaswaramModel2.setId(R.drawable.news_three);
        janaswaramModel2.setName("Wavers Meet");
        janaswaramModels.add(janaswaramModel2);

        JanaswaramModel janaswaramModel3 = new JanaswaramModel();
        janaswaramModel3.setId(R.drawable.farmers_of_amaravathi);
        janaswaramModel3.setName("Farmers of Amaravathi");
        janaswaramModels.add(janaswaramModel3);

        JanaswaramModel janaswaramModel4 = new JanaswaramModel();
        janaswaramModel4.setId(R.drawable.ap_splcial_status);
        janaswaramModel4.setName("AP Special Status");
        janaswaramModels.add(janaswaramModel4);

        JanaswaramModel janaswaramModel5 = new JanaswaramModel();
        janaswaramModel5.setId(R.drawable.jallikattu);
        janaswaramModel5.setName("Jallikattu");
        janaswaramModels.add(janaswaramModel5);

        joinDiscussionViewAdapter = new JoinDiscussionViewAdapter(mParent, janaswaramModels);
        grid_view.setAdapter(joinDiscussionViewAdapter);
        grid_view.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Utility.navigateDashBoardFragment(new JoinDiscussionViewFragment(), JoinDiscussionViewFragment.TAG, null, mParent);
    }
}
