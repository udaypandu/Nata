package nata.com.parsers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.BloodGroupModel;
import nata.com.models.Model;
import nata.com.models.SpinnerModel;

/**
 * Created by Shankar on 03/29/2016.
 */

public class BloodGroupParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        BloodGroupModel mBloodGroupModel = new BloodGroupModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<BloodGroupModel> mBloodGroupModels = new ArrayList<>();
            ArrayList<SpinnerModel> statesSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                BloodGroupModel mBloodGroupModelItem = new BloodGroupModel();
                mBloodGroupModelItem.setId(jsonObject.optString("id"));
                mBloodGroupModelItem.setBlood_group(jsonObject.optString("blood_group"));
                mBloodGroupModels.add(mBloodGroupModelItem);
                statesSpinnerModels.add(new SpinnerModel(jsonObject.optString("blood_group")));
            }
            mBloodGroupModel.setBloodGroupModels(mBloodGroupModels);
            mBloodGroupModel.setSpinnerModels(statesSpinnerModels);
            mBloodGroupModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mBloodGroupModel.setStatus(false);
            mBloodGroupModel.setMessage("OOPS..! Some problem with the API");
        }
        return mBloodGroupModel;
    }
}
