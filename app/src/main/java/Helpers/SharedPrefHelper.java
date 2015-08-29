package Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.apache.http.message.BasicNameValuePair;

import java.util.Calendar;

/**
 * Created by Alip on 8/4/2015.
 */
public class SharedPrefHelper {

    public  static BasicNameValuePair cache;

    public static void write(Context context,String key,String value){
//        SharedPreferences.Editor editor = context.getSharedPreferences("cache", 0).edit();
//        editor.putString(key, value);
//        editor.commit();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String read(Context context,String key){
        if (key== null ) return null;
        if (key.length()<1) return  null;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);

    }

    public static void clear(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().clear().commit();
    }

    public static boolean contain(Context context,String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.contains(key);
    }
}


