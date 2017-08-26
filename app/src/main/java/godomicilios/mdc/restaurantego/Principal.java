package godomicilios.mdc.restaurantego;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.lbl_title_page)TextView lbl_title_page;

    //String url = settings.url.url+ "NUEVO_PEDIDO&metodo=json&sucId=";
    //String urlFirst ="";
    //String url2 = settings.url.url+ "DETALLE_PEDIDO&metodo=json&pedido_id=";
    //String url3 = settings.url.url+"HIST_PEDIDO&metodo=json&sucId=";
    //String urlStatus = "https://godomicilios.co/webService/servicios_restaurante.php?svice=ESTADO_VENTA&metodo=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setupToolbar();
        setupDrawerlayout();
        setupViews();
        setupActivity();
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

    public void setupViews() {
        lbl_title_page = (TextView) findViewById(R.id.lbl_title_page);
    }

    public void setupActivity() {

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
                history();
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

    public void history() {
        startActivity(new Intent(Principal.this, History.class));
        finish();
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

}
