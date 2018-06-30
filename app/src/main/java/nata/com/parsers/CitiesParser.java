package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.CitiesModel;
import nata.com.models.Model;
import nata.com.models.SpinnerModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class CitiesParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        CitiesModel mCitiesModel = new CitiesModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<CitiesModel> citiesModels = new ArrayList<>();
            ArrayList<SpinnerModel> statesSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                CitiesModel citiesModelItem = new CitiesModel();
                citiesModelItem.setId(jsonObject.optString("id"));
                citiesModelItem.setCity_name(jsonObject.optString("city_name"));
                citiesModelItem.setActive(jsonObject.optString("active"));
                citiesModelItem.setState_id(jsonObject.optString("state_id"));
                citiesModels.add(citiesModelItem);
                statesSpinnerModels.add(new SpinnerModel(jsonObject.optString("city_name")));
            }
            mCitiesModel.setCitiesModels(citiesModels);
            mCitiesModel.setCitiesSpinnerModels(statesSpinnerModels);
            mCitiesModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mCitiesModel.setStatus(false);
            mCitiesModel.setMessage("OOPS..! Some problem with the API");
        }

        return mCitiesModel;
    }
}
