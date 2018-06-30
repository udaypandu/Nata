package nata.com.utility;

/**
 * Created by shankar on 10/20/2016.
 */

public class APIConstants {

    public static final String GSON = "Gson";
    public static final String JSON = "Json";
    public static final String STATUS = "status";

    public enum REQUEST_TYPE {
        GET, POST, MULTIPART_GET, MULTIPART_POST, DELETE, PUT, PATCH
    }

   // public static final String HOME_URL = "http://api-jana.uftausa.org/api/";
    public static final String HOME_URL = "http://api-nata.uftausa.org/api/";
    //public static final String HOME_URL = "http://testapi.kapuwelfare.com/api/";
    public static final String BASE_URL = "http://webservices.kapuwelfare.com/XRef/";
    public static final String COUNTRIES_URL = HOME_URL + "getCountries";
    public static final String REGISTER_URL = HOME_URL + "register";

    public static final String GET_PROFESSIONS = HOME_URL + "getProfessions";
    public static final String LOGIN = HOME_URL + "login";
    public static final String STATES_URL = HOME_URL + "getStates/"; //http://webservices.kapuwelfare.com/XRef/States/{CountryID}
    public static final String CITIES_URL = HOME_URL + "getCities/"; //http://webservices.kapuwelfare.com/XRef/Cities/{StateID}
    public static final String DISTRICTS_URL = HOME_URL + "getDistricts/"; //http://webservices.kapuwelfare.com/XRef/Districts/{StateID}
    public static final String MANDALS_URL = HOME_URL + "getMandals/"; //http://webservices.kapuwelfare.com/XRef/Mandals/{DistrictID}
    public static final String VILLAGES_URL = HOME_URL + "getvillages/"; //http://webservices.kapuwelfare.com/XRef/Villages/{MandalID}
    public static final String PROFESSION_URL = BASE_URL + "Profession"; //http://webservices.kapuwelfare.com/XRef/Profession
    public static final String REGISTRATION_URL = BASE_URL + "Registration/Invitation"; //http://webservices.kapuwelfare.com/Registration/Invitation
    public static final String NEWS_URL = HOME_URL + "SiteContent/News"; //http://webservices.kapuwelfare.com/SiteContent/News
    public static final String EVENTS_URL = HOME_URL + "SiteContent/Events"; //http://webservices.kapuwelfare.com/SiteContent/Events
    public static final String GET_ID_PROOFS = HOME_URL + "/getIDProofs"; //http://webservices.kapuwelfare.com/SiteContent/Events
    public static final String GET_BLOOD_GROUPS = HOME_URL + "/getBloodGroups";
    public static final String ASK_INVITE = HOME_URL + "ask_invite"; //http://api.kapuwelfare.com/api/ask_invite

    public static final String CHANGE_PASSWORD = HOME_URL + "changePassword";
    public static final String GET_PROFILE = HOME_URL + "getProfile";

    public static final String FORUM_ALL = "http://api-nata.uftausa.org/forum/all/";//http://api.kapuwelfare.com/forum/all/
    public static final String PUBLIC_TOPICS_FORUM = "http://api-nata.uftausa.org/forum/public_topics/";// http://api.kapuwelfare.com/forum/public_topics/2/{page_no}/{page_size}?api_key=5usBabpKQgqdW9Cx
    public static final String PRIVATE_TOPICS_FORUM = "http://api-nata.uftausa.org/forum/private_topics/";//http://api.kapuwelfare.com/forum/public_topics/2?api_key=5usBabpKQgqdW9Cx
    public static final String GET_MODERATORS_FORUM = "http://api-nata.uftausa.org/forum/get_moderators/";//http://api.kapuwelfare.com/forum/public_topics/2?api_key=5usBabpKQgqdW9Cx
    public static final String TOPIC_FORUM = "http://api-nata.uftausa.org/forum/topic/";//http://api.kapuwelfare.com/forum/topic/25?api_key=5usBabpKQgqdW9Cx
    public static final String POST_TOPIC_FORUM = "http://api-nata.uftausa.org/forum/post_topic/";//http://api.kapuwelfare.com/forum/post_topic/{category_id}
    public static final String GET_MEMBERS_FORUM = "http://api-nata.uftausa.org/forum/members/";//http://api.kapuwelfare/forum/members/2/{page_no}/{page_size}?api_key=5usBabpKQgqdW9Cx
    public static final String IS_MEMBER_FORUM = "http://api-nata.uftausa.org/forum/is_member/";//http://api.kapuwelfare/forum/is_member/2?api_key=5usBabpKQgqdW9Cx
    public static final String POST_REPLY_FORUM = "http://api-nata.uftausa.org/forum/post_reply/";//http://api.kapuwelfare.com/forum/post_reply/{topic_id}
    public static final String DELETE_TOPIC_FORUM = "http://api-nata.uftausa.org/forum/delete_topic/";//http://api.kapuwelfare.com/forum/delete_topic/{topic_id}

    public static final String ASK_MY_HELP_LIST = "http://api-nata.uftausa.org/forum/ask_my_help/";//http://api.kapuwelfare.com/forum/ask_my_help/{page_no}/{page_size}
    public static final String ASK_MY_HELP_MODERATE_GROUPS = "http://api-nata.uftausa.org/forum/moderate_groups";//http://api.kapuwelfare.com/forum/moderate_groups?api_key=5usBabpKQgqdW9Cx
    public static final String ASK_MY_HELP_GET_HELP_DETAIL = "http://api-nata.uftausa.org/forum/get_help/";//http://api.kapuwelfare.com/forum/get_help/{help_id}
    public static final String ASK_MY_HELP_REPLY = "http://api-nata.uftausa.org/forum/help_reply/";// http://api.kapuwelfare.com/forum/help_reply/{topic_id}
    public static final String ASK_MY_HELP_POST = "http://api-nata.uftausa.org/forum/ask_help/";// http://api.kapuwelfare.com/forum/ask_help/{category_id}
    public static final String ASK_MY_HELP_DELETE = "http://api-nata.uftausa.org/forum/delete_help/";// http://api.kapuwelfare.com/forum/delete_help/{help_id}
    public static final String ASK_HELPS_LIST_FOR_MODERATERS = "http://api-nata.uftausa.org/forum/ask_helps/";// http://api.kapuwelfare.com/forum/ask_helps/{cat_id}
    public static final String ASK_HELPS_LIST_FOR_MODERATERS_POST = "http://api-nata.uftausa.org/forum/ask_help/";// http://api.kapuwelfare.com/forum/ask_help/{category_id}
    public static final String ASK_HELPS_LIST_FOR_MODERATERS_POST_DETAIL = "http://api-nata.uftausa.org/forum/get_help/";// http://api.kapuwelfare.com/forum/get_help/{help_id}
    public static final String ASK_HELPS_LIST_FOR_HELP_REPLY = "http://api-nata.uftausa.org/forum/help_reply/";// http://api.kapuwelfare.com/forum/help_reply/{topic_id}
    public static final String ASK_HELPS_LIST_FOR_HELP_DELETE = "http://api-nata.uftausa.org/forum/delete_help/";// http://api.kapuwelfare.com/forum/delete_help/{help_id}
    public static final String ASK_HELPS_REPORT_ABUSE = "http://api-nata.uftausa.org/forum/report_abuse/";// http://api.kapuwelfare.com/forum/report_abuse/{topic_id}/{reply_id}?api_key=5usBabpKQgqdW9Cx
    public static final String ASK_HELPS_DELETE_REPLY = "http://api-nata.uftausa.org/forum/delete_reply/";// http://api.kapuwelfare.com/forum/delete_reply/{topic id}/{reply_id}?api_key=5usBabpKQgqdW9Cx

    public static final String JOBS_HOME_URL = "http://api.kapuwelfare.com/jobs";
    public static final String NEWS_HOME_URL = "http://api-nata.uftausa.org/contents";
    public static final String APPLY_JOBS_URL = "http://api.kapuwelfare.com/jobs/apply/";
    public static final String JOB_CATEGORY_URL = "http://api.kapuwelfare.com/jobs/categories/";
    public static final String POST_JOB_URL = "http://api.kapuwelfare.com/jobs/post_job";
    public static final String JOB_GROUPS_URL = "http://api.kapuwelfare.com/jobs/groups/";
    public static final String MY_POSTS_DELETE_URL = "http://api.kapuwelfare.com/jobs/delete/";
    public static final String MY_POSTS_EDIT_URL = "http://api.kapuwelfare.com/jobs/edit/";
    public static final String MY_APPLIED_JOBS_URL = "http://api.kapuwelfare.com/jobs/my_jobs";
    public static final String MY_POSTED_JOBS_URL = "http://api.kapuwelfare.com/jobs/my_postedjobs";
    public static final String MY_APPLIED_JOBS_UPDATE_JOB_STATUS = "http://api.kapuwelfare.com/jobs/update_status/";
    public static final String FIND_ALL_JOBS = "http://api.kapuwelfare.com/jobs?api_key=5usBabpKQgqdW9Cx"; //http://api.kapuwelfare.com/api/jobs

    public static final String UPDATE_DEVICE = "http://api-nata.uftausa.org/api/updateDevice"; //http://api.kapuwelfare.com/api/updateDevice
    public static final String FORGOT_PASSWORD = "http://api-nata.uftausa.org/api/forgotPassword"; //http://api.kapuwelfare.com/api/forgotPassword
    public static final String UPDATE_PROFILE = "http://api-nata.uftausa.org/api/updateProfile"; //http://api.kapuwelfare.com/api/updateProfile

    public static final String GET_MEMBERS = "http://api-nata.uftausa.org/api/get_members/country/1"; //http://api.kapuwelfare.com/api/get_members/country/1
    public static final String GET_OUR_FORCE_STATE = "http://api-nata.uftausa.org/api/get_members/state/"; //http://api.kapuwelfare.com/api/get_members/country/1
    public static final String GET_OUR_FORCE_DISTRICT = "http://api-nata.uftausa.org/api/get_members/district/"; //http://api.kapuwelfare.com/api/get_members/country/1
    public static final String GET_OUR_FORCE_MANDAL = "http://api-nata.uftausa.org/api/get_members/mandal/"; //http://api.kapuwelfare.com/api/get_members/country/1
    public static final String GET_OUR_FORCE_VILLAGE = "http://api-nata.uftausa.org/api/get_members/village/"; //http://api.kapuwelfare.com/api/get_members/country/1


}
