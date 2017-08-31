package godomicilios.mdc.restaurantego.Service;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import godomicilios.mdc.restaurantego.Events.NotificationsBus;
import godomicilios.mdc.restaurantego.Events.StationBus;
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
            JsonObject json_data = null;
            if(remoteMessage.getData() != null) {
                JsonParser jsonParser = new JsonParser();
                json_data = (JsonObject)jsonParser.parse(remoteMessage.getData().toString());
                System.out.println("data: " + json_data.toString());
            }
            showNotification(title, body, json_data);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(String title, String body, JsonObject json_data) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setVibrate(new long[] { 1000, 1000 })
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());

        if(json_data != null) {
            String type = json_data.get("tipoNotificacion").getAsString();
            if(type.equalsIgnoreCase("pedidoNuevo")) {
                int order_id = json_data.get("Idpedido").getAsInt();
                StationBus.getBus().post(new NotificationsBus(true, order_id, 1));
            }
        }
    }

}
