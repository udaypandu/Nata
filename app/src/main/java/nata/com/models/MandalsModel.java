package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/31/2016.
 */

public class MandalsModel extends Model {
    private String id;
    private String mandal_name;
    private String active;
    private String district_id;
    private ArrayList<MandalsModel> mandalsModels;
    private ArrayList<SpinnerModel> mandalsSpinnerModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMandal_name() {
        return mandal_name;
    }

    public void setMandal_name(String mandal_name) {
        this.mandal_name = mandal_name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public ArrayList<MandalsModel> getMandalsModels() {
        return mandalsModels;
    }

    public void setMandalsModels(ArrayList<MandalsModel> mandalsModels) {
        this.mandalsModels = mandalsModels;
    }

    public ArrayList<SpinnerModel> getMandalsSpinnerModels() {
        return mandalsSpinnerModels;
    }

    public void setMandalsSpinnerModels(ArrayList<SpinnerModel> mandalsSpinnerModels) {
        this.mandalsSpinnerModels = mandalsSpinnerModels;
    }
}
