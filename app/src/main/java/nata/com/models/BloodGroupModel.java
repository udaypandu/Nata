package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 3/29/2017.
 */
public class BloodGroupModel extends Model {
    private String id;
    private String blood_group;
    private ArrayList<BloodGroupModel> bloodGroupModels;
    private ArrayList<SpinnerModel> spinnerModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public ArrayList<BloodGroupModel> getBloodGroupModels() {
        return bloodGroupModels;
    }

    public void setBloodGroupModels(ArrayList<BloodGroupModel> bloodGroupModels) {
        this.bloodGroupModels = bloodGroupModels;
    }

    public ArrayList<SpinnerModel> getSpinnerModels() {
        return spinnerModels;
    }

    public void setSpinnerModels(ArrayList<SpinnerModel> spinnerModels) {
        this.spinnerModels = spinnerModels;
    }
}
