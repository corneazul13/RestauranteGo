package godomicilios.mdc.restaurantego.settings;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Creado por Deimer el 10/07/2017.
 */

public class Additions {

    @SerializedName("id_adicion")
    private int additon_id;

    @SerializedName("nombre_adicion")
    private String name;

    @SerializedName("cantidad")
    private int quantity;

    @SerializedName("valor")
    private float value;

    @SerializedName("imagen_adicion")
    private String avatar;

    public ArrayList <addition> additions;

    public Additions(){}

    public Additions(int additon_id, String name, int quantity, float value, String avatar) {
        this.additon_id = additon_id;
        this.name = name;
        this.quantity = quantity;
        this.value = value;
        this.avatar = avatar;
    }

//region Getters
    public int getAdditon_id() {
        return additon_id;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public float getValue() {
        return value;
    }
    public String getAvatar() {
        return avatar;
    }
//endregion

//region Setters
    public void setAdditon_id(int additon_id) {
        this.additon_id = additon_id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
//endregion

    @Override
    public String toString() {
        return "Additions{" +
                "additon_id=" + additon_id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", value=" + value +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}

