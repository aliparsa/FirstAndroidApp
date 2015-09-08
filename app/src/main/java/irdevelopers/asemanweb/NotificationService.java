package irdevelopers.asemanweb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;

import DataModel.News;
import Helpers.DatabaseHelper;
import irdevelopers.asemanweb.NewsGroupPickerActivity;
import irdevelopers.asemanweb.R;

public class NotificationService extends Service {
    public NotificationService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        showNewsUpdateNotification(new DatabaseHelper(getApplicationContext()).getAllUnNotifiedNews());

        return START_NOT_STICKY;
    }

    private void showNewsUpdateNotification(ArrayList<News> newses) {

        if (newses == null || newses.size() == 0) return;

        new DatabaseHelper(getApplicationContext()).makeNewsNotified(newses);
        String contents = "";


        if (newses.size() < 5) {
            for (News news : newses) {
                contents += news.title + "\n";
            }
        } else {
            contents += newses.get(newses.size() - 1).title + "\n";
            contents += newses.get(newses.size() - 2).title + "\n";
            contents += newses.get(newses.size() - 3).title + "\n";
            contents += newses.get(newses.size() - 4).title + "\n";
            contents += newses.get(newses.size() - 5).title + "\n";
        }


        try {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.small_notification_icon)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.tazeha))
                            .setContentTitle("خبر های جدید")
                            .setSound(uri)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(contents))
                            .setAutoCancel(true)
                            .setContentText(contents);


// Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, AllNewsActivity.class);
          //  resultIntent.putExtra("online", true);

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


}





