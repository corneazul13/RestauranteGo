package godomicilios.mdc.restaurantego.Events;

import com.squareup.otto.Bus;

/**
 * Creado por Deimer el 26/08/17.
 */

public class StationBus {

    private static Bus bus = new Bus();
    public static Bus getBus() {
        return bus;
    }

}
