package Helpers;

import android.content.Context;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import DataModel.Soal;
import Intefaces.CallBackAsync;
import Intefaces.CallBackSoal;
import Utilities.Webservice;

/**
 * Created by Ali on 8/29/2015.
 */
public class SoalLoader {
    public static ArrayList<Soal> soals;


    public static void syncSilent(final Context context) {

        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        basicNameValuePairs.add(new BasicNameValuePair("tag", "question"));
        Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {
            @Override
            public void onBeforStart() {
            }

            @Override
            public void onSuccessFinish(String result) {
                if (result != null && ((String) result).length() > 1)
                    SharedPrefHelper.write(context, "question", (String) result);
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    public static void getSoals(final Context context, final CallBackSoal callBack) {

        if (SharedPrefHelper.contain(context, "question")) {
            //read from shared pref
            try {
                JSONObject json = new JSONObject(SharedPrefHelper.read(context, "question"));
                ArrayList<Soal> soals = Soal.getArrayListFromJson(json.getJSONArray("content"));
                if (soals.size() > 1) {
                    // it's ok we can return it !
                    callBack.onSuccess(soals);
                } else {
                    // we have groups in shared pref but it is empty ! so we try to load it online
                    syncOnline(context, callBack);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // try to load online because shared pref is empty
            syncOnline(context, callBack);
        }
    }

    public static void syncOnline(final Context context, final CallBackSoal callBack) {

        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        basicNameValuePairs.add(new BasicNameValuePair("tag", "question"));
        Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {
            @Override
            public void onBeforStart() {

            }

            @Override
            public void onSuccessFinish(String result) {
                try {
                    if (result != null && ((String) result).length() > 1)
                        SharedPrefHelper.write(context, "question", (String) result);

                    JSONObject json = new JSONObject(result.toString());
                    ArrayList<Soal> soals = Soal.getArrayListFromJson(json.getJSONArray("content"));
                    callBack.onSuccess(soals);

                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onError("error fbdsg");
                }
            }

            @Override
            public void onError(String errorMessage) {
                callBack.onError("error jfgjf");
            }
        });


    }

}
