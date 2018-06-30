package nata.com.parsers;

import android.content.Context;


import org.json.JSONObject;

import nata.com.models.DeletePostModel;
import nata.com.models.Model;

/**
 * Created by Shankar on 10/31/2016.
 */

public class DeleteParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        DeletePostModel mDeletePostModel = new DeletePostModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);
            if (mJSONObject.has("status")) {
                mDeletePostModel.setMessage(mJSONObject.optString("msg"));
            }
            mDeletePostModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mDeletePostModel.setStatus(false);
            mDeletePostModel.setMessage("OOPS..! Some problem with the API");
        }

        return mDeletePostModel;
    }
}
