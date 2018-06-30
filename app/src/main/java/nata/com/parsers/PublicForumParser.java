package nata.com.parsers;

import android.content.Context;



import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.ForumListModel;
import nata.com.models.Model;
import nata.com.models.PublicForumModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class PublicForumParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        ForumListModel mForumListModel = new ForumListModel();
        try {
            JSONObject mJSONObject = new JSONObject(response);
            JSONArray mTopicsJSONArray = mJSONObject.optJSONArray("topics");
            ArrayList<PublicForumModel> mPublicForumModels = new ArrayList<>();
            for (int i = 0; i < mTopicsJSONArray.length(); i++) {
                JSONObject jsonObject = mTopicsJSONArray.optJSONObject(i);
                PublicForumModel mPublicForumModelItem = new PublicForumModel();
                mPublicForumModelItem.setId(jsonObject.optString("id"));
                mPublicForumModelItem.setRecordeddate(jsonObject.optString("recordeddate"));
                mPublicForumModelItem.setUsername(jsonObject.optString("username"));
                mPublicForumModelItem.setName(jsonObject.optString("name"));
                mPublicForumModelItem.setReplies(jsonObject.optString("replies"));
                mPublicForumModels.add(mPublicForumModelItem);
            }
            mForumListModel.setPublicForumModels(mPublicForumModels);
            mForumListModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mForumListModel.setStatus(false);
            mForumListModel.setMessage("OOPS..! Some problem with the API");
        }

        return mForumListModel;
    }
}
