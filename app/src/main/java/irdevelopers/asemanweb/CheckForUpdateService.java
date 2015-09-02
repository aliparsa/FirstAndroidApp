package irdevelopers.asemanweb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import DataModel.News;
import Helpers.AppUpdaterHelper;
import Helpers.CustomNotificationHelper;
import Helpers.GroupsLoader;
import Helpers.NewsLoader;
import Helpers.Ram;
import Helpers.SharedPrefHelper;
import Helpers.SoalLoader;
import Intefaces.CallBackAsync;
import Intefaces.CallBackYes;
import Utilities.Webservice;
import irdevelopers.asemanweb.MainActivity;
import irdevelopers.asemanweb.NewsGroupPickerActivity;
import irdevelopers.asemanweb.R;

public class CheckForUpdateService extends Service {
    public CheckForUpdateService() {
    }

    public static void silentSync() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        checkForAppUpdate();
        //checkForNewsUpdate();
        CustomNotificationHelper.checkForCustomNotification(getApplicationContext());
        GroupsLoader.syncSilent(getApplicationContext());
        SoalLoader.syncSilent(getApplicationContext());
        NewsLoader.syncSilent(getApplicationContext());

        return START_NOT_STICKY;
    }

    private void checkForNewsUpdate() {
        SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean a = myPreference.getBoolean("app_update_checkbox", true);
        if (!myPreference.getBoolean("app_update_checkbox", true)) return;



        try {
            List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
            basicNameValuePairs.add(new BasicNameValuePair("tag", "lastUpdate"));
            basicNameValuePairs.add(new BasicNameValuePair("uid", SharedPrefHelper.read(getApplicationContext(), "lastNewsUid")));
            //basicNameValuePairs.add(new BasicNameValuePair("uid", "25"));
            Webservice.postData(getApplicationContext(), basicNameValuePairs, new CallBackAsync<String>() {


                @Override
                public void onSuccessFinish(String result) {

                    if (!result.equals("0")) {
                        ArrayList<News> newses = new ArrayList<News>();
                        try {
                            JSONObject json = new JSONObject(result);
                            newses.addAll(News.getArrayListFromJson(json.getJSONArray("content")));

                            for(News news : newses){
                                int newsUid = Integer.parseInt(news.uid);
                                int lastUid = Integer.parseInt(SharedPrefHelper.read(getApplicationContext(),"lastNewsUid"));
                                if (newsUid>lastUid)
                                    SharedPrefHelper.write(getApplicationContext(),"lastNewsUid",newsUid+"");
                            }

                            saveUapdatedGroups(newses);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        showNewsUpdateNotification(newses);
                    }
                    stopSelf();
                }

                @Override
                public void onError(String errorMessage) {
                    stopSelf();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveUapdatedGroups(ArrayList<News> newses) {
        ArrayList<String> updatedGroups = new ArrayList<String>();
        for (News news : newses) {
            if (!news.groupCode.contains(",")) {
                // coma nadarad
                if (!updatedGroups.contains(news.groupCode))
                    updatedGroups.add(news.groupCode);
            } else {
                // comma darad
                String[] arr = news.groupCode.split(",");
                for (int i = 0; i < arr.length; i++) {
                    if (!updatedGroups.contains(arr[i]))
                        updatedGroups.add(arr[i]);
                }
            }
        }
        Ram.updatedGroups = updatedGroups;
    }

    private void showNewsUpdateNotification(ArrayList<News> newses) {
        String contents="";

        if (newses.size()<5){
            for(News news : newses){
            contents+=news.title+"\n";
        }
        }else {
            contents += newses.get(newses.size()-1).title + "\n";
            contents += newses.get(newses.size()-2).title + "\n";
            contents += newses.get(newses.size()-3).title + "\n";
            contents += newses.get(newses.size()-4).title + "\n";
            contents += newses.get(newses.size()-5).title + "\n";
        }

//        for(News news : newses){
//            contents+=news.title+"\n";
//        }
        try {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.small_notification_icon)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.tazeha))
                            .setContentTitle("خبر های جدیدی داریم")
                            .setSound(uri)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(contents))
                            .setAutoCancel(true)
                            .setContentText(contents);


// Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, NewsGroupPickerActivity.class);
            resultIntent.putExtra("online", true);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(NewsGroupPickerActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
            mNotificationManager.notify(24685, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkForAppUpdate() {
        AppUpdaterHelper.isNewVersionExist(getApplicationContext(), new CallBackYes() {
            @Override
            public void onYesResult(String result) {

                if (SharedPrefHelper.contain(getApplicationContext(), "savedVesrion")) {
                    int savedVesrion = Integer.parseInt(SharedPrefHelper.read(getApplicationContext(), "savedVesrion"));
                    int onlineVersion = Integer.parseInt(result);
                    if (savedVesrion != onlineVersion) {
                        showAppUpdateNotification();
                        SharedPrefHelper.write(getApplicationContext(), "savedVesrion", result);

                    }
                } else {
                    // this is first app update notification
                    showAppUpdateNotification();
                    SharedPrefHelper.write(getApplicationContext(), "savedVesrion", result);


                }

            }
        });

//        SharedPreferences myPreference = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean a = myPreference.getBoolean("app_update_checkbox", true);
//
//        if (!myPreference.getBoolean("app_update_checkbox", true)) return;
//
//        try {
//            final Context context = getApplicationContext();
//            List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
//            basicNameValuePairs.add(new BasicNameValuePair("tag", "update"));
//
//            Webservice.postData(context, basicNameValuePairs, new CallBackAsync<String>() {
//                @Override
//                public void onBeforStart() {
//
//                }
//
//                @Override
//                public void onSuccessFinish(String result) {
//                    //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(result);
//                        int serverVersion = jsonObject.getInt("version");
//                        final String url = jsonObject.getString("url");
//
//                        int versionCode = context.getPackageManager()
//                                .getPackageInfo(context.getPackageName(), 0).versionCode;
//
//                        if (serverVersion > versionCode) {
//                            // show update dialog
//                            showAppUpdateNotification();
//
//                        }
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void showAppUpdateNotification() {
        try {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.small_notification_icon)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.tazeha))
                            .setContentTitle("نسخه جدید آسمان وب آماده دانلود است")
                            .setSound(uri)
                            .setAutoCancel(true)
                            .setContentText("جهت دریافت این نسخه تپ کنید");
            Intent resultIntent = new Intent(this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
            mNotificationManager.notify(24686, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





