package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/31/2016.
 */

public class DistrictsModel extends Model {
    private String id;
    private String district_name;
    private String active;
    private String state_id;
    private ArrayList<DistrictsModel> districtsModels;
    private ArrayList<SpinnerModel> districtsSpinnerModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
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

    public ArrayList<DistrictsModel> getDistrictsModels() {
        return districtsModels;
    }

    public void setDistrictsModels(ArrayList<DistrictsModel> districtsModels) {
        this.districtsModels = districtsModels;
    }

    public ArrayList<SpinnerModel> getDistrictsSpinnerModels() {
        return districtsSpinnerModels;
    }

    public void setDistrictsSpinnerModels(ArrayList<SpinnerModel> districtsSpinnerModels) {
        this.districtsSpinnerModels = districtsSpinnerModels;
    }
}
