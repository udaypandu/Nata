package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.DistrictsModel;
import nata.com.models.Model;
import nata.com.models.SpinnerModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class DistrictsParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        DistrictsModel mDistrictsModel = new DistrictsModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<DistrictsModel> districtsModels = new ArrayList<>();
            ArrayList<SpinnerModel> districtsSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                DistrictsModel districtsModelItem = new DistrictsModel();
                districtsModelItem.setId(jsonObject.optString("id"));
                districtsModelItem.setDistrict_name(jsonObject.optString("district_name"));
                districtsModelItem.setActive(jsonObject.optString("active"));
                districtsModelItem.setState_id(jsonObject.optString("state_id"));
                districtsModels.add(districtsModelItem);
                districtsSpinnerModels.add(new SpinnerModel(jsonObject.optString("district_name")));
            }
            mDistrictsModel.setDistrictsModels(districtsModels);
            mDistrictsModel.setDistrictsSpinnerModels(districtsSpinnerModels);
            mDistrictsModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mDistrictsModel.setStatus(false);
            mDistrictsModel.setMessage("OOPS..! Some problem with the API");
        }

        return mDistrictsModel;
    }
}
