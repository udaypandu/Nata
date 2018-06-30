package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 1/14/2017.
 */
public class SelectIdProofModel extends Model {
    private String id;
    private String value;
    private ArrayList<SelectIdProofModel> selectIdProofModels;
    private ArrayList<SpinnerModel> spinnerModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<SelectIdProofModel> getSelectIdProofModels() {
        return selectIdProofModels;
    }

    public void setSelectIdProofModels(ArrayList<SelectIdProofModel> selectIdProofModels) {
        this.selectIdProofModels = selectIdProofModels;
    }

    public ArrayList<SpinnerModel> getSpinnerModels() {
        return spinnerModels;
    }

    public void setSpinnerModels(ArrayList<SpinnerModel> spinnerModels) {
        this.spinnerModels = spinnerModels;
    }
}
