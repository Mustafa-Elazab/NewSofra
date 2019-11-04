package com.example.mustafa.sofraNew.data.local.SharedPreferences;


import android.app.Activity;
import android.content.SharedPreferences;

public class SharedPreferencesManger {

    private static SharedPreferences sharedPreferences = null;
    public static String USER_TYPE = "USER_TYPE";
    public static String RESTAURANT = "RESTAURANT";
    public static String CLIENT = "CLIENT";
    public static String USER_API_TOKEN = "USER_API_TOKEN";
    public static String RESTURANT_API_TOKEN = "RESTURANT_API_TOKEN";
    public static String USER_PASSWORD = "USER_PASSWORD";
    public static String RESTURANT_PASSWORD = "RESTURANT_PASSWORD";
    public static String USER_ID = "USER_ID";
    public static int RESTURANT_ID = 0;
    private static String USER_NAME = "USER_NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String RESTURANT_NAME = "RESTURANT_NAME";
    public static String RESTURANT_EMAIL = "RESTURANT_EMAIL";
    private static String USER_BID = "USER_BID";
    private static String USER_PHONE = "USER_PHONE";
    private static String USER_DLD = "USER_DLD";
    public static String RESTURANT_REGION_ID = "RESTURANT_REGION_ID";
    public static String RESTURANT_PHOTO_URL = "RESTURANT_PHOTO_URL";
    private static String USER_CITY_NAME = "USER_CITY_NAME";
    private static String USER_GOVERMENT_ID = "USER_GOVERMENT_ID";
    private static String USER_GOVERMENT_NAME = "USER_GOVERMENT_NAME";
    private static String USER_BLOOD_TYPE_NAME = "USER_BLOOD_TYPE_NAME";
    public static String REMEBER = "REMEBER";
    public static String PIN_CODE_RESTURANT = "PIN_CODE_RESTURANT";
    public static String PIN_CODE_USER = "PIN_CODE_USER";



    public static void setSharedPreferences(Activity activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(
                    "Blood", activity.MODE_PRIVATE);

        }
    }

    public static void SaveData(Activity activity, String data_Key, String data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void SaveData(Activity activity, String data_Key, int data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static void SaveData(Activity activity, String data_Key, boolean data_Value) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static String LoadData(Activity activity, String data_Key) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getString(data_Key, null);
    }

    public static boolean LoadBoolean(Activity activity, String data_Key) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getBoolean(data_Key, false);
    }

    public static void clean(Activity activity) {
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
        }
    }

}
