package com.swami.vidyanand.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.swami.vidyanand.R;
import com.swami.vidyanand.activities.IndexActivity;
import com.swami.vidyanand.data.Constants;
import com.swami.vidyanand.utils.CustomTagHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GcmIntentService extends IntentService {
    public GcmIntentService() {
        super("GcmIntentService");
    }

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public static final String TAG = "GCM";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());
                String message = intent.getStringExtra(Constants.SUVICHAR);
                sendNotification(message);
                //showToast(extras.getString("message"));
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Log.i(TAG, "Inside SendNotification");
        try {
            Intent intent = new Intent(this, IndexActivity.class);
            intent.putExtra(Constants.MESSAGE, msg);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            String[] lines = msg.split(System.getProperty("line.separator"));
            NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(
                    this)
                    .setSmallIcon(R.drawable.store_icon)
                    .setContentTitle("सुविचार")
                    .setAutoCancel(true)
                    .setContentText(
                            Html.fromHtml(lines[0], null, new CustomTagHandler()));


            Uri alarmSound = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(alarmSound);

            mBuilder.setVibrate(null);
            mBuilder.setContentIntent(contentIntent);

            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }

    }
}
