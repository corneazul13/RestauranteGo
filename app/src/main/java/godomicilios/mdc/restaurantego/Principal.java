package godomicilios.mdc.restaurantego;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import godomicilios.mdc.restaurantego.Adapters.AdapterRecyclerOrders;
import godomicilios.mdc.restaurantego.Service.Api;
import godomicilios.mdc.restaurantego.Utils.Certificate;
import godomicilios.mdc.restaurantego.Utils.Connection;
import godomicilios.mdc.restaurantego.settings.*;
import godomicilios.mdc.restaurantego.settings.detail;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ProgressBar progress_bar;
    private RecyclerView recycler;
    private TextView lbl_data_not_found;

    String url = settings.url.url+ "NUEVO_PEDIDO&metodo=json&sucId=";
    String urlFirst ="";
    String url2 = settings.url.url+ "DETALLE_PEDIDO&metodo=json&pedido_id=";
    String url3 = settings.url.url+"HIST_PEDIDO&metodo=json&sucId=";
    String urlStatus = "https://godomicilios.co/webService/servicios_restaurante.php?svice=ESTADO_VENTA&metodo=json";
    LinearLayout principal_layout;
    TextView textView;
    String i=settings.stablishment.getUser();
    String j=settings.stablishment.getPss();
    String h=settings.stablishment.getStatus();
    public static final String MyPREFERENCES= "myPreferenced";
    public static final String User = "user";
    public static final String Password = "password";
    public static final String Status = "status";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);
        setupToolbar();
        setupDrawerlayout();
        setupViews();
        setupActivity();
        reloadHistory();
        getTokenFirebase();
    }

    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolbar.setContentInsetStartWithNavigation(0);
        }
    }

    public void setupDrawerlayout() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setupViews() {
        principal_layout = (LinearLayout) findViewById(R.id.li);
        textView = (TextView) findViewById(R.id.textView);
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        recycler = (RecyclerView)findViewById(R.id.recycler);
        lbl_data_not_found = (TextView)findViewById(R.id.lbl_data_not_found);
    }

    public void setupActivity() {
        settings.order.orders= new ArrayList<>();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(settings.stablishment.getIdSuc()==null&&h.equals("a")){
            urlFirst = settings.url.url+ "LOGIN&metodo=json&usr="+i+"&pwd="+j;
        }
        if(settings.stablishment.getSelection()==0){
            int sucId = Integer.parseInt(settings.stablishment.getIdSuc());
            history(sucId);
        } else{
            textView.setText(getResources().getText(R.string.lbl_title_view));
            try {
                Connection connection = new Connection(Principal.this);
                if(connection.isOnline()) {
                    progress_bar.setVisibility(View.VISIBLE);
                    System.out.println("url_par_two: " + settings.stablishment.getIdSuc());
                    int sucId = Integer.parseInt(settings.stablishment.getIdSuc());
                    history(sucId);
                } else {
                    setupImage();
                    lbl_data_not_found.setText("Error de conexión");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

//region Peticion historial de pedidos con retrofit

    public void history(int sucursal_id) {
        final String url = getString(R.string.url_api);
        String svice = "HIST_PEDIDO";
        String metodo = "json";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url)
                .setClient(new OkClient(Certificate.createClient()))
                .build();
        Api api = restAdapter.create(Api.class);
        api.historial(svice, metodo, sucursal_id, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray array, retrofit.client.Response response) {
                if(array.size() > 0) {
                    List<order> orders = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject json_order = array.get(i).getAsJsonObject();
                        order order = new Gson().fromJson(json_order, order.class);
                        orders.add(order);
                    }
                    setupRecyclerHistory(orders);
                } else {
                    lbl_data_not_found.setVisibility(View.VISIBLE);
                    setupImage();
                }
                progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                progress_bar.setVisibility(View.GONE);
                lbl_data_not_found.setVisibility(View.VISIBLE);
                lbl_data_not_found.setText("Error de conexión");
                setupImage();
                try {
                    Log.d("Principal(history)", "Errors body: " + error.getMessage());
                } catch (Exception ex) {
                    Log.e("Principal(history)", "Error ret: " + error + "; Error ex: " + ex.getMessage());
                }
            }
        });
    }

    public void setupImage() {
        int seed = (int) (Math.random() * 25 + 1);
        if(seed%2 == 0) {
            lbl_data_not_found.setCompoundDrawablesWithIntrinsicBounds(
                    null, ContextCompat.getDrawable(Principal.this, R.drawable.little_pig), null, null
            );
        } else {
            lbl_data_not_found.setCompoundDrawablesWithIntrinsicBounds(
                    null, ContextCompat.getDrawable(Principal.this, R.drawable.little_pulp), null, null
            );
        }
    }

    public void reloadHistory() {
        lbl_data_not_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = new Connection(Principal.this);
                if(connection.isOnline()) {
                    view.setVisibility(View.GONE);
                    progress_bar.setVisibility(View.VISIBLE);
                    System.out.println("url_par_two: " + settings.stablishment.getIdSuc());
                    int sucId = Integer.parseInt(settings.stablishment.getIdSuc());
                    history(sucId);
                } else {
                    lbl_data_not_found.setText("No se detecto conexión activa a internet");
                }
            }
        });
    }

    public void setupRecyclerHistory(List<order> orders) {
        recycler.setVisibility(View.VISIBLE);
        AdapterRecyclerOrders adapter = new AdapterRecyclerOrders(Principal.this, orders);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recycler.setAdapter(adapter);
        recycler.getItemAnimator().setAddDuration(500);
    }

    public void getTokenFirebase() {
        String token_refresh = FirebaseInstanceId.getInstance().getToken();
        System.out.println("token: " + token_refresh);
    }

//endregion

}
