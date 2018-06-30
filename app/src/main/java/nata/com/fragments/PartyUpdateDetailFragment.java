package nata.com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nata.com.activities.DashboardActivity;
import nata.com.nata.R;
import nata.com.utility.Utility;


/**
 * Created by shankar on 2/3/17.
 */

public class PartyUpdateDetailFragment extends Fragment {

    public static final String TAG = "PartyUpdateDetailFragment";
    private DashboardActivity mParent;
    private View rootView;

    private TextView txt_left_icon;
    private TextView txt_right_icon;

    private TextView txt_first_article_header;
    private TextView txt_details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParent.getSupportActionBar().setTitle(Utility.getResourcesString(getActivity(), R.string.party_updates));
        rootView = inflater.inflate(R.layout.party_update_details, container, false);
        initUI();
        return rootView;
    }

    private void initUI() {

        txt_left_icon = (TextView) rootView.findViewById(R.id.txt_left_icon);
        txt_right_icon = (TextView) rootView.findViewById(R.id.txt_right_icon);

        txt_first_article_header = (TextView) rootView.findViewById(R.id.txt_first_article_header);
        txt_details = (TextView) rootView.findViewById(R.id.txt_details);

        txt_right_icon.setTypeface(Utility.setTypeFace_fontawesome(getActivity()));
        txt_left_icon.setTypeface(Utility.setTypeFace_fontawesome(getActivity()));

        txt_first_article_header.setText("AP Demands special status | Pawan kalyan speak to national media");
        txt_details.setText("Jana Sena leader Pawan Kalyan is taking forward the movement of '#ap demands special status' by extending the support from his party. He urged the Andhra Pradesh Youth to take part in the movement with peaceful protests. He said,\"Youth of AP\" should raise their voice through peaceful protests is the only remedy ,to achieve the promised \"SCS\".\n" +
                "\n" +
                "He further extended his support to silent protest at R K Beach in Vizag on Ja. 26th, 2017. He wrote \"If the youth of AP are planning to do a silent protest on 26th Jan at RK Beach, Vizag, Janasena Supports them.\" on his twitter handle.\n" +
                "\n" +
                "He further added that he is going to release a musical protest album on Jan. 24th which was supposed to release on Feb. 3rd in order to support the movement of 'AP Demands special status'. He said \"JanaSena raises its 'Voice against Opportunistic, Divisive & Criminal Politics;through a Protest Musical album\"DeshBachao !\"APDemandsSpecialStatus,I had planned a Musical protest album DeshBachao to be released on 5th Feb but will prepone its release to 24th Jan.\"\n" +
                "\n" +
                "He even added that Courage, self-respect, Integrity and accountability is lacking in Andhra politicians.");

        txt_first_article_header.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));
        txt_details.setTypeface(Utility.setTypeFace_Lato_Regular(getActivity()));

    }

}
