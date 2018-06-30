package nata.com.parsers;

import android.content.Context;



import org.json.JSONObject;

import nata.com.models.LoginModel;
import nata.com.models.Model;

/**
 * Created by Shankar on 10/31/2016.
 */

public class LoginParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        LoginModel mLoginModel = new LoginModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);
            mLoginModel.setMessage(mJSONObject.optString("msg"));
            mLoginModel.setUuid(mJSONObject.optString("uuid"));
            mLoginModel.setEmail(mJSONObject.optString("email"));
            mLoginModel.setRole(mJSONObject.optString("role"));
            mLoginModel.setRole_name(mJSONObject.optString("role_name"));
            mLoginModel.setFirst_name(mJSONObject.optString("first_name"));
            mLoginModel.setCi_session(mJSONObject.optString("ci_session"));
            mLoginModel.setLast_name(mJSONObject.optString("last_name"));
            mLoginModel.setGroup_moderator(mJSONObject.optBoolean("group_moderator"));
            mLoginModel.setAdmin_link(mJSONObject.optString("admin_link"));
            mLoginModel.setStatus_msg(mJSONObject.optString("status"));
            mLoginModel.setPhoto(mJSONObject.optString("photo"));
            mLoginModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mLoginModel.setStatus(false);
            mLoginModel.setMessage("OOPS..! Some problem with the API");
        }

        return mLoginModel;
    }
}
