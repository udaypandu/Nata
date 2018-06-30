package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 10/31/2016.
 */

public class CountriesModel extends Model {
    private String id;
    private String country_name;
    private String active;
    private String iso_code;
    private ArrayList<CountriesModel> countriesModels;
    private ArrayList<SpinnerModel> countriesSpinnerModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getIso_code() {
        return iso_code;
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    public ArrayList<CountriesModel> getCountriesModels() {
        return countriesModels;
    }

    public void setCountriesModels(ArrayList<CountriesModel> countriesModels) {
        this.countriesModels = countriesModels;
    }

    public ArrayList<SpinnerModel> getCountriesSpinnerModels() {
        return countriesSpinnerModels;
    }

    public void setCountriesSpinnerModels(ArrayList<SpinnerModel> countriesSpinnerModels) {
        this.countriesSpinnerModels = countriesSpinnerModels;
    }
}
