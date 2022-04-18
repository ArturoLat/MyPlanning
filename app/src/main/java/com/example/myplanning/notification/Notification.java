package com.example.myplanning.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myplanning.R;

public class Notification {

    private PendingIntent pendingIntent;
    private Context contest;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    private void createNotificacion(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(contest, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("Nova Tasca");
        builder.setContentText("Nova Tasca");
        builder.setColor(Color.BLUE);
        builder.setLights(Color.RED, 1000,1000);
        builder.setVibrate(new long[] {1000, 1000});

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(contest);
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());

    }

}
