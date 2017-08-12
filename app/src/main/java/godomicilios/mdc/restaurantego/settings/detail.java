package godomicilios.mdc.restaurantego.settings;

import java.util.ArrayList;

/**
 * Created by PROGRAMACION5 on 10/07/2017.
 */

public class detail {

    private String name;
    private Integer cant;
    private Integer num;
    private String picture;
    private Integer subtotalProduct;
    private String drink = "";
    private String observation = "";
    private ArrayList<ingredient> ingredients;
    private ArrayList<addition> additions;
    public ArrayList<detail> details;

    public detail(String name,Integer cant,String picture,Integer subtotalProduct,
                  String drink,String observation,ArrayList<ingredient> ingredients,ArrayList<addition> additions){
        this.name = name;
        this.cant = cant;
        this.picture = picture;
        this.subtotalProduct = subtotalProduct;
        this.drink = drink;
        this.observation = observation;
        this.ingredients = ingredients;
        this.additions = additions;
    }
    public detail (){}

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

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public ArrayList<ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<addition> getAdditions() {
        return additions;
    }

    public void setAdditions(ArrayList<addition> additions) {
        this.additions = additions;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getSubtotalProduct() {
        return subtotalProduct;
    }

    public void setSubtotalProduct(Integer subtotalProduct) {
        this.subtotalProduct = subtotalProduct;
    }
}
