package godomicilios.mdc.restaurantego.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import godomicilios.mdc.restaurantego.R;

/**
 * Creado por Deimer el 23/08/17.
 */

public class Notifications extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            //String data = remoteMessage.getData().toString();
            //System.out.println("data: " + data);
            showNotification(title, body);
        }
    }

    public JsonObject setupJson(String json_body) {
        JsonParser parser = new JsonParser();
        return parser.parse(json_body).getAsJsonObject();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(String title, String text) {
        JsonObject json = setupJson(text);
        String body = json.get("mensaje").getAsString();
        JsonObject json_data = json.getAsJsonObject("data");
        System.out.println("data: " + json_data.toString());

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setVibrate(new long[] { 1000, 1000 })
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

}
