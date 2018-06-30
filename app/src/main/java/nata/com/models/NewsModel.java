package nata.com.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Shankar on 11/1/2016.
 */
public class NewsModel extends Model implements Serializable {
    private String id;
    private String site_content_type;
    private String title;
    private String event_date;
    private String description;
    private String image_name;
    private ArrayList<String> other_images;
    private String address;
    private String created;
    private String lastupdated;
    private String active;
    private String status;
    private String createdBy;
    private ArrayList<NewsModel> newsModels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_content_type() {
        return site_content_type;
    }

    public void setSite_content_type(String site_content_type) {
        this.site_content_type = site_content_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public ArrayList<String> getOther_images() {
        return other_images;
    }

    public void setOther_images(ArrayList<String> other_images) {
        this.other_images = other_images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ArrayList<NewsModel> getNewsModels() {
        return newsModels;
    }

    public void setNewsModels(ArrayList<NewsModel> newsModels) {
        this.newsModels = newsModels;
    }
}
