package godomicilios.mdc.restaurantego;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import godomicilios.mdc.restaurantego.settings.*;
import godomicilios.mdc.restaurantego.settings.detail;

import static godomicilios.mdc.restaurantego.R.mipmap.ic_launcher;

public class head extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String url = settings.url.url+ "NUEVO_PEDIDO&metodo=json&sucId=";
    String urlFirst ="";
    String url2 = settings.url.url+ "DETALLE_PEDIDO&metodo=json&pedido_id=";
    String url3 = settings.url.url+"HIST_PEDIDO&metodo=json&sucId=";
    String urlStatus = "https://godomicilios.co/webService/servicios_restaurante.php?svice=ESTADO_VENTA&metodo=json";
    Integer cou=0;
    final Handler handler = new Handler();
    LinearLayout li;
    DecimalFormat formatea = new DecimalFormat("###,###.##");
    TextView textView;
    String i=settings.stablishment.getUser();
    String j=settings.stablishment.getPss();
    String h=settings.stablishment.getStatus();
    Integer countError=0;
    public static final String MyPREFERENCES= "myPreferenced";
    public static final String User = "user";
    public static final String Password = "password";
    public static final String Status = "status";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        li = (LinearLayout) findViewById(R.id.li);
        settings.order.orders= new ArrayList<>();
        textView = (TextView) findViewById(R.id.textView);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
       if(settings.stablishment.getIdSuc()==null&&h=="a"){
           urlFirst = settings.url.url+ "LOGIN&metodo=json&usr="+i+"&pwd="+j;

       }

        try {
            http(url+settings.stablishment.getIdSuc());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cou=1;
            http(url+settings.stablishment.getIdSuc());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(settings.stablishment.getSelection()==0){
            handler.postDelayed(new Runnable() {
                public void run() {
                    if(settings.stablishment.getSelection()==0){
                        try {
                            http(url+settings.stablishment.getIdSuc());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    handler.postDelayed(this, 5000);
                }
            }, 5000);
        }
        else{
            textView.setText("HISTORIAL DE PEDIDO");
            try {
                http(url3+settings.stablishment.getIdSuc());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        if (settings.stablishment.getSelection()==0){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            finish();
            startActivity(getIntent());
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.action_now:
                if(settings.stablishment.getSelection() == 1) {
                    settings.stablishment.setSelection(0);
                    startActivity(getIntent());
                    finish();
                }
                break;
            case R.id.action_history:
                settings.stablishment.setSelection(1);
                startActivity(getIntent());
                finish();
                break;
            case R.id.action_chat:
                break;
            case R.id.action_logout:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void http (final String url) throws Exception{


        final RequestQueue queue = Volley.newRequestQueue(this,new HurlStack(
                null, CustomSSLSocketFactory.getSSLSocketFactory(head.this)));

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(JsonArrayRequest.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(final JSONArray response) {
                        try{

                            ArrayList<order> query = new ArrayList<>();
                            for (int a =0;a<response.length();a++){
                                final JSONObject ord = (JSONObject) response.getJSONObject(a);
                            query.add(new order(

                                    ord.getInt("id_pedido"),a,
                                    ord.getInt("estado_pedido"),
                                    ord.getInt("metodo_pago_id"),
                                    ord.getInt("venta_total_pedido"),
                                    ord.getInt("costo_domicilio"),
                                    ord.getString("usuario"),
                                    ord.getString("fecha_pedido"),
                                    ord.getString("direccion_domicilio")
                            ));
                            }
                            Integer total=0;
                            Integer idValidaror=0;
                            for (order orders:query){
                                Integer validator = 0;
                                for (order or:settings.order.orders){
                                    Integer idOr=or.getIdOrder();
                                    Integer idOrder=orders.getIdOrder();
                                    if(idOr.equals(idOrder)){
                                        validator=1;
                                        break;
                                    }
                                }
                                if(validator==0){

                                    settings.order.orders.add(orders);
                                    total=1;
                                }
                                idValidaror =idValidaror+1;
                            }
                            Integer idValidaror2=0;

                            for (order or:settings.order.orders){

                                Integer validator = 0;
                                for (order orders:query){
                                    if(or.getIdOrder().equals(orders.getIdOrder())){
                                        validator=1;
                                        break;
                                    }
                                }
                                if(validator==0){
                                    settings.order.orders.remove(idValidaror2);
                                    total=1;
                                }
                                idValidaror2=idValidaror2+1;
                            }

                            if(total==1) {
                                if(settings.stablishment.getSelection()==0){
                                    if(cou>0){
                                        showNotification();
                                    }
                                }

                                li.removeAllViews();
                                for (int h = 0; h < settings.order.orders.size(); h++) {
                                    View child = View.inflate(head.this, R.layout.new_domi, null);
                                    TextView textView4 = (TextView) child.findViewById(R.id.textView4);
                                    textView4.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView no = (TextView) child.findViewById(R.id.no);
                                    no.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView textView19 = (TextView) child.findViewById(R.id.textView19);
                                    textView19.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView status = (TextView) child.findViewById(R.id.status);
                                    status.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView textView17 = (TextView) child.findViewById(R.id.textView17);
                                    textView17.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView metho = (TextView) child.findViewById(R.id.metho);
                                    metho.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView textView15 = (TextView) child.findViewById(R.id.textView15);
                                    textView15.setTypeface(settings.fonts.typefaceR(head.this));
                                    final ImageView imageView3 = (ImageView) child.findViewById(R.id.imageView3);
                                    TextView textView14 = (TextView) child.findViewById(R.id.textView14);
                                    textView14.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView sale = (TextView) child.findViewById(R.id.sale);
                                    sale.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView textView12 = (TextView) child.findViewById(R.id.textView12);
                                    textView12.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView price = (TextView) child.findViewById(R.id.price);
                                    price.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView textView10 = (TextView) child.findViewById(R.id.textView10);
                                    textView10.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView user = (TextView) child.findViewById(R.id.user);
                                    user.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView textView8 = (TextView) child.findViewById(R.id.textView8);
                                    textView8.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView date = (TextView) child.findViewById(R.id.date);
                                    date.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView textView6 = (TextView) child.findViewById(R.id.textView6);
                                    textView6.setTypeface(settings.fonts.typefaceR(head.this));
                                    TextView address = (TextView) child.findViewById(R.id.address);
                                    address.setTypeface(settings.fonts.typefaceR(head.this));
                                    ImageView imageView6 = (ImageView) child.findViewById(R.id.imageView6);
                                    imageView3.setId(h);

                                    if (settings.order.orders.get(h).getStatus() == 1) {
                                        status.setText("SE EFECTUÓ EL PAGO");
                                        imageView6.setImageDrawable(getResources().getDrawable(R.drawable.puntovax3));
                                    } else {
                                        status.setText("NO SE EFECTUÓ EL PAGO");
                                    }
                                    switch (settings.order.orders.get(h).getMethod()) {
                                        case 1:
                                            metho.setText("EFECTIVO");
                                            break;
                                        case 2:
                                            metho.setText("DATÁFONO");
                                            break;
                                        case 3:
                                            metho.setText("OnLine");
                                            break;
                                    }
                                    sale.setText("$"+formatea.format(settings.order.orders.get(h).getSale()).toString());
                                    price.setText("$"+formatea.format(settings.order.orders.get(h).getPrice()).toString());
                                    user.setText(settings.order.orders.get(h).getUser());
                                    date.setText(settings.order.orders.get(h).getDate());
                                    address.setText(settings.order.orders.get(h).getAddress());
                                    no.setText("No." + settings.order.orders.get(h).getIdOrder());
                                    imageView3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                httpDetail(url2+settings.order.orders.get(imageView3.getId()).getIdOrder(),
                                                        imageView3.getId());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    li.addView(child, li.getChildCount());
                                }
                            }

                        }
                        catch (Exception e){

                            String mensajee ="No hay establecimientos cerca";

                            Toast toast1 =
                                    Toast.makeText(getApplicationContext(),
                                            mensajee, Toast.LENGTH_SHORT);

                            toast1.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    http(url+settings.stablishment.getIdSuc());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        );
        queue.add(jsonArrayRequest);
    }



    public void httpDetail (final String url, final Integer num) throws Exception{
        settings.detail.details = new ArrayList<>();


        final RequestQueue queue = Volley.newRequestQueue(this,new HurlStack(
                null, CustomSSLSocketFactory.getSSLSocketFactory(head.this)));

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(JsonArrayRequest.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(final JSONArray response) {
                        try{

                            for(int a =0;a<response.length();a++){
                                final JSONObject deta = (JSONObject) response.getJSONObject(a);

                                ArrayList<ingredient> ingredients = new ArrayList<>();
                                ArrayList<addition> additions = new ArrayList<>();
                                String dri="";
                                String ob = "";
                                Integer drinkId = deta.getInt("bebida");
                                if (drinkId ==1 ){
                                    JSONObject jsonObject= deta.getJSONObject("detalle_bebida");
                                    if(jsonObject.length()>0){
                                        String h = jsonObject.getString("nombre_bebida");
                                        String k = jsonObject.getString("tamano");
                                        dri = h+" "+k;
                                    }
                                }

                                if(deta.getJSONArray("detalle_ingre").length()>0){
                                    JSONArray jsonArray= deta.getJSONArray("detalle_ingre");
                                    for(int h = 0;h<jsonArray.length();h++){
                                        final JSONObject jsonObject = (JSONObject) response.getJSONObject(h);
                                        if(!jsonObject.getString("nombre_ingrediente").equals("")){
                                            ingredients.add(new ingredient(
                                                    jsonObject.getString("nombre_ingrediente")
                                            ));
                                        }
                                    }
                                }

                                if(deta.getJSONArray("detalle_adi").length()>0){
                                    JSONArray jsonArray= deta.getJSONArray("detalle_adi");
                                    for (int h=0;h<jsonArray.length();h++){
                                        final JSONObject jsonObject = (JSONObject) jsonArray.getJSONObject(h);
                                        Integer totalAddition;
                                        totalAddition= jsonObject.getInt("cantidad")*
                                                jsonObject.getInt("valor");
                                        String nameAddition=jsonObject.getString("nombre_adicion");
                                        Integer cantt =jsonObject.getInt("cantidad");
                                        Integer value = jsonObject.getInt("valor");
                                            additions.add(new addition(
                                                    nameAddition,
                                                    cantt,
                                                    value,
                                                    totalAddition

                                            ));
                                        totalAddition= jsonObject.getInt("cantidad")*
                                                jsonObject.getInt("valor");

                                    }
                                }

                                settings.detail.details.add(new detail(
                                        deta.getString("nombre_producto"),a, deta.getString("foto_producto"),
                                        deta.getInt("venta_producto"), dri, deta.getString("observaciones"),
                                        ingredients,additions));
                            }
                            settings.detail.setNum(num);
                            Intent go = new Intent(head.this, detailC.class);
                            startActivity(go);

                        }
                        catch (Exception e){


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                settings.detail.setNum(num);
                Intent go = new Intent(head.this, detailC.class);
                startActivity(go);
            }
        }
        );
        queue.add(jsonArrayRequest);
    }

    public void showNotification (){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(ic_launcher);
        builder.setContentTitle("Nuevo Pedido!");
        builder.setContentText("Tienes un nuevo pedido");
        builder.setSound(alarmSound);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        Intent go = new Intent(this, head.class);
        stackBuilder.addParentStack(head.class);
        stackBuilder.addNextIntent(go);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }

    /*public void httpFirst (final String url) throws Exception{

        final RequestQueue queue = Volley.newRequestQueue(this,new HurlStack(
                null, CustomSSLSocketFactory.getSSLSocketFactory(head.this)));

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(JsonObjectRequest.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(final JSONObject response) {
                        try{
                            if (response.length()>1){
                                JSONArray jsonArray= response.getJSONArray("row");
                                for(int i = 0;i<jsonArray.length();i++){
                                    final JSONObject sta = (JSONObject) jsonArray.getJSONObject(i);
                                    settings.stablishment.setId( sta.getInt("id_admin"));
                                    settings.stablishment.setName(sta.getString("nombres"));
                                    settings.stablishment.setIdSuc(sta.getInt("sucursal_id"));
                                }

                            }
                        }
                        catch (Exception e){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (countError ==2){
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Status,"status");
                        editor.commit();

                    String mensajee ="Oops ha ocurrido un error, por favor Ingresa tus datos";

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    mensajee, Toast.LENGTH_SHORT);

                    toast1.show();
                    Intent go = new Intent(head.this, MainActivity.class);
                    startActivity(go);
                }
                else{
                    try {
                        httpFirst(urlFirst);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                countError=countError+1;
            }
        }
        );
        queue.add(jsonObjectRequest);
    }*/

}
