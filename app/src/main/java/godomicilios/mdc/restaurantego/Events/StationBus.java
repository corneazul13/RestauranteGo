package godomicilios.mdc.restaurantego.Events;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Creado por Deimer el 26/08/17.
 */

public class StationBus {

    private static Bus bus = new Bus(ThreadEnforcer.ANY);
    public static Bus getBus() {
        return bus;
    }

}
