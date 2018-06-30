package nata.com.parsers;

import android.content.Context;


import org.json.JSONObject;

import nata.com.models.Model;
import nata.com.models.ReportDeleteModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class ReportDeleteParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        ReportDeleteModel mReportDeleteModel = new ReportDeleteModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);
            if (mJSONObject.has("status")) {
                if (mJSONObject.getString("status").equalsIgnoreCase("error")) {
                    mReportDeleteModel.setStatus(false);
                    mReportDeleteModel.setMessage(mJSONObject.optString("message"));
                } else {
                    mReportDeleteModel.setMessage(mJSONObject.optString("msg"));
                    mReportDeleteModel.setStatus(true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            mReportDeleteModel.setStatus(false);
            mReportDeleteModel.setMessage("OOPS..! Some problem with the API");
        }

        return mReportDeleteModel;
    }
}
