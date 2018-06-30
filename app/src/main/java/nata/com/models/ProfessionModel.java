package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 1/14/2017.
 */
public class ProfessionModel extends Model {
    private String id;
    private String value;
    private ArrayList<ProfessionModel> professionModels;
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

    public ArrayList<ProfessionModel> getSelectIdProofModels() {
        return professionModels;
    }

    public void setSelectIdProofModels(ArrayList<ProfessionModel> selectIdProofModels) {
        this.professionModels = selectIdProofModels;
    }

    public ArrayList<SpinnerModel> getSpinnerModels() {
        return spinnerModels;
    }

    public void setSpinnerModels(ArrayList<SpinnerModel> spinnerModels) {
        this.spinnerModels = spinnerModels;
    }
}
