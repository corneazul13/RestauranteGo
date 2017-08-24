package godomicilios.mdc.restaurantego;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import godomicilios.mdc.restaurantego.settings.CustomSSLSocketFactory;
import godomicilios.mdc.restaurantego.settings.settings;
import godomicilios.mdc.restaurantego.settings.status;

public class detailC extends AppCompatActivity {

    TextView a,b,c,d, subtotal,price,total,textView, textView3;
    EditText editText3;
    LinearLayout observ, show,content;
    ImageView imageView2;
    Button confirm, cancel;
    EditText comentary;
    String url = settings.url.url+ "RESPUESTA_PEDIDO&metodo=json&pedido_id=";
    DecimalFormat formatea = new DecimalFormat("###,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //textView = (TextView) findViewById(R.id.textView);
        //comentary = (EditText) findViewById(R.id.comentary);
        //textView.setTypeface(settings.fonts.typefaceR(this));
        //confirm = (Button) findViewById(R.id.confirm);
        //a = (TextView) findViewById(R.id.a);
        //a.setTypeface(settings.fonts.typefaceR(this));
        //b = (TextView) findViewById(R.id.b);
        //b.setTypeface(settings.fonts.typefaceR(this));
        //c = (TextView) findViewById(R.id.c);
        //c.setTypeface(settings.fonts.typefaceR(this));
        //d = (TextView) findViewById(R.id.d);
        //cancel = (Button) findViewById(R.id.cancel);
        //content = (LinearLayout) findViewById(R.id.content);
        //d.setTypeface(settings.fonts.typefaceR(this));
        //observ = (LinearLayout) findViewById(R.id.observ);
        //subtotal = (TextView) findViewById(R.id.subtotal);
        //subtotal.setTypeface(settings.fonts.typefaceR(this));
        //price = (TextView) findViewById(R.id.price);
        //price.setTypeface(settings.fonts.typefaceR(this));
        //total = (TextView) findViewById(R.id.total);
        //total.setTypeface(settings.fonts.typefaceR(this));
        //show  = (LinearLayout) findViewById(R.id.show);
        //textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setTypeface(settings.fonts.typefaceR(this));
        comentary.setTypeface(settings.fonts.typefaceR(this));
        Integer numTotal = settings.order.orders.get(settings.detail.getNum()).getSale()+settings.order.orders.get(settings.detail.getNum()).getPrice();
        subtotal.setText("$"+ formatea.format(settings.order.orders.get(settings.detail.getNum()).getSale()));
        price.setText("$"+formatea.format(settings.order.orders.get(settings.detail.getNum()).getPrice()));
        total.setText("$"+formatea.format(numTotal));
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    onBackPressed();

            }
        });

        if(settings.stablishment.getSelection()==1){
            observ.setVisibility(View.GONE);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    answer(url+settings.order.orders.get(settings.detail.getNum()).getId() +"&estado=2"+"&comentario="+comentary.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comentary.getText().toString().equals("")){
                    ocultar();
                    mostrar();
                    comentary.setHintTextColor(getResources().getColor(R.color.cancel));
                    comentary.setHint("Agregar comentario!");
                }else{
                    try {
                        answer(url+settings.order.orders.get(settings.detail.getNum()).getId()+"&estado=3"+"&comentario="+comentary.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        for (int g =0; g<settings.detail.details.size();g++){
            View child = View.inflate(detailC.this, R.layout.detail, null);
            TextView e = (TextView) child.findViewById(R.id.e);
            e.setTypeface(settings.fonts.typefaceR(this));
            TextView f = (TextView) child.findViewById(R.id.f);
            f.setTypeface(settings.fonts.typefaceR(this));
            TextView gh = (TextView) child.findViewById(R.id.g);
            gh.setTypeface(settings.fonts.typefaceR(this));
            TextView m = (TextView) child.findViewById(R.id.m);
            m.setTypeface(settings.fonts.typefaceR(this));
            TextView n = (TextView) child.findViewById(R.id.n);
            n.setTypeface(settings.fonts.typefaceR(this));
            TextView nameProduct = (TextView) child.findViewById(R.id.nameProduct);
            nameProduct.setTypeface(settings.fonts.typefaceB(this));
            TextView cant = (TextView) child.findViewById(R.id.cant);
            cant.setTypeface(settings.fonts.typefaceR(this));
            TextView nameDrink = (TextView) child.findViewById(R.id.nameDrink);
            nameDrink.setTypeface(settings.fonts.typefaceR(this));
            TextView observation = (TextView) child.findViewById(R.id.observation);
            observation.setTypeface(settings.fonts.typefaceR(this));
            TextView subtotal2 = (TextView) child.findViewById(R.id.subtotal2);
            subtotal2.setTypeface(settings.fonts.typefaceR(this));
            ImageView picture = (ImageView) child.findViewById(R.id.picture);
            View viewDrink = (View)child.findViewById(R.id.viewDrink);
            LinearLayout drinkL = (LinearLayout) child.findViewById(R.id.drinkL);
            LinearLayout ingredientL=(LinearLayout) child.findViewById(R.id.ingredientL);
            View viewIngredient = (View) child.findViewById(R.id.viewIngredient);
            LinearLayout additionL =(LinearLayout) child.findViewById(R.id.additionL);
            LinearLayout observationL = (LinearLayout) child.findViewById(R.id.observationL);
            View viewFinal = (View) child.findViewById(R.id.viewFinal);

            nameProduct.setText(settings.detail.details.get(g).getName());
            Integer ca=g+1;
            cant.setText(ca.toString());

            if(settings.detail.details.size()<=1){
                viewFinal.setVisibility(View.GONE);
            }
            if(!settings.detail.details.get(g).getDrink().equals("")){

                nameDrink.setText(settings.detail.details.get(g).getDrink());
            }
            else{
                viewDrink.setVisibility(View.GONE);
                drinkL.setVisibility(View.GONE);

            }

            if(settings.detail.details.get(g).getIngredients().size()>0){


            }
            else{
                viewIngredient.setVisibility(View.GONE);
                ingredientL.setVisibility(View.GONE);

            }

            for (int aa=0;aa<settings.detail.details.get(g).getAdditions().size();aa++){
                View childA = View.inflate(detailC.this, R.layout.addition, null);
                TextView h = (TextView) childA.findViewById(R.id.h);
                h.setTypeface(settings.fonts.typefaceR(this));
                TextView i = (TextView) childA.findViewById(R.id.i);
                i.setTypeface(settings.fonts.typefaceR(this));
                TextView j = (TextView) childA.findViewById(R.id.j);
                j.setTypeface(settings.fonts.typefaceR(this));
                TextView k = (TextView) childA.findViewById(R.id.k);
                k.setTypeface(settings.fonts.typefaceR(this));
                TextView l = (TextView) childA.findViewById(R.id.l);
                l.setTypeface(settings.fonts.typefaceR(this));
                TextView nameAddition = (TextView) childA.findViewById(R.id.nameAddition);
                nameAddition.setTypeface(settings.fonts.typefaceR(this));
                TextView cantAddition = (TextView) childA.findViewById(R.id.cantAddition);
                cantAddition.setTypeface(settings.fonts.typefaceR(this));
                TextView valueTotal = (TextView) childA.findViewById(R.id.valueTotal);
                valueTotal.setTypeface(settings.fonts.typefaceR(this));
                TextView valueUnity = (TextView) childA.findViewById(R.id.valueUnity);
                valueUnity.setTypeface(settings.fonts.typefaceR(this));

                nameAddition.setText(settings.detail.details.get(g).getAdditions().get(aa).getName());
                cantAddition.setText(settings.detail.details.get(g).getAdditions().get(aa).getCant().toString());
                valueUnity.setText(settings.detail.details.get(g).getAdditions().get(aa).getUnitPrice().toString());
                valueTotal.setText(settings.detail.details.get(g).getAdditions().get(aa).getTotalPrice().toString());

                additionL.addView(childA);
            }
            if (!settings.detail.details.get(g).getObservation().equals("")){
                observation.setText(settings.detail.details.get(g).getObservation());
            }
            else{
                observationL.setVisibility(View.GONE);
            }

            subtotal2.setText("$ "+settings.detail.details.get(g).getSubtotalProduct().toString());

            String imgUrl ="http://godomicilios.co/admin/img/productos/"+settings.detail.details.get(g).getPicture();
            Picasso.with(detailC.this)
                    .load(imgUrl)
                    .into(picture, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            //do smth when picture is loaded successfully
                            String h="";
                            if(h==""){}
                        }

                        @Override
                        public void onError() {
                            //do smth when there is picture loading error
                            String h="";
                            if(h==""){}
                        }
                    });
            show.addView(child);
        }

    }
    public void answer (String ur) throws Exception{


        final RequestQueue queue = Volley.newRequestQueue(this,new HurlStack(
                null, CustomSSLSocketFactory.getSSLSocketFactory(detailC.this)));

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(JsonArrayRequest.Method.GET, ur, null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(final JSONArray response) {
                        try{

                            settings.status.statuses = new ArrayList<>();


                        }
                        catch (Exception e){

                            settings.status.statuses = new ArrayList<>();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                settings.status.statuses = new ArrayList<>();
            }
        }
        );
        queue.add(jsonArrayRequest);
    }
    public void mostrar()
    {
        if (content.getVisibility() == View.GONE)
        {
            animar(true);
            content.setVisibility(View.VISIBLE);
        }
    }

    public void ocultar()
    {
        if (content.getVisibility() == View.VISIBLE)
        {
            animar(false);
            content.setVisibility(View.GONE);
        }

    }

    private void animar(boolean mostrar)
    {
        AnimationSet set = new AnimationSet(true);
        Animation animation = null;
        if (mostrar)
        {
            //desde la esquina inferior derecha a la superior izquierda
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        }
        else
        {    //desde la esquina superior izquierda a la esquina inferior derecha
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        }
        //duración en milisegundos
        animation.setDuration(500);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);

        content.setLayoutAnimation(controller);
        content.startAnimation(animation);
    }

}
