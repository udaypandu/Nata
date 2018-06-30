package nata.com.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.adapters.PopularOurForumAdapter;
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
 * Created by Shankar Pilli on 11/4/2016
 */
public class PopularOurForumFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = "PopularOurForumFragment";
    private DashboardActivity mParent;
    private View rootView;

    private ForumModel mForumModel;
    private GridView grid_our_forum;
    private TextView tv_ltr;
    private PopularOurForumAdapter mPopularOurForumAdapter;
    int position = -1;
    private AdView mAdView;
    private LinearLayout ll_a_to_z;
    private String mAtoZLetters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static String mSelectedLetter = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle.containsKey("position")) {
            position = bundle.getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        rootView = inflater.inflate(R.layout.fragment_our_forum, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        ll_a_to_z = (LinearLayout) rootView.findViewById(R.id.ll_a_to_z);
        grid_our_forum = (GridView) rootView.findViewById(R.id.grid_our_forum);
        tv_ltr = (TextView) rootView.findViewById(R.id.tv_ltr);
        tv_ltr.setVisibility(View.INVISIBLE);
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        getOurForumData();

        if (Constants.logAddsOnOrOff) {
            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }
        if (position == 1)
            setAtoZLetters();
    }

    private void setAtoZLetters() {
        ll_a_to_z.removeAllViews();
        for (int i = 0; i < mAtoZLetters.length; i++) {
            TextView tvLetters = new TextView(mParent);
            tvLetters.setTypeface(Utility.setTypeFace_Lato_Regular(mParent));
            tvLetters.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            tvLetters.setPadding(10, 10, 10, 10);
            tvLetters.setGravity(Gravity.CENTER);
            tvLetters.setTextColor(Utility.getColor(mParent, R.color.white));
            tvLetters.setBackgroundColor(Utility.getColor(mParent, R.color.colorPrimary));
            tvLetters.setText(mAtoZLetters[i]);

            if (!mSelectedLetter.equalsIgnoreCase(mAtoZLetters[i])) {
                tvLetters.setBackgroundColor(Utility.getColor(mParent, R.color.colorPrimary));
            } else {
                tvLetters.setBackgroundColor(Utility.getColor(mParent, R.color.colorPrimary));
                tvLetters.setBackground(Utility.getDrawable(mParent, R.drawable.circle_letter_selecter));
            }

            tvLetters.setId(i);
            tvLetters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utility.isNetworkAvailable(mParent)) {
                        int mPosition = v.getId();
                        if (mSelectedLetter.equalsIgnoreCase(mAtoZLetters[mPosition])) {
                            mSelectedLetter = "";
                            setAtoZLetters();
                        } else {
                            mSelectedLetter = mAtoZLetters[mPosition];
                            setAtoZLetters();
                            getOurForumData();
                        }

                    } else {
                        Utility.showSettingDialog(
                                mParent,
                                Utility.getResourcesString(mParent,
                                        R.string.no_internet_msg),
                                Utility.getResourcesString(mParent,
                                        R.string.no_internet_title),
                                Utility.NO_INTERNET_CONNECTION).show();
                    }
                }
            });
            ll_a_to_z.addView(tvLetters);
        }
    }

    private void getOurForumData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("api_key", Constants.API_KEY_VALUE);
        paramMap.put("term", mSelectedLetter);
        ForumParser mForumParser = new ForumParser();
        String mFinalUrl;
        if (position == 0) {
            mFinalUrl = APIConstants.FORUM_ALL + "1";
        } else {
            mFinalUrl = APIConstants.FORUM_ALL + "0";
        }
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                mFinalUrl, paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mForumParser);
        Utility.execute(serverIntractorAsync);
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof ForumModel) {
                    mForumModel = (ForumModel) model;
                    if (mForumModel != null && mForumModel.getForumModels().size() > 0)
                    {
                        setDataToTheList();
                        grid_our_forum.setVisibility(View.VISIBLE);
                        tv_ltr.setVisibility(View.GONE);
                    }
                    else {
                        grid_our_forum.setVisibility(View.GONE);
                        tv_ltr.setVisibility(View.VISIBLE);
                    }

                } else {
                    Utility.setSnackBarEnglish(mParent, grid_our_forum, model.getMessage());
                }
            }
        }
    }

    private void setDataToTheList() {
        if (mForumModel.getForumModels() != null && mForumModel.getForumModels().size() > 0) {
            mPopularOurForumAdapter = new PopularOurForumAdapter(mParent, mForumModel.getForumModels());
            grid_our_forum.setAdapter(mPopularOurForumAdapter);
        }
    }
}