package nata.com.parsers;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import nata.com.models.Model;
import nata.com.models.NewsModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class NewsesParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        NewsModel mNewsModel = new NewsModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            ArrayList<NewsModel> newsModels = new ArrayList<>();
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                NewsModel mNewsModelItem = new NewsModel();
                mNewsModelItem.setId(jsonObject.optString("id"));
                mNewsModelItem.setSite_content_type(jsonObject.optString("site_content_type"));
                mNewsModelItem.setTitle(jsonObject.optString("title"));
                mNewsModelItem.setEvent_date(jsonObject.optString("event_date"));
                mNewsModelItem.setDescription(jsonObject.optString("description"));
                mNewsModelItem.setImage_name(jsonObject.optString("image_name"));
                JSONArray jsonArray = jsonObject.optJSONArray("other_images");
                ArrayList<String> otherImages = new ArrayList<>();
                if (jsonArray != null)
                    for (int j = 0; j < jsonArray.length(); j++) {
                        otherImages.add(jsonArray.get(j).toString());
                    }
                mNewsModelItem.setOther_images(otherImages);
                mNewsModelItem.setAddress(jsonObject.optString("address"));
                mNewsModelItem.setCreated(jsonObject.optString("created"));
                mNewsModelItem.setLastupdated(jsonObject.optString("lastupdated"));
                mNewsModelItem.setActive(jsonObject.optString("active"));
                mNewsModelItem.setStatus(jsonObject.optString("status"));
                mNewsModelItem.setCreatedBy(jsonObject.optString("createdBy"));
                newsModels.add(mNewsModelItem);
            }
            mNewsModel.setNewsModels(newsModels);
            mNewsModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mNewsModel.setStatus(false);
            mNewsModel.setMessage("OOPS..! Some problem with the API");
        }
        return mNewsModel;
    }
}
