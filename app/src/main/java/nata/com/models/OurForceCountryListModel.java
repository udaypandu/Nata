package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 9/11/2017.
 */

public class OurForceCountryListModel extends Model {

    private ArrayList<OurForceCountryModel> ourForceCountryModels;

    public ArrayList<OurForceCountryModel> getOurForceCountryModels() {
        return ourForceCountryModels;
    }

    public void setOurForceCountryModels(ArrayList<OurForceCountryModel> ourForceCountryModels) {
        this.ourForceCountryModels = ourForceCountryModels;
    }
}
