package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 4/6/2017.
 */

public class MembersListModel extends Model {
    private String total;
    private ArrayList<MembersModel> membersModels;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<MembersModel> getMembersModels() {
        return membersModels;
    }

    public void setMembersModels(ArrayList<MembersModel> membersModels) {
        this.membersModels = membersModels;
    }
}
