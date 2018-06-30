package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 4/6/2017.
 */

public class ForumListModel extends Model {
    private int total;
    private ArrayList<PublicForumModel> publicForumModels;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<PublicForumModel> getPublicForumModels() {
        return publicForumModels;
    }

    public void setPublicForumModels(ArrayList<PublicForumModel> publicForumModels) {
        this.publicForumModels = publicForumModels;
    }
}
