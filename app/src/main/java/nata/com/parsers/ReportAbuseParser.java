package nata.com.parsers;

import android.content.Context;



import org.json.JSONObject;

import nata.com.models.Model;
import nata.com.models.ReportAbuseModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class ReportAbuseParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        ReportAbuseModel mReportAbuseModel = new ReportAbuseModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);
            if (mJSONObject.has("status")) {
                if (mJSONObject.getString("status").equalsIgnoreCase("error")) {
                    mReportAbuseModel.setStatus(false);
                    mReportAbuseModel.setMessage(mJSONObject.optString("message"));
                } else {
                    mReportAbuseModel.setMessage(mJSONObject.optString("msg"));
                    mReportAbuseModel.setStatus(true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            mReportAbuseModel.setStatus(false);
            mReportAbuseModel.setMessage("OOPS..! Some problem with the API");
        }

        return mReportAbuseModel;
    }
}
