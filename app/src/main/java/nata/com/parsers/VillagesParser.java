package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.Model;
import nata.com.models.SpinnerModel;
import nata.com.models.VillagesModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class VillagesParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        VillagesModel mVillagesModel = new VillagesModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<VillagesModel> villagesModels = new ArrayList<>();
            ArrayList<SpinnerModel> districtsSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                VillagesModel villagesModelItem = new VillagesModel();
                villagesModelItem.setId(jsonObject.optString("id"));
                villagesModelItem.setVillage_name(jsonObject.optString("village_name"));
                villagesModelItem.setActive(jsonObject.optString("active"));
                villagesModelItem.setMandal_id(jsonObject.optString("mandal_id"));
                villagesModels.add(villagesModelItem);
                districtsSpinnerModels.add(new SpinnerModel(jsonObject.optString("village_name")));
            }
            mVillagesModel.setVillagesModels(villagesModels);
            mVillagesModel.setVillagesSpinnerModels(districtsSpinnerModels);
            mVillagesModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mVillagesModel.setStatus(false);
            mVillagesModel.setMessage("OOPS..! Some problem with the API");
        }

        return mVillagesModel;
    }
}
