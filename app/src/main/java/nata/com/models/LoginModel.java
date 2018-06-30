package nata.com.models;

/**
 * Created by Shankar on 12/30/2016.
 */

public class LoginModel extends Model {
    private String uuid;
    private String email;
    private String role;
    private String photo;
    private String role_name;
    private String first_name;
    private String last_name;
    private String ci_session;
    private boolean group_moderator;
    private String admin_link;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCi_session() {
        return ci_session;
    }

    public void setCi_session(String ci_session) {
        this.ci_session = ci_session;
    }

    public boolean isGroup_moderator() {
        return group_moderator;
    }

    public void setGroup_moderator(boolean group_moderator) {
        this.group_moderator = group_moderator;
    }

    public String getAdmin_link() {
        return admin_link;
    }

    public void setAdmin_link(String admin_link) {
        this.admin_link = admin_link;
    }
}
