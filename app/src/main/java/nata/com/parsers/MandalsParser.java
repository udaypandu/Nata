package nata.com.parsers;

import android.content.Context;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.MandalsModel;
import nata.com.models.Model;
import nata.com.models.SpinnerModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class MandalsParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        MandalsModel mMandalsModel = new MandalsModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<MandalsModel> mandalsModels = new ArrayList<>();
            ArrayList<SpinnerModel> districtsSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                MandalsModel mandalsModelItem = new MandalsModel();
                mandalsModelItem.setId(jsonObject.optString("id"));
                mandalsModelItem.setMandal_name(jsonObject.optString("mandal_name"));
                mandalsModelItem.setActive(jsonObject.optString("active"));
                mandalsModelItem.setDistrict_id(jsonObject.optString("district_id"));
                mandalsModels.add(mandalsModelItem);
                districtsSpinnerModels.add(new SpinnerModel(jsonObject.optString("mandal_name")));
            }
            mMandalsModel.setMandalsModels(mandalsModels);
            mMandalsModel.setMandalsSpinnerModels(districtsSpinnerModels);
            mMandalsModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mMandalsModel.setStatus(false);
            mMandalsModel.setMessage("OOPS..! Some problem with the API");
        }

        return mMandalsModel;
    }
}
