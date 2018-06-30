package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.Model;
import nata.com.models.ModeratorsModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class ModeratorsParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        ModeratorsModel mModeratorsModel = new ModeratorsModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<ModeratorsModel> mModeratorsModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                ModeratorsModel mModeratorsModelItem = new ModeratorsModel();
                mModeratorsModelItem.setFirstname(jsonObject.optString("firstname"));
                mModeratorsModelItem.setLastname(jsonObject.optString("lastname"));
                mModeratorsModelItem.setEmail(jsonObject.optString("email"));
                mModeratorsModelItem.setPhoto(jsonObject.optString("photo"));
                mModeratorsModels.add(mModeratorsModelItem);
            }
            mModeratorsModel.setModeratorsModels(mModeratorsModels);
            mModeratorsModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mModeratorsModel.setStatus(false);
            mModeratorsModel.setMessage("OOPS..! Some problem with the API");
        }
        return mModeratorsModel;
    }
}
