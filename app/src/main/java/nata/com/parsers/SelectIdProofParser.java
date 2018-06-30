package nata.com.parsers;

import android.content.Context;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.Model;
import nata.com.models.SelectIdProofModel;
import nata.com.models.SpinnerModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class SelectIdProofParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        SelectIdProofModel mSelectIdProofModel = new SelectIdProofModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<SelectIdProofModel> selectIdProofModels = new ArrayList<>();
            ArrayList<SpinnerModel> statesSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                SelectIdProofModel mSelectIdProofModelItem = new SelectIdProofModel();
                mSelectIdProofModelItem.setId(jsonObject.optString("id"));
                mSelectIdProofModelItem.setValue(jsonObject.optString("value"));
                selectIdProofModels.add(mSelectIdProofModelItem);
                statesSpinnerModels.add(new SpinnerModel(jsonObject.optString("value")));
            }
            mSelectIdProofModel.setSelectIdProofModels(selectIdProofModels);
            mSelectIdProofModel.setSpinnerModels(statesSpinnerModels);
            mSelectIdProofModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mSelectIdProofModel.setStatus(false);
            mSelectIdProofModel.setMessage("OOPS..! Some problem with the API");
        }

        return mSelectIdProofModel;
    }
}
