package nata.com.parsers;

import android.content.Context;



import org.json.JSONObject;

import nata.com.models.Model;
import nata.com.models.SuccessModel;

/**
 * Created by Shankar on 1/15/2017.
 */
public class AskForInvitationParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        SuccessModel mSuccessModel = new SuccessModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);

            if (mJSONObject.has("failed")){
                JSONObject mFailedJSONObject = mJSONObject.optJSONObject("failed");
                mSuccessModel.setMessage(mFailedJSONObject.optString("message"));
                mSuccessModel.setStatus_msg(mFailedJSONObject.optString("status"));
                mSuccessModel.setStatus(false);
            } else {
                mSuccessModel.setMessage(mJSONObject.optString("msg"));
                mSuccessModel.setStatus_msg(mJSONObject.optString("status"));
                mSuccessModel.setStatus(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mSuccessModel.setStatus(false);
            mSuccessModel.setMessage("OOPS..! Some problem with the API");
        }
        return mSuccessModel;
    }
}
