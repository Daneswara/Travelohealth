package com.masbie.travelohealth;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.masbie.travelohealth.dao.internal.queue.ServiceDao;
import com.masbie.travelohealth.db.DBOpenHelper;
import com.masbie.travelohealth.pojo.service.RoomQueueNotificationPojo;
import com.masbie.travelohealth.pojo.service.RoomQueueProcessedPojo;
import com.masbie.travelohealth.pojo.service.ServiceQueueNotificationPojo;
import com.masbie.travelohealth.pojo.service.ServiceQueueProcessedPojo;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import timber.log.Timber;

/**
 * Created by Daneswara on 18/10/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    static int notifId = -1;
    private DBOpenHelper db;

    @Override public void onCreate()
    {
        this.db = new DBOpenHelper(this);
        super.onCreate();
    }

    @Override public void onDestroy()
    {
        this.db.close();
        super.onDestroy();
    }

    @Override public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Timber.d("onMessageReceived");

        super.onMessageReceived(remoteMessage);

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Timber.d("From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if(remoteMessage.getData().size() > 0)
        {
            Timber.d("Message data payload [%d]: %s", remoteMessage.getData().size(), remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            String              type = data.get("type");
            if(type != null)
            {
                switch(type)
                {
                    case "room":
                    {
                        RoomQueueProcessedPojo summary = RoomQueueProcessedPojo.fromJson(data.get("result"));
                        Timber.d(summary.toString());

                        //Storage.getInstance(this).manageRoomQueue(Collections.singletonList(summary));
                        switch(summary.getProcessed())
                        {
                            case 2:
                            {
                                this.sendNotification("Pemesanan Kamar", "Pemesanan Anda Berhasil");
                            }
                            break;
                            case 3:
                            {
                                this.sendNotification("Pemesanan Kamar", "Pemesanan Anda Gagal");
                            }
                            break;
                        }
                    }
                }
            }
        }

        // Check if message contains a notification payload.
        if(remoteMessage.getNotification() != null)
        {
            String topic = remoteMessage.getFrom().replace("/topics/", "");
            try
            {
                String topicSpec = topic.split("-")[0];
                switch(topicSpec)
                {
                    case "service":
                    {
                        Timber.d("Message Notification Body Service: " + remoteMessage.getNotification().getBody());
                        Gson                         gson         = ServiceQueueNotificationPojo.inferenceGsonBuilder(new GsonBuilder()).create();
                        ServiceQueueNotificationPojo notification = gson.fromJson(remoteMessage.getNotification().getBody(), ServiceQueueNotificationPojo.class);
                        this.processServiceQueue(notification);
                    }
                    break;
                    case "room":
                    {
                        Timber.d("Message Notification Body Room: " + remoteMessage.getNotification().getBody());
                        Gson                      gson         = RoomQueueNotificationPojo.inferenceGsonBuilder(new GsonBuilder()).create();
                        RoomQueueNotificationPojo notification = gson.fromJson(remoteMessage.getNotification().getBody(), RoomQueueNotificationPojo.class);
                        this.processRoomQueue(notification);
                    }
                    break;
                }
            }
            catch(ArrayIndexOutOfBoundsException ignored)
            {
                Timber.e(ignored);
            }

            Timber.d(remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void processServiceQueue(ServiceQueueNotificationPojo notification)
    {
        Timber.d("processServiceQueue");

        //Collection<ServiceQueuePojo> queues = Storage.getInstance(this).retrieveServiceQueue(ServiceQueuePojo.inferenceGsonBuilder(new GsonBuilder()).create()).values();

        @Nullable List<ServiceQueueProcessedPojo> queues = ServiceDao.findWhereServiceIDAndOrder(this.db, notification.getService(), notification.getOrder());
        for(ServiceQueueProcessedPojo queue : queues)
        {
            if(queue.getQueue().intValue() == notification.getProcessed().intValue())
            {
                this.sendNotification(String.format(Locale.getDefault(), "Antrian %s", queue.getService().getName()), String.format(Locale.getDefault(), "Antrian anda pada %s telah dipanggil", queue.getService().getName()));
            }
            else if(queue.getQueue() > notification.getProcessed())
            {
                this.sendNotification(String.format(Locale.getDefault(), "Antrian %s", queue.getService().getName()), String.format(Locale.getDefault(), "Antrian anda pada %s kurang %d lagi", queue.getService().getName(), queue.getQueue().intValue() - notification.getProcessed().intValue()));
            }
            else
            {
                continue;
            }
            queue.setQueueProcessed(notification.getProcessed());
            ServiceDao.insertOrUpdate(db, queue);
        }
    }

    private void processRoomQueue(RoomQueueNotificationPojo notification)
    {
        Timber.d("processServiceQueue");

        /*Collection<RoomQueueProcessedPojo> queues = Storage.getInstance(this).retrieveRoomQueue(RoomQueueProcessedPojo.inferenceGsonBuilder(new GsonBuilder()).create()).values();
        for(RoomQueueProcessedPojo queue : queues)
        {
            if((queue.getOrder().isEqual(notification.getOrder())))
            {
                if(queue.getQueue().intValue() == notification.getProcessed().intValue())
                {
                    this.sendNotification("Pemesanan Kamar", "Pemesanan Anda Sedang Diproses");
                    break;
                }
                else if(queue.getQueue() > notification.getProcessed())
                {
                    this.sendNotification("Pemesanan Kamar", String.format(Locale.getDefault(), "Pemesanan anda akan diproses %d antrian lagi", queue.getQueue() - notification.getProcessed()));
                    break;
                }
            }
        }*/
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String messageBody)
    {
        Intent intent = new Intent(this, App.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(++MyFirebaseMessagingService.notifId, notificationBuilder.build());
    }
}
