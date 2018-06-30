package nata.com.parsers;

import android.content.Context;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.MembersListModel;
import nata.com.models.MembersModel;
import nata.com.models.Model;

/**
 * Created by Shankar on 10/31/2016.
 */

public class MembersForumParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        MembersListModel mMembersListModel = new MembersListModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);
            JSONArray mMembersJSONArray = mJSONObject.optJSONArray("members");
            ArrayList<MembersModel> mMembersModels = new ArrayList<>();
            for (int i = 0; i < mMembersJSONArray.length(); i++) {
                JSONObject jsonObject = mMembersJSONArray.optJSONObject(i);
                MembersModel mMembersModelItem = new MembersModel();
                mMembersModelItem.setUuid(jsonObject.optString("uuid"));
                mMembersModelItem.setPhoto(jsonObject.optString("photo"));
                mMembersModelItem.setFirstname(jsonObject.optString("firstname"));
                mMembersModelItem.setLastname(jsonObject.optString("lastname"));
                mMembersModelItem.setOccu(jsonObject.optString("occu"));
                mMembersModelItem.setCompany(jsonObject.optString("company"));
                mMembersModels.add(mMembersModelItem);
            }
            mMembersListModel.setMembersModels(mMembersModels);
            mMembersListModel.setTotal(mJSONObject.optString("total"));
            mMembersListModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mMembersListModel.setStatus(false);
            mMembersListModel.setMessage("OOPS..! Some problem with the API");
        }

        return mMembersListModel;
    }
}
