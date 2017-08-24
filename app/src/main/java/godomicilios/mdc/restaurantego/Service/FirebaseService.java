package godomicilios.mdc.restaurantego.Service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Creado por Deimer el 23/08/17.
 */

public class FirebaseService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("Token actualizado: " + refreshedToken);
    }

}
