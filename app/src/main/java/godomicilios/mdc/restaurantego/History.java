package godomicilios.mdc.restaurantego;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import godomicilios.mdc.restaurantego.Adapters.AdapterRecyclerOrders;
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

public class History extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    //Views
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.drawer_layout)DrawerLayout drawer_layout;
    @Bind(R.id.progress_bar)ProgressBar progress_bar;
    @Bind(R.id.lbl_data_not_found)TextView lbl_data_not_found;
    @Bind(R.id.recycler)RecyclerView recycler;
    //Inits
    private Context context;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        setupActivity();
    }

    public void setupActivity() {
        context = this;
        setupUser();
        setupToolbar();
        setupDrawerlayout();
        setupFunctions();
        reloadHistory();
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
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolbar.setContentInsetStartWithNavigation(0);
        }
    }

    public void setupDrawerlayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                ordersNow();
                break;
            case R.id.action_chat:
                break;
            case R.id.action_logout:
                logout();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ordersNow() {
        startActivity(new Intent(History.this, Principal.class));
        finish();
    }

    public void logout() {
        SharedPreferences preferences =getSharedPreferences("session_preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putBoolean("session", false);
        editor.apply();
        startActivity(new Intent(History.this, Login.class));
        finish();
    }

    public void setupFunctions() {
        Connection connection = new Connection(context);
        if(connection.isOnline()) {
            progress_bar.setVisibility(View.VISIBLE);
            int sucId = user.getSucursal_id();
            history(sucId);
        } else {
            setupImage();
            lbl_data_not_found.setText("Error de conexión");
        }
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
                    settings.order.orders = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject json_order = array.get(i).getAsJsonObject();
                        order order = new Gson().fromJson(json_order, order.class);
                        settings.order.orders.add(order);
                    }
                    setupRecyclerHistory(settings.order.orders);
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
                    Log.d("Principal(History)", "Errors body: " + error.getMessage());
                } catch (Exception ex) {
                    Log.e("Principal(History)", "Error ret: " + error + "; Error ex: " + ex.getMessage());
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

    public void reloadHistory() {
        lbl_data_not_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connection connection = new Connection(context);
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
        AdapterRecyclerOrders adapter = new AdapterRecyclerOrders(context, orders);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recycler.setAdapter(adapter);
        recycler.getItemAnimator().setAddDuration(500);
    }

}
