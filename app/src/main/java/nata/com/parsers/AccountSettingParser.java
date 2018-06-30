package nata.com.parsers;

import android.content.Context;


import org.json.JSONObject;

import nata.com.models.AccountSettingModel;
import nata.com.models.Model;

/**
 * Created by Shankar on 1/15/2017.
 */
public class AccountSettingParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        AccountSettingModel mAccountSettingModel = new AccountSettingModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);

            if (mJSONObject.has("failed")){
                JSONObject mFailedJSONObject = mJSONObject.optJSONObject("failed");
                mAccountSettingModel.setMessage(mFailedJSONObject.optString("message"));
                mAccountSettingModel.setStatus_msg(mFailedJSONObject.optString("status"));
                mAccountSettingModel.setStatus(false);
            } else {
                mAccountSettingModel.setMessage(mJSONObject.optString("msg"));
                mAccountSettingModel.setStatus_msg(mJSONObject.optString("status"));
                mAccountSettingModel.setStatus(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mAccountSettingModel.setStatus(false);
            mAccountSettingModel.setMessage("OOPS..! Some problem with the API");
        }
        return mAccountSettingModel;
    }
}
