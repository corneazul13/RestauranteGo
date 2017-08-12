package godomicilios.mdc.restaurantego.settings;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by PROGRAMACION5 on 07/07/2017.
 */

public class fonts {

    private String tfl = "font/Montserrat-Bold.otf";
    private String tfb = "font/Montserrat-Light.otf";
    private String tfr = "font/Montserrat-Regular.otf";

    public Typeface typefaceL (Context context){
        Typeface t = Typeface.createFromAsset(context.getAssets(), tfl);
        return t;
    }

    public Typeface typefaceB (Context context){
        Typeface t = Typeface.createFromAsset(context.getAssets(), tfb);
        return t;
    }

    public Typeface typefaceR (Context context){
        Typeface t = Typeface.createFromAsset(context.getAssets(), tfr);
        return t;
    }

}
