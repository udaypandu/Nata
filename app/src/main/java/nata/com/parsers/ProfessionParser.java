package nata.com.parsers;

import android.content.Context;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.Model;
import nata.com.models.ProfessionModel;
import nata.com.models.SpinnerModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class ProfessionParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        ProfessionModel mProfessionModel = new ProfessionModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<ProfessionModel> mProfessionModels = new ArrayList<>();
            ArrayList<SpinnerModel> statesSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                ProfessionModel mProfessionModelItem = new ProfessionModel();
                mProfessionModelItem.setId(jsonObject.optString("id"));
                mProfessionModelItem.setValue(jsonObject.optString("name"));
                mProfessionModels.add(mProfessionModelItem);
                statesSpinnerModels.add(new SpinnerModel(jsonObject.optString("name")));
            }
            mProfessionModel.setSelectIdProofModels(mProfessionModels);
            mProfessionModel.setSpinnerModels(statesSpinnerModels);
            mProfessionModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mProfessionModel.setStatus(false);
            mProfessionModel.setMessage("OOPS..! Some problem with the API");
        }

        return mProfessionModel;
    }
}
