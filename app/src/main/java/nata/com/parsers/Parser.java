package nata.com.parsers;


import android.content.Context;

import nata.com.models.Model;


/**
 * Created by Shankar Rao on 10/20/2016.
 */
public interface Parser<T extends Model> {

    T parseResponse(String response, Context context);

    //public ArrayList<Model> parse(String resp) throws JSONException;
}