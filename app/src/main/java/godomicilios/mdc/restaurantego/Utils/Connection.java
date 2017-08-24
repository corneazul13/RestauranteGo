package godomicilios.mdc.restaurantego.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Creado por Deimer el 22/08/17.
 */

public class Connection {

    //Variable para alerta asincronica
    private Context contexto;

    public Connection(Context contexto){
        this.contexto = contexto;
    }

    public void setContext(Context contexto){
        this.contexto = contexto;
    }

    public boolean isOnline(){
        boolean online = false;
        ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                if(activeNetwork.isConnectedOrConnecting()) {
                    Log.i("Utils()isOnline", "Connected to wifi.");
                    online = true;
                }
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                if(activeNetwork.isConnectedOrConnecting()) {
                    Log.i("Utils()isOnline", "Connected to the mobile provider's data plan.");
                    online = true;
                }
            }
        } else {
            online = false;
        }
        return online;
    }

}
