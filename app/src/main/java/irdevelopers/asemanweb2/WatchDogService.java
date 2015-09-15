package irdevelopers.asemanweb2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Calendar;

public class WatchDogService extends Service {
    public WatchDogService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Calendar cal = Calendar.getInstance();
        Intent myIntent = new Intent(this, CheckForUpdateService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, myIntent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Set Interval for watchdog
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),  1000 * 60 * 60  , pintent);

        return START_STICKY;
    }
}
