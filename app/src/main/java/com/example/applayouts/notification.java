package com.example.applayouts;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import android.app.NotificationManager;

public class notification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String channelID = "drinkNotification";
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    channelID,
                    "drinkNoti",
                    NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }

        Notification.Builder noti = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            noti = new Notification.Builder(context,channelID);
        }
        noti.setContentTitle("Drink!");
        noti.setContentText("Remember to keep drinking water!");
        noti.setSmallIcon(R.drawable.ic_launcher_foreground);
        notificationManager.notify(121212, noti.build());

    }
}