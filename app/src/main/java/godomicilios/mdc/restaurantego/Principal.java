package godomicilios.mdc.restaurantego;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.lib.colordialog.ColorDialog;
import godomicilios.mdc.restaurantego.Adapters.AdapterRecyclerOrders;
import godomicilios.mdc.restaurantego.Events.NotificationsBus;
import godomicilios.mdc.restaurantego.Events.StationBus;
import godomicilios.mdc.restaurantego.Service.Api;
import godomicilios.mdc.restaurantego.Utils.Certificate;
import godomicilios.mdc.restaurantego.Utils.Connection;
import godomicilios.mdc.restaurantego.Utils.User;
import godomicilios.mdc.restaurantego.settings.order;
import godomicilios.mdc.restaurantego.settings.settings;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Views
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.lbl_title_page)TextView lbl_title_page;
    @Bind(R.id.progress_bar)ProgressBar progress_bar;
    @Bind(R.id.lbl_data_not_found)TextView lbl_data_not_found;
    @Bind(R.id.recycler)RecyclerView recycler;
    @Bind(R.id.swipe_refresh)SwipeRefreshLayout swipe_refresh;
    //Inits
    private Context context;
    private User user;
    //String urlStatus = "https://godomicilios.co/webService/servicios_restaurante.php?svice=ESTADO_VENTA&metodo=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        setupActivity();
    }

    public void onStart() {
        super.onStart();
        StationBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        StationBus.getBus().unregister(this);
    }

    @Subscribe
    public void recievedChallenge(NotificationsBus notificationsBus){
        boolean active = notificationsBus.isRefresh();
        int notification_id = notificationsBus.getNotification_id();
        if(active) {
            System.out.println("success: " + true);
            clearNotification(notification_id);
            int sucId = user.getSucursal_id();
            newOrders(sucId, false);
        }
    }

    public void clearNotification(int notification_id) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notification_id);
    }

    public void setupActivity() {
        context = this;
        setupUser();
        setupToolbar();
        setupDrawerlayout();
        setupViews();
        setupFunctions();
        setupSwipeRefresh();
    }

    public void setupUser() {
        user = new User();
        SharedPreferences preferences = getSharedPreferences("session_preferences", Context.MODE_PRIVATE);
        user.setAdmin_id(preferences.getInt("admin_id", 0));
        user.setSucursal_id(preferences.getInt("sucursal_id", 0));
        user.setIdentification(preferences.getString("identification", ""));
        user.setEmail(preferences.getString("email", ""));
        user.setUsername(preferences.getString("username", ""));
        user.setFullname(preferences.getString("fullname", ""));
        user.setTelephone(preferences.getString("telephone", ""));
        user.setAvatar(preferences.getString("avatar", ""));
        user.setProfile_id(preferences.getInt("profile_id", 0));
        user.setCreated_at(preferences.getString("created_at", ""));
        user.setToken_firebase(preferences.getString("token_firebase", ""));
        System.out.println("user: " + user.toString());
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setupSwipeRefresh(){
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int sucId = user.getSucursal_id();
                newOrders(sucId, true);
            }
        });
        swipe_refresh.setColorSchemeResources(
                R.color.blueGo,
                R.color.redG,
                R.color.blueGoDark,
                R.color.redGoSelect
        );
    }

    public void setupViews() {
        lbl_title_page = (TextView) findViewById(R.id.lbl_title_page);
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
            case R.id.action_history:
                actionHistory();
                break;
            case R.id.action_chat:
                actionChatRoom();
                break;
            case R.id.action_logout:
                dialogLogout();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void actionHistory() {
        startActivity(new Intent(Principal.this, History.class));
        finish();
    }

    public void actionChatRoom() {
        startActivity(new Intent(Principal.this, ChatRoom.class));
    }

    public void dialogLogout() {
        ColorDialog dialog = new ColorDialog(this);
        dialog.setTitle("Cerrar Sesón");
        dialog.setContentText("¿Deseas realmente cerrar la sesión?");
        dialog.setPositiveListener("Si", new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                logout();
            }
        }).setNegativeListener("No", new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    public void logout() {
        SharedPreferences preferences =getSharedPreferences("session_preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putBoolean("session", false);
        editor.apply();
        startActivity(new Intent(Principal.this, Login.class));
        finish();
    }

//Funciones principales

    public void setupFunctions() {
        Connection connection = new Connection(context);
        if(connection.isOnline()) {
            progress_bar.setVisibility(View.VISIBLE);
            int sucId = user.getSucursal_id();
            newOrders(sucId, false);
        } else {
            setupImage();
            lbl_data_not_found.setText("Error de conexión");
        }
    }

    public void newOrders(int sucursal_id, final boolean flag) {
        final String url = getString(R.string.url_api);
        String svice = "NUEVO_PEDIDO";
        String metodo = "json";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url)
                .setClient(new OkClient(Certificate.createClient()))
                .build();
        Api api = restAdapter.create(Api.class);
        api.newOrder(svice, metodo, sucursal_id, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray array, retrofit.client.Response response) {
                if(array.size() > 0) {
                    settings.order.orders = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject json_order = array.get(i).getAsJsonObject();
                        order order = new Gson().fromJson(json_order, order.class);
                        settings.order.orders.add(order);
                    }
                    setupRecyclerHistory(settings.order.orders);
                    if(flag) swipe_refresh.setRefreshing(false);
                } else {
                    lbl_data_not_found.setVisibility(View.VISIBLE);
                    setupImage();
                    if(flag) swipe_refresh.setRefreshing(false);
                }
                progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                if(flag) swipe_refresh.setRefreshing(false);
                progress_bar.setVisibility(View.GONE);
                lbl_data_not_found.setVisibility(View.VISIBLE);
                lbl_data_not_found.setText("Error de conexión");
                setupImage();
                try {
                    Log.d("Principal(newOrders)", "Errors body: " + error.getMessage());
                } catch (Exception ex) {
                    Log.e("Principal(newOrders)", "Error ret: " + error + "; Error ex: " + ex.getMessage());
                }
            }
        });
    }

    public void setupImage() {
        int seed = (int) (Math.random() * 25 + 1);
        if(seed%2 == 0) {
            lbl_data_not_found.setCompoundDrawablesWithIntrinsicBounds(
                    null, ContextCompat.getDrawable(context, R.drawable.little_pig), null, null
            );
        } else {
            lbl_data_not_found.setCompoundDrawablesWithIntrinsicBounds(
                    null, ContextCompat.getDrawable(context, R.drawable.little_pulp), null, null
            );
        }
    }

    @OnClick(R.id.lbl_data_not_found)
    public void reloadHistory(View view) {
        Connection connection = new Connection(context);
        if(connection.isOnline()) {
            view.setVisibility(View.GONE);
            progress_bar.setVisibility(View.VISIBLE);
            int sucId = user.getSucursal_id();
            newOrders(sucId, false);
        } else {
            lbl_data_not_found.setText("No se detecto conexión activa a internet");
        }
    }

    public void setupRecyclerHistory(List<order> orders) {
        recycler.setVisibility(View.VISIBLE);
        AdapterRecyclerOrders adapter = new AdapterRecyclerOrders(context, orders);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recycler.setAdapter(adapter);
        recycler.getItemAnimator().setAddDuration(500);
    }

}
