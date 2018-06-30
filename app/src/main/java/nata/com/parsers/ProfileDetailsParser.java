package nata.com.parsers;

import android.content.Context;



import org.json.JSONArray;
import org.json.JSONObject;

import nata.com.models.LocationModel;
import nata.com.models.Model;
import nata.com.models.PersonalDetailsModel;

/**
 * Created by Shankar on 10/31/2016.
 */

public class ProfileDetailsParser implements Parser<Model> {

    @Override
    public Model parseResponse(String response, Context context) {
        PersonalDetailsModel mPersonalDetailsModel = new PersonalDetailsModel();
        try {
            JSONArray mJSONArray = new JSONArray(response);
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject jsonObject = mJSONArray.optJSONObject(i);
                mPersonalDetailsModel.setName(jsonObject.optString("name"));
                mPersonalDetailsModel.setId(jsonObject.optString("id"));
                mPersonalDetailsModel.setUuid(jsonObject.optString("uuid"));
                mPersonalDetailsModel.setEmail(jsonObject.optString("email"));
                mPersonalDetailsModel.setPassword(jsonObject.optString("password"));
                mPersonalDetailsModel.setPhoto(jsonObject.optString("photo"));
                mPersonalDetailsModel.setActive(jsonObject.optString("active"));
                mPersonalDetailsModel.setUser_type(jsonObject.optString("user_type"));
                mPersonalDetailsModel.setQuestion_id(jsonObject.optString("question_id"));
                mPersonalDetailsModel.setQuestion_response(jsonObject.optString("question_response"));
                mPersonalDetailsModel.setAsk_for_help(jsonObject.optString("ask_for_help"));
                mPersonalDetailsModel.setCreated(jsonObject.optString("created"));
                mPersonalDetailsModel.setLastupdated(jsonObject.optString("lastupdated"));
                mPersonalDetailsModel.setFirstname(jsonObject.optString("firstname"));
                mPersonalDetailsModel.setLastname(jsonObject.optString("lastname"));
                mPersonalDetailsModel.setPhone(jsonObject.optString("phone"));
                mPersonalDetailsModel.setCountry(jsonObject.optString("country"));
                mPersonalDetailsModel.setState(jsonObject.optString("state"));
                mPersonalDetailsModel.setDistrict(jsonObject.optString("district"));
                mPersonalDetailsModel.setMandal(jsonObject.optString("mandal"));
                mPersonalDetailsModel.setVillage(jsonObject.optString("village"));
                mPersonalDetailsModel.setZip(jsonObject.optString("zip"));
                mPersonalDetailsModel.setOccupation(jsonObject.optString("occupation"));
                mPersonalDetailsModel.setProfession(jsonObject.optString("profession"));
                mPersonalDetailsModel.setDeleted(jsonObject.optString("deleted"));
                mPersonalDetailsModel.setStatus_id(jsonObject.optString("status_id"));
                mPersonalDetailsModel.setReason(jsonObject.optString("reason"));
                mPersonalDetailsModel.setCreatedby(jsonObject.optString("createdby"));
                mPersonalDetailsModel.setApprovedby(jsonObject.optString("approvedby"));
                mPersonalDetailsModel.setOptout(jsonObject.optString("optout"));
                mPersonalDetailsModel.setFather_name(jsonObject.optString("father_name"));
                mPersonalDetailsModel.setGothram(jsonObject.optString("gothram"));
                mPersonalDetailsModel.setGender(jsonObject.optString("gender"));
                mPersonalDetailsModel.setDob(jsonObject.optString("dob"));
                mPersonalDetailsModel.setBlood_group_id(jsonObject.optString("blood_group_id"));
                mPersonalDetailsModel.setCurrent_address(jsonObject.optString("current_address"));
                mPersonalDetailsModel.setPermanent_address(jsonObject.optString("permanent_address"));
                mPersonalDetailsModel.setId_proof(jsonObject.optString("id_proof"));
                mPersonalDetailsModel.setCompany(jsonObject.optString("company"));
                mPersonalDetailsModel.setBlood_group(jsonObject.optString("blood_group"));
                mPersonalDetailsModel.setName(jsonObject.optString("name"));
                mPersonalDetailsModel.setOccu(jsonObject.optString("occu"));

                if (jsonObject.has("location")) {
                    LocationModel locationModel = new LocationModel();
                    JSONObject locationJsonObject = jsonObject.optJSONObject("location");
                    locationModel.setCountry(locationJsonObject.optString("country"));
                    locationModel.setState(locationJsonObject.optString("state"));
                    locationModel.setDistrict(locationJsonObject.optString("district"));
                    locationModel.setCity(locationJsonObject.optString("city"));
                    locationModel.setMandal(locationJsonObject.optString("mandal"));
                    locationModel.setVillage(locationJsonObject.optString("village"));
                    mPersonalDetailsModel.setLocationModel(locationModel);
                }
            }
            mPersonalDetailsModel.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            mPersonalDetailsModel.setStatus(false);
            mPersonalDetailsModel.setMessage("OOPS..! Some problem with the API");
        }

        return mPersonalDetailsModel;
    }
}
