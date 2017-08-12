package godomicilios.mdc.restaurantego.settings;

import java.util.ArrayList;

/**
 * Created by PROGRAMACION5 on 03/08/2017.
 */

public class status {
    private Integer id;
    private String name;
    public ArrayList<status> statuses;

    public status (Integer id, String name){
        this.setId(id);
        this.setName(name);
    }
    public status(){}

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
}
