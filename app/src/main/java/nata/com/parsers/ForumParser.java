package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.ForumModel;
import nata.com.models.Model;
import nata.com.models.SpinnerModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class ForumParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        ForumModel mForumModel = new ForumModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<ForumModel> mForumModels = new ArrayList<>();
            ArrayList<SpinnerModel> forumsSpinnerModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                ForumModel mForumModelItem = new ForumModel();
                mForumModelItem.setId(jsonObject.optString("id"));
                mForumModelItem.setImage(jsonObject.optString("image"));
                mForumModelItem.setName(jsonObject.optString("name"));
                mForumModels.add(mForumModelItem);
                forumsSpinnerModels.add(new SpinnerModel(jsonObject.optString("name")));
            }
            mForumModel.setForumModels(mForumModels);
            mForumModel.setForumsSpinnerModels(forumsSpinnerModels);
            mForumModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mForumModel.setStatus(false);
            mForumModel.setMessage("OOPS..! Some problem with the API");
        }

        return mForumModel;
    }
}
