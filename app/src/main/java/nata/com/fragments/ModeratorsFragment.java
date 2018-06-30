package nata.com.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.LinkedHashMap;

import nata.com.activities.DashboardActivity;
import nata.com.asynctask.IAsyncCaller;
import nata.com.asynctask.ServerIntractorAsync;
import nata.com.models.Model;
import nata.com.models.ModeratorsModel;
import nata.com.nata.R;
import nata.com.parsers.ModeratorsParser;
import nata.com.utility.APIConstants;
import nata.com.utility.Constants;
import nata.com.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModeratorsFragment extends Fragment implements View.OnClickListener, IAsyncCaller {

    public static final String TAG = "ModeratorsFragment";
    private DashboardActivity mParent;
    private View rootView;

    private ImageView img_moderators;
    private TextView txt_moderators;
    private TextView txt_email_id;
    private TextView tv_count;

    private TextView tv_previous_icon;
    private TextView tv_next_icon;

    private Typeface typeface;
    private ModeratorsModel mModeratorsModel;

    private int position = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.invalidateOptionsMenu();
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_moderators, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {
        typeface = Utility.setTypeFace_Lato_Regular(getActivity());

        img_moderators = (ImageView) rootView.findViewById(R.id.img_moderators);
        txt_moderators = (TextView) rootView.findViewById(R.id.txt_moderators);
        txt_email_id = (TextView) rootView.findViewById(R.id.txt_email_id);
        tv_count = (TextView) rootView.findViewById(R.id.tv_count);

        tv_previous_icon = (TextView) rootView.findViewById(R.id.tv_previous_icon);
        tv_next_icon = (TextView) rootView.findViewById(R.id.tv_next_icon);

        txt_moderators.setTypeface(typeface);
        txt_email_id.setTypeface(typeface);
        tv_count.setTypeface(typeface);

        tv_previous_icon.setTypeface(Utility.setTypeFace_Image(getActivity()));
        tv_next_icon.setTypeface(Utility.setTypeFace_Image(getActivity()));

        tv_previous_icon.setOnClickListener(this);
        tv_next_icon.setOnClickListener(this);
        getModeratorsData();
    }

    private void getModeratorsData() {
        LinkedHashMap<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put(Constants.API_KEY, Constants.API_KEY_VALUE);
        ModeratorsParser mModeratorsParser = new ModeratorsParser();
        ServerIntractorAsync serverIntractorAsync = new ServerIntractorAsync(getActivity(), Utility.getResourcesString(getActivity(),
                R.string.please_wait), true,
                APIConstants.GET_MODERATORS_FORUM + ForumTabDetailFragment.forumModel.getId(), paramMap,
                APIConstants.REQUEST_TYPE.GET, this, mModeratorsParser);
        Utility.execute(serverIntractorAsync);
    }

    private void setImageData(String from) {
        if (mModeratorsModel != null && mModeratorsModel.getModeratorsModels().size() > 0) {
            if (!Utility.isValueNullOrEmpty(mModeratorsModel.getModeratorsModels().get(position).getPhoto()))
                Utility.universalImageLoadercirclePicLoading(img_moderators,
                        mModeratorsModel.getModeratorsModels().get(position).getPhoto(), null, R.drawable.avatar_image);
            else
                img_moderators.setImageDrawable(Utility.getDrawable(mParent, R.drawable.avatar_image));
            txt_moderators.setText(Utility.capitalizeFirstLetter(mModeratorsModel.getModeratorsModels().get(position).getFirstname()) + " "
                    + Utility.capitalizeFirstLetter(mModeratorsModel.getModeratorsModels().get(position).getLastname()));
            txt_email_id.setText(mModeratorsModel.getModeratorsModels().get(position).getEmail());
            tv_count.setText("" + (position + 1) + "/" + mModeratorsModel.getModeratorsModels().size());
        }

        if (position == 0) {
            tv_previous_icon.setVisibility(View.GONE);
            tv_next_icon.setVisibility(View.VISIBLE);
        } else if (position == (mModeratorsModel.getModeratorsModels().size() - 1)) {
            tv_next_icon.setVisibility(View.GONE);
            tv_previous_icon.setVisibility(View.VISIBLE);
        } else {
            tv_next_icon.setVisibility(View.VISIBLE);
            tv_previous_icon.setVisibility(View.VISIBLE);
        }

        if (from.equalsIgnoreCase(Constants.FIRST_TIME) && mModeratorsModel != null
                && mModeratorsModel.getModeratorsModels().size() == 1) {
            tv_next_icon.setVisibility(View.GONE);
            tv_previous_icon.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next_icon:
                position = position + 1;
                setImageData(Constants.API_TIME);
                break;
            case R.id.tv_previous_icon:
                position = position - 1;
                setImageData(Constants.API_TIME);
                break;
        }
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model.isStatus()) {
                if (model instanceof ModeratorsModel) {
                    mModeratorsModel = (ModeratorsModel) model;
                    setImageData(Constants.FIRST_TIME);
                } else {
                    Utility.setSnackBarEnglish(mParent, img_moderators, model.getMessage());
                }
            }
        }
    }
}
