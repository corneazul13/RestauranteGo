package godomicilios.mdc.restaurantego.settings;

import java.util.ArrayList;

/**
 * Created by PROGRAMACION5 on 10/07/2017.
 */

public class addition {
    private String name;
    private Integer cant;
    private Integer unitPrice;
    private Integer totalPrice;
    public ArrayList <addition> additions;

    public addition(String name, int cant, int unitPrice, Integer totalPrice) {
        this.name = name;
        this.cant = cant;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
    public addition(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCant() {
        return cant;
    }

    public void setCant(Integer cant) {
        this.cant = cant;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
