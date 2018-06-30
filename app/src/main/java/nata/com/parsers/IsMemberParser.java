package nata.com.parsers;

import android.content.Context;


import org.json.JSONObject;

import nata.com.models.IsMemberModel;
import nata.com.models.Model;

/**
 * Created by Shankar on 10/31/2016.
 */

public class IsMemberParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        IsMemberModel mIsMemberModel = new IsMemberModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);
            mIsMemberModel.setMember(mJSONObject.optBoolean("member"));
            mIsMemberModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mIsMemberModel.setStatus(false);
            mIsMemberModel.setMessage("OOPS..! Some problem with the API");
        }

        return mIsMemberModel;
    }
}
