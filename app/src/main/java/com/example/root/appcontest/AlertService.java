package com.example.root.appcontest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class AlertService extends Service {

    private NotificationManager notify_m;
    ServiceThread thread;
    private Notification notifi;

    public AlertService()
    {
        ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Thread bgMessage = new Thread(new BGmessage());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notify_m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        BGmessage bghandler = new BGmessage();
        thread = new ServiceThread(bghandler);
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        thread.stopForever();
        thread = null;
        super.onDestroy();
    }

    private class BGmessage extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(AlertService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(AlertService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            /*
            NotificationChannel notificationChannel = new NotificationChannel("sibal", "sibal", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableVibration(true);
            notify_m.createNotificationChannel(notificationChannel);

            notifi = new Notification.Builder(getApplicationContext(),"sibal")
                    .setContentTitle("title")
                    .setContentText("text")
                    .setSmallIcon(R.drawable.ic_angle)
                    .setTicker("test")
                    .setContentIntent(pendingIntent)
                    .build();

            notifi.defaults = Notification.DEFAULT_VIBRATE;
            */

            //notify_m.notify(0, notifi);
            //Toast.makeText(AlertService.this, "되라고 씨발", Toast.LENGTH_SHORT).show();

        }
    }
}
