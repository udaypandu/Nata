package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.ForumDetailModel;
import nata.com.models.ForumsReplyModel;
import nata.com.models.Model;

/**
 * Created by Shankar on 10/31/2016.
 */

public class ForumDetailParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        ForumDetailModel mForumDetailModel = new ForumDetailModel();
        try {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject mJSONObject = jsonArray.optJSONObject(0);
            JSONArray mRepliesJSONArray = mJSONObject.optJSONArray("replies");
            mForumDetailModel.setId(mJSONObject.optString("id"));
            mForumDetailModel.setCatId(mJSONObject.optString("catId"));
            mForumDetailModel.setType(mJSONObject.optString("type"));
            mForumDetailModel.setName(mJSONObject.optString("name"));
            mForumDetailModel.setContent(mJSONObject.optString("content"));
            mForumDetailModel.setImage(mJSONObject.optString("image"));
            mForumDetailModel.setUserid(mJSONObject.optString("userid"));
            mForumDetailModel.setRecordeddate(mJSONObject.optString("recordeddate"));
            mForumDetailModel.setIsPrivate(mJSONObject.optString("isPrivate"));
            mForumDetailModel.setUsername(mJSONObject.optString("username"));
            ArrayList<ForumsReplyModel> mForumsReplyModels = new ArrayList<>();
            for (int i = 0; i < mRepliesJSONArray.length(); i++) {
                JSONObject jsonObject = mRepliesJSONArray.optJSONObject(i);
                ForumsReplyModel mForumsReplyModelItem = new ForumsReplyModel();
                mForumsReplyModelItem.setId(jsonObject.optString("id"));
                mForumsReplyModelItem.setTopicid(jsonObject.optString("topicid"));
                mForumsReplyModelItem.setContent(jsonObject.optString("content"));
                mForumsReplyModelItem.setImage(jsonObject.optString("image"));
                mForumsReplyModelItem.setUserid(jsonObject.optString("userid"));
                mForumsReplyModelItem.setRecordeddate(jsonObject.optString("recordeddate"));
                mForumsReplyModelItem.setIsAbuse(jsonObject.optString("isAbuse"));
                mForumsReplyModelItem.setUsername(jsonObject.optString("username"));
                mForumsReplyModels.add(mForumsReplyModelItem);
            }
            mForumDetailModel.setForumsReplyModels(mForumsReplyModels);
            mForumDetailModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mForumDetailModel.setStatus(false);
            mForumDetailModel.setMessage("OOPS..! Some problem with the API");
        }

        return mForumDetailModel;
    }
}
