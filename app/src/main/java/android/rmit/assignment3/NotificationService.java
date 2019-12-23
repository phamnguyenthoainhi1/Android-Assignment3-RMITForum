package android.rmit.assignment3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {
    private Utilities utilities = new Utilities();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String type = remoteMessage.getData().get("type");
        if(type!=null) {
            switch (type) {
                case "reply":
                    Intent intent = new Intent(this,PostDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("id",remoteMessage.getData().get("postId"));

                    notify(remoteMessage,intent);

                    break;
                case "comment":
                    break;
            }
        }
    }

    @Override
    public void onNewToken(String newToken) {
        super.onNewToken(newToken);

        utilities.updateToken(newToken);
    }

    private void notify(RemoteMessage remoteMessage,Intent intent){
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags=Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(123,notification);
    }
}
