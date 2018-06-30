package nata.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;


import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.AskForHelpMyGroupsAdapter;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.ForumModel;
import nata.com.models.Model;
import nata.com.nata.R;
import nata.com.parsers.ForumParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskForHelpMyModerateFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = "AskForHelpMyModerateFragment";
    private DashboardActivity mParent;
    private View rootView;

    private ForumModel mForumModel;
    private GridView grid_our_forum;
   private AskForHelpMyGroupsAdapter mAskForHelpMyGroupsAdapter;
    private TextView tv_my_groups;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        rootView = inflater.inflate(R.layout.fragment_ask_for_help_my_moderate, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        grid_our_forum = (GridView) rootView.findViewById(R.id.grid_our_forum);
        tv_my_groups = (TextView) rootView.findViewById(R.id.tv_my_groups);
        tv_my_groups.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        getMyGroupsData();
    }

   private void getMyGroupsData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        ForumParser mForumParser = new ForumParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.ASK_MY_HELP_MODERATE_GROUPS
                , paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mForumParser);
        Utility.execute(serverIntractorAsync);
    }


   // @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof ForumModel) {
                    mForumModel = (ForumModel) model;
                    setDataToTheList();
                } else {
                    Utility.setSnackBarEnglish(mParent, grid_our_forum, model.getMessage());
                }
            }
        }
    }

    private void setDataToTheList() {
        if (mForumModel.getForumModels() != null && mForumModel.getForumModels().size() > 0) {
            mAskForHelpMyGroupsAdapter = new AskForHelpMyGroupsAdapter(mParent, mForumModel.getForumModels());
            grid_our_forum.setAdapter(mAskForHelpMyGroupsAdapter);
        }
    }
}