package nata.com.models;

import java.util.ArrayList;

/**
 * Created by Shankar on 4/6/2017.
 */

public class ForumDetailModel extends Model {
    private String id;
    private String catId;
    private String type;
    private String name;
    private String content;
    private String image;
    private String userid;
    private String recordeddate;
    private String isPrivate;
    private String username;
    private ArrayList<ForumsReplyModel> forumsReplyModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public ArrayList<ForumsReplyModel> getForumsReplyModels() {
        return forumsReplyModels;
    }

    public void setForumsReplyModels(ArrayList<ForumsReplyModel> forumsReplyModels) {
        this.forumsReplyModels = forumsReplyModels;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }
}
