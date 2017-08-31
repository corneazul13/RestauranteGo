package godomicilios.mdc.restaurantego.Events;

/**
 * Creado por Deimer el 26/08/17.
 */

public class NotificationsBus {

    private boolean refresh;
    private int order_id;
    private int notification_id;

    public NotificationsBus(boolean refresh, int order_id, int notification_id) {
        this.refresh = refresh;
        this.order_id = order_id;
        this.notification_id = notification_id;
    }

    public boolean isRefresh() {
        return refresh;
    }
    public int getOrder_id() {
        return order_id;
    }
    public int getNotification_id() {
        return notification_id;
    }
}
