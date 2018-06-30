package nata.com.models;

/**
 * Created by Shankar on 4/6/2017.
 */

public class ForumsReplyModel {
    private String id;
    private String topicid;
    private String content;
    private String image;
    private String userid;
    private String recordeddate;
    private String isAbuse;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicid() {
        return topicid;
    }

    public void setTopicid(String topicid) {
        this.topicid = topicid;
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

    public String getIsAbuse() {
        return isAbuse;
    }

    public void setIsAbuse(String isAbuse) {
        this.isAbuse = isAbuse;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
