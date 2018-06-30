package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/31/2016.
 */

public class VillagesModel extends Model {
    private String id;
    private String village_name;
    private String active;
    private String mandal_id;
    private ArrayList<VillagesModel> villagesModels;
    private ArrayList<SpinnerModel> villagesSpinnerModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getMandal_id() {
        return mandal_id;
    }

    public void setMandal_id(String mandal_id) {
        this.mandal_id = mandal_id;
    }

    public ArrayList<VillagesModel> getVillagesModels() {
        return villagesModels;
    }

    public void setVillagesModels(ArrayList<VillagesModel> villagesModels) {
        this.villagesModels = villagesModels;
    }

    public ArrayList<SpinnerModel> getVillagesSpinnerModels() {
        return villagesSpinnerModels;
    }

    public void setVillagesSpinnerModels(ArrayList<SpinnerModel> villagesSpinnerModels) {
        this.villagesSpinnerModels = villagesSpinnerModels;
    }
}
