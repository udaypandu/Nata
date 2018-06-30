package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.Model;
import nata.com.models.OurForceCountryListModel;
import nata.com.models.OurForceCountryModel;

/**
 * Created by Shankar on 03/29/2016.
 */

public class OurForceCountyParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        OurForceCountryListModel mOurForceCountryListModel = new OurForceCountryListModel();
        try {
           // JSONObject mJSONObject = new JSONObject(response);
            JSONArray mJSONArray = new JSONArray(response);

          //  JSONArray mJSONArray = mJSONObject.optJSONArray("users");
            ArrayList<OurForceCountryModel> ourForceCountryModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                OurForceCountryModel mOurForceCountryModelItem = new OurForceCountryModel();
                mOurForceCountryModelItem.setId(jsonObject.optString("id"));
                mOurForceCountryModelItem.setCount(jsonObject.optString("count"));
                if (jsonObject.has("country"))
                    mOurForceCountryModelItem.setCountry(jsonObject.optString("country"));
                if (jsonObject.has("state"))
                    mOurForceCountryModelItem.setCountry(jsonObject.optString("state"));
                if (jsonObject.has("district"))
                    mOurForceCountryModelItem.setCountry(jsonObject.optString("district"));
                if (jsonObject.has("mandal"))
                    mOurForceCountryModelItem.setCountry(jsonObject.optString("mandal"));
                if (jsonObject.has("village"))
                    mOurForceCountryModelItem.setCountry(jsonObject.optString("village"));
                ourForceCountryModels.add(mOurForceCountryModelItem);
            }
            mOurForceCountryListModel.setOurForceCountryModels(ourForceCountryModels);
            mOurForceCountryListModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mOurForceCountryListModel.setStatus(false);
            mOurForceCountryListModel.setMessage("OOPS..! Some problem with the API");
        }
        return mOurForceCountryListModel;
    }
}
