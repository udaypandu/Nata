package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.CountriesModel;
import nata.com.models.Model;
import nata.com.models.SpinnerModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class CountriesParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        CountriesModel countriesModel = new CountriesModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<CountriesModel> countriesModels = new ArrayList<>();
            ArrayList<SpinnerModel> countriesSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                CountriesModel countriesModelItem = new CountriesModel();
                countriesModelItem.setId(jsonObject.optString("id"));
                countriesModelItem.setCountry_name(jsonObject.optString("country_name"));
                countriesModelItem.setActive(jsonObject.optString("active"));
                countriesModelItem.setIso_code(jsonObject.optString("iso_code"));
                countriesModels.add(countriesModelItem);
                countriesSpinnerModels.add(new SpinnerModel(jsonObject.optString("country_name")));
            }
            countriesModel.setCountriesModels(countriesModels);
            countriesModel.setCountriesSpinnerModels(countriesSpinnerModels);
            countriesModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            countriesModel.setStatus(false);
            countriesModel.setMessage("OOPS..! Some problem with the API");
        }

        return countriesModel;
    }
}
