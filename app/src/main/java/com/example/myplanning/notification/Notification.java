package com.example.myplanning.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myplanning.R;
import com.example.myplanning.activitats.MainActivity;

import java.util.Calendar;

public class Notification extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private Context contest;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 1;
    private Button bNotification;



    private void createNotificacion(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(contest, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("Nova Tasca");
        builder.setContentText("Nova Tasca");
        builder.setColor(Color.BLUE);
        builder.setLights(Color.RED, 1000,1000);
        builder.setVibrate(new long[] {1000, 1000});
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(contest);
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

    }

    private void setPendingIntent(){
        Intent fullScreenIntent = new Intent(this, MainActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Incoming call")
                        .setContentText("(919) 555-1234")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)

                        // Use a full-screen intent only for the highest-priority alerts where you
                        // have an associated activity that you would like to launch after the user
                        // interacts with the notification. Also, if your app targets Android 10
                        // or higher, you need to request the USE_FULL_SCREEN_INTENT permission in
                        // order for the platform to invoke this notification.
                        .setFullScreenIntent(fullScreenPendingIntent, true);

        android.app.Notification incomingCallNotification = notificationBuilder.build();
    }
}
