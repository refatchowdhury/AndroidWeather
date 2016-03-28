package com.refat.restweather.app.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by refat on 20/01/2016.
 */
public class SharedPreference {

    public static final String PREFS_NAME = "LOC_PREFS";
    public static final String PREFS_KEY = "LOC_PREFS_String";
    public static final String PREFS_KEY_LAT = "LOC_PREFS_LAT";
    public static final String PREFS_KEY_LON = "LOC_PREFS_LON";
    public static final String PREFS_KEY_CITY = "LOC_PREFS_CITY";

    public SharedPreference() {
        super();
    }

    public void save(Context context, String city, double lat, double lon) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        // editor.putString(PREFS_KEY, text); //3
        editor.putLong(PREFS_KEY_LAT, Double.doubleToRawLongBits(lat));
        editor.putLong(PREFS_KEY_LON, Double.doubleToRawLongBits(lon));
        editor.putString(PREFS_KEY_CITY, city);


        editor.commit(); //4
    }

    public String getCity(Context context) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY_CITY, null);
        return text;
    }

    public double getLat(Context context) {
        SharedPreferences settings;

        double lat;
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        lat =  Double.longBitsToDouble(settings.getLong(PREFS_KEY_LAT, Double.doubleToLongBits(50.11092209999999)));
        Log.i("SharedPreference", "lat:" + lat);
        return lat;

    }

    public double getLon(Context context) {
        SharedPreferences settings;
        double lon;
        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);


        lon =  Double.longBitsToDouble(settings.getLong(PREFS_KEY_LON, Double.doubleToLongBits(8.6821267)));
        Log.i("SharedPreference", "lon:" + lon);
        return lon;

    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(PREFS_KEY);
        editor.commit();
    }
}
