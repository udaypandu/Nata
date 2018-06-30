package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/31/2016.
 */

public class CitiesModel extends Model {
    private String id;
    private String city_name;
    private String active;
    private String state_id;
    private ArrayList<CitiesModel> citiesModels;
    private ArrayList<SpinnerModel> citiesSpinnerModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public ArrayList<CitiesModel> getCitiesModels() {
        return citiesModels;
    }

    public void setCitiesModels(ArrayList<CitiesModel> citiesModels) {
        this.citiesModels = citiesModels;
    }

    public ArrayList<SpinnerModel> getCitiesSpinnerModels() {
        return citiesSpinnerModels;
    }

    public void setCitiesSpinnerModels(ArrayList<SpinnerModel> citiesSpinnerModels) {
        this.citiesSpinnerModels = citiesSpinnerModels;
    }
}
