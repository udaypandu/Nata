package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.Model;
import nata.com.models.SpinnerModel;
import nata.com.models.StatesModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class StatesParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        StatesModel mStatesModel = new StatesModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<StatesModel> statesModels = new ArrayList<>();
            ArrayList<SpinnerModel> statesSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                StatesModel statesModelItem = new StatesModel();
                statesModelItem.setId(jsonObject.optString("id"));
                statesModelItem.setState_name(jsonObject.optString("state_name"));
                statesModelItem.setActive(jsonObject.optString("active"));
                statesModelItem.setCountry_id(jsonObject.optString("country_id"));
                statesModelItem.setAbbrivation(jsonObject.optString("abbrivation"));
                statesModels.add(statesModelItem);
                statesSpinnerModels.add(new SpinnerModel(jsonObject.optString("state_name")));
            }
            mStatesModel.setStatesModels(statesModels);
            mStatesModel.setStatesSpinnerModels(statesSpinnerModels);
            mStatesModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mStatesModel.setStatus(false);
            mStatesModel.setMessage("OOPS..! Some problem with the API");
        }

        return mStatesModel;
    }
}
