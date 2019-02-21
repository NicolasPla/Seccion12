package com.example.seccion12;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class NotificationHandler extends ContextWrapper {

    private NotificationManager manager;

    public static final String CHANNEL_HIGH_ID = "1";
    private final String CHANNEL_HIGH_NAME = "HIGH CHANNEL";
    public static final String CHANNEL_LOW_ID = "2";
    private final String CHANNEL_LOW_NAME = "LOW CHANNEL";

    public NotificationHandler(Context context) {
        super(context);
        createChannels();
    }

    public  NotificationManager getManager(){
        if (manager == null)
        {
            manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return manager;
    }

    private void createChannels(){
        // >=OREO
        if (Build.VERSION.SDK_INT >= 26){
        // Creating High Channel
            NotificationChannel highChannel = new NotificationChannel(
                    CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH);
            // EXTRA CONFIG
                highChannel.enableLights(true);
                highChannel.setLightColor(Color.YELLOW);

                highChannel.setShowBadge(true);
                highChannel.enableVibration(true);
                highChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
                highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                highChannel.setSound(defaultSoundUri, null);
        // Creating Low Channel
            NotificationChannel lowChannel = new NotificationChannel(
                    CHANNEL_LOW_ID, CHANNEL_LOW_NAME, NotificationManager.IMPORTANCE_LOW);

            getManager().createNotificationChannel(highChannel);
            getManager().createNotificationChannel(lowChannel);

        }
    }
        // ESTE METODO SE LLAMA DE AFUERA PARA CREAR NOTIFICACIONES

    public Notification.Builder createNotification(String title, String message, boolean isHighImportance){
        // ESTE IF PODRIA ESTAR EN UNA CLASE UTILS
        if (Build.VERSION.SDK_INT >= 26){
            if (isHighImportance){
                return this.createNotificationWithChannel(title, message, CHANNEL_HIGH_ID);
            }
            return this.createNotificationWithChannel(title, message, CHANNEL_LOW_ID);
        }
        return this.createNotificationWithoutChannel(title, message);
    }

    private Notification.Builder createNotificationWithChannel(String title, String message, String channelId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("message", message);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Request code > Variable fija para identificar notificacion
            // PendingIntent Intent preparado para el click en la notificacion

            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


            return  new Notification.Builder(getApplicationContext(), channelId)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(getColor(R.color.colorPrimary))
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
        }
        return null;
    }

    private Notification.Builder createNotificationWithoutChannel(String title, String Message){

            return  new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(Message)
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setAutoCancel(true);
    }
}
