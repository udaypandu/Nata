package nata.com.models;

import java.util.ArrayList;

/**
 * Created by shankar on 4/1/2017.
 */

public class ModeratorsModel extends Model {
    private String firstname;
    private String lastname;
    private String email;
    private String photo;
    private ArrayList<ModeratorsModel> moderatorsModels;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<ModeratorsModel> getModeratorsModels() {
        return moderatorsModels;
    }

    public void setModeratorsModels(ArrayList<ModeratorsModel> moderatorsModels) {
        this.moderatorsModels = moderatorsModels;
    }
}
