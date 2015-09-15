package Helpers;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import Intefaces.CallBackAsync;
import Utilities.Webservice;

/**
 * Created by Ali on 9/15/2015.
 */
public class StatisticsHelper {
    public static void sendStatisticsToServer(Context context){
         try {
             // uniqe id
             String android_id = Settings.Secure.getString(context.getContentResolver(),
                     Settings.Secure.ANDROID_ID);
             String manufacturer = Build.MANUFACTURER;
             String model = Build.MODEL;


             List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
             basicNameValuePairs.add(new BasicNameValuePair("tag", "statistics"));
             basicNameValuePairs.add(new BasicNameValuePair("uid", android_id));
             basicNameValuePairs.add(new BasicNameValuePair("model", model));
             basicNameValuePairs.add(new BasicNameValuePair("manufacturer", manufacturer));

             Webservice.postData(context, basicNameValuePairs);

             Webservice.postDataToAddress(context, basicNameValuePairs, "http://irdevelopers.ir/aseman/st.php", new CallBackAsync() {
                 @Override
                 public void onSuccessFinish(Object result) {

                 }

                 @Override
                 public void onError(String errorMessage) {

                 }
             });
         }catch (Exception e){
             e.printStackTrace();
         }
    }


}
