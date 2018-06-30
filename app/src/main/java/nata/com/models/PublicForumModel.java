package nata.com.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shankar on 3/30/2017.
 */

public class PublicForumModel extends Model implements Serializable {
    private String id;
    private String name;
    private String recordeddate;
    private String username;
    private String replies;
    private ArrayList<PublicForumModel> publicForumModels;

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

    public String getRecordeddate() {
        return recordeddate;
    }

    public void setRecordeddate(String recordeddate) {
        this.recordeddate = recordeddate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<PublicForumModel> getPublicForumModels() {
        return publicForumModels;
    }

    public void setPublicForumModels(ArrayList<PublicForumModel> publicForumModels) {
        this.publicForumModels = publicForumModels;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }
}
