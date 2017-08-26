package godomicilios.mdc.restaurantego.Events;

/**
 * Creado por Deimer el 26/08/17.
 */

public class NotificationsBus {

    private boolean refresh;
    private int type;
    private int notification_id;

    public NotificationsBus(boolean refresh, int type, int notification_id) {
        this.refresh = refresh;
        this.type = type;
        this.notification_id = notification_id;
    }

    public boolean isRefresh() {
        return refresh;
    }
    public int getType() {
        return type;
    }
    public int getNotification_id() {
        return notification_id;
    }
}
