package nata.com.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Shankar on 3/30/2017.
 */

public class ForumModel extends Model implements Serializable {
    private String id;
    private String name;
    private String image;
    private ArrayList<ForumModel> forumModels;
    private ArrayList<SpinnerModel> forumsSpinnerModels;;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<ForumModel> getForumModels() {
        return forumModels;
    }

    public void setForumModels(ArrayList<ForumModel> forumModels) {
        this.forumModels = forumModels;
    }

    public ArrayList<SpinnerModel> getForumsSpinnerModels() {
        return forumsSpinnerModels;
    }

    public void setForumsSpinnerModels(ArrayList<SpinnerModel> forumsSpinnerModels) {
        this.forumsSpinnerModels = forumsSpinnerModels;
    }
}
