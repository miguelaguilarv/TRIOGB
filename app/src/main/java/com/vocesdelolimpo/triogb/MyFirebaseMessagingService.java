package com.vocesdelolimpo.triogb;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    private static final String TAG="MyFirebaseMessagingService";
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG,"MENSAJE RECIVIDO"+token);
    }
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"MENSAJE RECIVIDO");
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String title= notification.getTitle();
        String msg= notification.getBody();

        sendNotification(title,msg);

    }

    private void sendNotification(String title, String msg) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,MyNotification.NOTIFICATION_ID,intent,PendingIntent.FLAG_ONE_SHOT);

        MyNotification  notification = new MyNotification(this,MyNotification.CHANNEL_ID_NOTIFICATIONS) ;
        notification.build(R.drawable.ic_launcher_foreground, title, msg, pendingIntent);
        notification.addChannel("Notificaciones", NotificationManager.IMPORTANCE_DEFAULT);
        notification.createChannelGroup(MyNotification.CHANNEL_GROUP_GENERAL,R.string.notification_channel_group_general);
        notification.show(MyNotification.NOTIFICATION_ID);



    }
}
