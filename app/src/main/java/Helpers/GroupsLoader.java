package Helpers;

import android.content.Context;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import DataModel.Group;
import Intefaces.CallBackAsync;
import Intefaces.CallBackGroup;
import Utilities.Webservice;

/**
 * Created by Ali on 8/29/2015.
 */
public class GroupsLoader {
    public static ArrayList<Group> groups ;





    public static void syncSilent(final Context context){

        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        basicNameValuePairs.add(new BasicNameValuePair("tag", "groups"));
        Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {


            @Override
            public void onSuccessFinish(String result) {
                if (result != null && result.length() > 1)
                    SharedPrefHelper.write(context, "groups", result);
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    public static void getGroups(final Context context, final CallBackGroup callBack) {

        if(SharedPrefHelper.contain(context,"groups")){
            //read from shared pref
            try {
                JSONArray jsonArray = new JSONArray(SharedPrefHelper.read(context, "groups"));
                groups = Group.getArrayListFromJson(jsonArray);
                if (groups.size()>1){
                    // it's ok we can return it !
                    callBack.onSuccess(groups);
                }else{
                    // we have groups in shared pref but it is empty ! so we try to load it online
                    syncOnline(context,callBack);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            // try to load online because shared pref is empty
            syncOnline(context,callBack);
        }
    }

    public static void syncOnline(final Context context, final CallBackGroup callBack) {

        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        basicNameValuePairs.add(new BasicNameValuePair("tag", "groups"));
        Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {


            @Override
            public void onSuccessFinish(String result) {
                try {
                    SharedPrefHelper.write(context, "groups", result);
                    JSONArray jsonArray = new JSONArray(result);
                    callBack.onSuccess(Group.getArrayListFromJson(jsonArray));

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

































