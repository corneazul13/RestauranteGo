package godomicilios.mdc.restaurantego.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import godomicilios.mdc.restaurantego.R;

/**
 * Creado por Deimer el 29/08/17.
 */

public class Product {

    @SerializedName("producto_id")
    @Expose
    private Integer id;

    @SerializedName("categoria_id")
    @Expose private Integer category_id;

    @SerializedName("nombre_producto")
    @Expose private String name;

    @SerializedName("descripcion_producto")
    @Expose private String description;

    @SerializedName("valor_producto")
    @Expose private float value;

    @SerializedName("foto_producto")
    @Expose private String avatar;

    @SerializedName("tipo_producto")
    @Expose private String product_type;

    @SerializedName("estado")
    @Expose private Integer state;

    @SerializedName("fecha_venta")
    @Expose private String sale_date;

    public ArrayList<Product> products;

    public Product() {}

    public Product(Integer id, Integer category_id, String name, String description, float value,
                   String avatar, String product_type, Integer state, String sale_date) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.avatar = avatar;
        this.product_type = product_type;
        this.state = state;
        this.sale_date = sale_date;
    }

    public Integer getId() {
        return id;
    }
    public Integer getCategory_id() {
        return category_id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public float getValue() {
        return value;
    }
    public String getAvatar() {
        return avatar;
    }
    public String getProduct_type() {
        return product_type;
    }
    public Integer getState() {
        return state;
    }
    public String getSale_date() {
        return sale_date;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }
    public void setName(String name) {
        this.name = R.string.url_img + name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }
    public void setState(Integer state) {
        this.state = state;
    }
    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", avatar='" + avatar + '\'' +
                ", product_type='" + product_type + '\'' +
                ", state=" + state +
                ", sale_date='" + sale_date + '\'' +
                ", products=" + products +
                '}';
    }
}
