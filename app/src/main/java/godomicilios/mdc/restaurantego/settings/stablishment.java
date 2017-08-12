package godomicilios.mdc.restaurantego.settings;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by PROGRAMACION5 on 07/07/2017.
 */
public class stablishment {
    private Integer id;
    private String name;
    private String idSuc;
    private Integer selection=0;
    private String status;
    private String user;
    private String pss;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdSuc() {
        return idSuc;
    }

    public void setIdSuc(String idSuc) {
        this.idSuc = idSuc;
    }

    public Integer getSelection() {
        return selection;
    }

    public void setSelection(Integer selection) {
        this.selection = selection;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPss() {
        return pss;
    }

    public void setPss(String pss) {
        this.pss = pss;
    }

    public boolean isOnline(Context contexto){
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
