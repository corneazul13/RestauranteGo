package godomicilios.mdc.restaurantego.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creado por Deimer el 2/09/17.
 */

public class Drink {

    @SerializedName("producto_id")
    private int drink_id;

    @SerializedName("empresa_id")
    private int business_id;

    @SerializedName("tipo_bebida")
    private int drink_type;

    @SerializedName("nombre_bebida")
    private String drink_name;

    @SerializedName("tamano_bebida")
    private int drink_size;

    @SerializedName("valor")
    private float value;

    @SerializedName("imagen")
    private String avatar;

    @SerializedName("estado")
    private int state;

    @SerializedName("tamano")
    private String size;

    public Drink() {}

    public Drink(int drink_id, int business_id, int drink_type, String drink_name,
                 int drink_size, float value, String avatar, int state, String size) {
        this.drink_id = drink_id;
        this.business_id = business_id;
        this.drink_type = drink_type;
        this.drink_name = drink_name;
        this.drink_size = drink_size;
        this.value = value;
        this.avatar = avatar;
        this.state = state;
        this.size = size;
    }

//region Getters
    public int getDrink_id() {
        return drink_id;
    }
    public int getBusiness_id() {
        return business_id;
    }
    public int getDrink_type() {
        return drink_type;
    }
    public String getDrink_name() {
        return drink_name;
    }
    public int getDrink_size() {
        return drink_size;
    }
    public float getValue() {
        return value;
    }
    public String getAvatar() {
        return avatar;
    }
    public int getState() {
        return state;
    }
    public String getSize() {
        return size;
    }
//endregion

//region Setters
    public void setDrink_id(int drink_id) {
        this.drink_id = drink_id;
    }
    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }
    public void setDrink_type(int drink_type) {
        this.drink_type = drink_type;
    }
    public void setDrink_name(String drink_name) {
        this.drink_name = drink_name;
    }
    public void setDrink_size(int drink_size) {
        this.drink_size = drink_size;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public void setState(int state) {
        this.state = state;
    }
    public void setSize(String size) {
        this.size = size;
    }
//endregion

    @Override
    public String toString() {
        return "Drink{" +
                "drink_id=" + drink_id +
                ", business_id=" + business_id +
                ", drink_type=" + drink_type +
                ", drink_name='" + drink_name + '\'' +
                ", drink_size=" + drink_size +
                ", value=" + value +
                ", avatar='" + avatar + '\'' +
                ", state=" + state +
                ", size='" + size + '\'' +
                '}';
    }
}
