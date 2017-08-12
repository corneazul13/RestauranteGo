package godomicilios.mdc.restaurantego.settings;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PROGRAMACION5 on 10/07/2017.
 */

public class order {
    private Integer id;
    private Integer idSec;
    private Integer status;
    private Integer method;
    private Integer sale;
    private Integer price;
    private String user;
    private String date;
    private String address;
    public ArrayList<order> orders;
    public order (Integer id, Integer idSec,Integer status, Integer method, Integer sale, Integer price,
                  String user, String date, String address){

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
    public order(){}

    public Integer getIdOrder() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIdSec() {
        return idSec;
    }

    public void setIdSec(Integer idSec) {
        this.idSec = idSec;
    }
}
