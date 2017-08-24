package godomicilios.mdc.restaurantego.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by PROGRAMACION5 on 10/07/2017.
 *
 */

public class order {

    @SerializedName("id_pedido")
    @Expose private Integer id;

    @SerializedName("sucursal_id")
    @Expose private Integer idSec;

    @SerializedName("estado_pedido")
    @Expose private Integer status;

    @SerializedName("metodo_pago_id")
    @Expose private Integer method;

    @SerializedName("venta_total_pedido")
    @Expose private Integer sale;

    @SerializedName("costo_domicilio")
    @Expose private Integer price;

    @SerializedName("usuario")
    @Expose private String user;

    @SerializedName("fecha_pedido")
    @Expose private String date;

    @SerializedName("direccion_domicilio")
    @Expose private String address;

    public ArrayList<order> orders;

    public order(){}

    public order(Integer id, Integer idSec,Integer status, Integer method, Integer sale,
                 Integer price, String user, String date, String address){
        this.setId(id);
        this.setIdSec(idSec);
        this.setStatus(status);
        this.setMethod(method);
        this.setSale(sale);
        this.setPrice(price);
        this.setUser(user);
        this.setDate(date);
        this.setAddress(address);
    }

    public Integer getId() {
        return id;
    }
    public Integer getIdSec() {
        return idSec;
    }
    public Integer getStatus() {
        return status;
    }
    public Integer getMethod() {
        return method;
    }
    public Integer getSale() {
        return sale;
    }
    public Integer getPrice() {
        return price;
    }
    public String getUser() {
        return user;
    }
    public String getDate() {
        return date;
    }
    public String getAddress() {
        return address;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setIdSec(Integer idSec) {
        this.idSec = idSec;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public void setMethod(Integer method) {
        this.method = method;
    }
    public void setSale(Integer sale) {
        this.sale = sale;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "order{" +
                "id=" + id +
                ", idSec=" + idSec +
                ", status=" + status +
                ", method=" + method +
                ", sale=" + sale +
                ", price=" + price +
                ", user='" + user + '\'' +
                ", date='" + date + '\'' +
                ", address='" + address + '\'' +
                ", orders=" + orders +
                '}';
    }

}
