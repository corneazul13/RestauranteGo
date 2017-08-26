package godomicilios.mdc.restaurantego.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Creado por Deimer el 18/08/17.
 */

public interface Api {

    @GET("/servicios_restaurante.php")
    void historial(
            @Query("svice") String svice,
            @Query("metodo") String metodo,
            @Query("sucId") int sucursal_id,
            Callback<JsonArray> cb
    );

    @GET("/servicios_restaurante.php")
    void orderDetail(
            @Query("svice") String svice,
            @Query("metodo") String metodo,
            @Query("pedido_id") int sucursal_id,
            Callback<JsonArray> cb
    );

    @GET("/servicios_restaurante.php")
    void login(
            @Query("svice") String svice,
            @Query("metodo") String metodo,
            @Query("usr")String username,
            @Query("pwd")String password,
            Callback<JsonObject> cb
    );

}
