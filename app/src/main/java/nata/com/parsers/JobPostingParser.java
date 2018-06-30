package nata.com.parsers;

import android.content.Context;



import org.json.JSONObject;

import nata.com.models.JobPostingModel;
import nata.com.models.Model;

/**
 * Created by Shankar on 10/31/2016.
 */

public class JobPostingParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        JobPostingModel mJobPostingModel = new JobPostingModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);
            if (mJSONObject.has("failed")) {
                JSONObject mJSONObjectFailed = mJSONObject.optJSONObject("failed");
                mJobPostingModel.setMessage(mJSONObjectFailed.optString("message"));
                mJobPostingModel.setStatus(true);
            } else if (mJSONObject.has("status")) {
                mJobPostingModel.setMessage(mJSONObject.optString("msg"));
                mJobPostingModel.setStatus(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mJobPostingModel.setStatus(false);
            mJobPostingModel.setMessage("OOPS..! Some problem with the API");
        }

        return mJobPostingModel;
    }
}
