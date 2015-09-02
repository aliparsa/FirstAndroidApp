package Helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Intefaces.CallBackAsync;
import Utilities.Webservice;
import irdevelopers.asemanweb.MainActivity;
import irdevelopers.asemanweb.R;

/**
 * Created by Alip on 8/16/2015.
 */
public class CustomNotificationHelper {

    static int id;
    static String title;
    static String content;

    public static void checkForCustomNotification(final Context context) {

        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
        basicNameValuePairs.add(new BasicNameValuePair("tag", "customNotification"));
        Webservice.postDataToAddress(context, basicNameValuePairs, "http://irdevelopers.ir/sky/func.php", new CallBackAsync<String>() {


            @Override
            public void onSuccessFinish(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                     id = jsonObject.getInt("id");
                     title = jsonObject.getString("title");
                     content = jsonObject.getString("content");

                    if (id==1) return;

                    if (SharedPrefHelper.contain(context, "customNotificationId")) {
                        // darad    moghayese va namayesh
                        if ( Integer.parseInt(SharedPrefHelper.read(context,"customNotificationId"))<id ) {
                            SharedPrefHelper.write(context, "customNotificationId", id + "");
                            showCustomNotification(context);
                        }
                    } else {
                        // nadarad save va namayesh
                         SharedPrefHelper.write(context,"customNotificationId",id+"");
                        showCustomNotification(context);
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }


    public static void showCustomNotification(Context context) {
        try {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.small_notification_icon)
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.tazeha))
                            .setContentTitle(title)
                            .setSound(uri)
                            .setAutoCancel(true)
                            .setContentText(content);
            Intent resultIntent = new Intent(context, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE);
            mNotificationManager.notify(24687, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
