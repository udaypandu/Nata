package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/31/2016.
 */

public class StatesModel extends Model {
    private String id;
    private String state_name;
    private String active;
    private String country_id;
    private String abbrivation;
    private ArrayList<StatesModel> statesModels;
    private ArrayList<SpinnerModel> statesSpinnerModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getAbbrivation() {
        return abbrivation;
    }

    public void setAbbrivation(String abbrivation) {
        this.abbrivation = abbrivation;
    }

    public ArrayList<StatesModel> getStatesModels() {
        return statesModels;
    }

    public void setStatesModels(ArrayList<StatesModel> statesModels) {
        this.statesModels = statesModels;
    }

    public ArrayList<SpinnerModel> getStatesSpinnerModels() {
        return statesSpinnerModels;
    }

    public void setStatesSpinnerModels(ArrayList<SpinnerModel> statesSpinnerModels) {
        this.statesSpinnerModels = statesSpinnerModels;
    }
}
