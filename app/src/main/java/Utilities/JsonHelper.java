package Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ashkan on 8/18/2014.
 */
public class JsonHelper {

    public boolean error = false;

    public Object getObject(JSONObject json, String index, Object replacement) {

        error = false;

        if (json.isNull(index)) {
            error = true;
            return replacement;
        }

        try {

            return json.get(index);

        } catch (JSONException e) {
            //e.printStackTrace();
            error = true;
        }

        return replacement;
    }

    public String getString(JSONObject json, String index, String replacement) {

        error = false;

        if (json.isNull(index)) {
            error = true;
            return replacement;
        }

        try {

            return json.getString(index);

        } catch (JSONException e) {
            //e.printStackTrace();
            error = true;
        }

        return replacement;
    }

    public int getInt(JSONObject json, String index, int replacement) {

        error = false;

        if (json.isNull(index)) {
            error = true;
            return replacement;
        }

        try {

            return json.getInt(index);

        } catch (JSONException e) {
            //e.printStackTrace();
            error = true;
        }

        return replacement;
    }

    public boolean getBoolean(JSONObject json, String index, boolean replacement) {

        error = false;

        if (json.isNull(index)) {
            error = true;
            return replacement;
        }

        try {

            return json.getBoolean(index);

        } catch (JSONException e) {
            //e.printStackTrace();
            error = true;
        }

        return replacement;
    }

    public double getDouble(JSONObject json, String index, double replacement) {

        error = false;

        if (json.isNull(index)) {
            error = true;
            return replacement;
        }

        try {

            return json.getDouble(index);

        } catch (JSONException e) {
            //e.printStackTrace();
            error = true;
        }

        return replacement;
    }

    public JSONArray getJsonArray(JSONObject json, String index, JSONArray replacement) {

        error = false;

        if (json.isNull(index)) {
            error = true;
            return replacement;
        }

        try {

            return json.getJSONArray(index);

        } catch (JSONException e) {
            //e.printStackTrace();
            error = true;
        }

        return replacement;
    }

    public long getLong(JSONObject json, String index, long replacement) {

        error = false;

        if (json.isNull(index)) {
            error = true;
            return replacement;
        }

        try {

            return json.getLong(index);

        } catch (JSONException e) {
            //e.printStackTrace();
            error = true;
        }

        return replacement;
    }

    public JSONObject getJsonObject(JSONObject json, String index, JSONObject replacement) {

        error = false;

        if (json.isNull(index)) {
            error = true;
            return replacement;
        }

        try {

            return json.getJSONObject(index);

        } catch (JSONException e) {
            //e.printStackTrace();
            error = true;
        }

        return replacement;
    }


    public static Object getObjectS(JSONObject json, String index, Object replacement) {

        if (json.isNull(index))
            return replacement;

        try {

            return json.get(index);

        } catch (JSONException e) {
            //e.printStackTrace();
        }

        return replacement;
    }

    public static String getStringS(JSONObject json, String index, String replacement) {

        if (json.isNull(index))
            return replacement;

        try {

            return json.getString(index);

        } catch (JSONException e) {
            //e.printStackTrace();
        }

        return replacement;
    }

    public static int getIntS(JSONObject json, String index, int replacement) {

        if (json.isNull(index))
            return replacement;

        try {

            return json.getInt(index);

        } catch (JSONException e) {
            //e.printStackTrace();
        }

        return replacement;
    }

    public static boolean getBooleanS(JSONObject json, String index, boolean replacement) {

        if (json.isNull(index))
            return replacement;

        try {

            return json.getBoolean(index);

        } catch (JSONException e) {
            //e.printStackTrace();
        }

        return replacement;
    }

    public static double getDoubleS(JSONObject json, String index, double replacement) {

        if (json.isNull(index))
            return replacement;

        try {

            return json.getDouble(index);

        } catch (JSONException e) {
            //e.printStackTrace();
        }

        return replacement;
    }

    public static JSONArray getJsonArrayS(JSONObject json, String index, JSONArray replacement) {

        if (json.isNull(index))
            return replacement;

        try {

            return json.getJSONArray(index);

        } catch (JSONException e) {
            //e.printStackTrace();
        }

        return replacement;
    }

    public static long getLongS(JSONObject json, String index, long replacement) {

        if (json.isNull(index))
            return replacement;

        try {

            return json.getLong(index);

        } catch (JSONException e) {
            //e.printStackTrace();
        }

        return replacement;
    }

    public static JSONObject getJsonObjectS(JSONObject json, String index, JSONObject replacement) {

        if (json.isNull(index))
            return replacement;

        try {

            return json.getJSONObject(index);

        } catch (JSONException e) {
            //e.printStackTrace();
        }

        return replacement;
    }

}

