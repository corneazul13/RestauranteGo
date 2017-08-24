package godomicilios.mdc.restaurantego;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import godomicilios.mdc.restaurantego.Service.Api;
import godomicilios.mdc.restaurantego.Utils.Certificate;
import godomicilios.mdc.restaurantego.Utils.MaterialDialog;
import godomicilios.mdc.restaurantego.settings.detail;
import godomicilios.mdc.restaurantego.settings.ingredient;
import godomicilios.mdc.restaurantego.settings.addition;
import godomicilios.mdc.restaurantego.settings.settings;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

/**
 * Creado por Deimer el 22/08/17.
 */

public class Details extends AppCompatActivity {

    //Views-Toolbar
    @Bind(R.id.toolbar)Toolbar toolbar;
    //Views-TextView
    @Bind(R.id.lbl_title_page)TextView lbl_title_page;
    @Bind(R.id.lbl_subtotal)TextView lbl_subtotal;
    @Bind(R.id.lbl_cost_living)TextView lbl_cost_living;
    @Bind(R.id.lbl_total)TextView lbl_total;
    @Bind(R.id.txt_subtotal)TextView txt_subtotal;
    @Bind(R.id.txt_price)TextView txt_price;
    @Bind(R.id.txt_total)TextView txt_total;
    @Bind(R.id.lbl_observations)TextView lbl_observations;
    @Bind(R.id.lbl_details)TextView lbl_details;
    //Views-Edittext
    @Bind(R.id.txt_observations)EditText txt_observations;
    //Views-Fab
    @Bind(R.id.fab_cancel)FloatingActionButton fab_cancel;
    @Bind(R.id.fab_confirm)FloatingActionButton fab_confirm;
    //Views-Layout
    @Bind(R.id.layout_observations)LinearLayout layout_observations;
    @Bind(R.id.layout_content)LinearLayout layout_content;
    @Bind(R.id.layout_details)LinearLayout layout_details;
    @Bind(R.id.scroll_details)ScrollView scroll_details;

    //Utils
    private Context context;
    private MaterialDialog dialog;
    //private String url;
    private DecimalFormat format;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setupActivity();
    }

    public void setupActivity() {
        context = this;
        dialog = new MaterialDialog(context);
        //url = settings.url.url+ "RESPUESTA_PEDIDO&metodo=json&pedido_id=";
        format = new DecimalFormat("###,###.##");
        getExtras();
        setupToolbar();
        setupLabels();
    }

    public void getExtras(){
        Bundle extras = getIntent().getExtras();
        int order_id = extras.getInt("order_id");
        int position = extras.getInt("position");
        System.out.println("order_id: " + order_id + "; position: " + position);
        orderDetail(order_id, position);
    }

    public void setupToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setContentInsetStartWithNavigation(0);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void setupLabels() {
        lbl_title_page.setTypeface(settings.fonts.typefaceR(context));
        lbl_subtotal.setTypeface(settings.fonts.typefaceR(context));
        lbl_cost_living.setTypeface(settings.fonts.typefaceR(context));
        lbl_total.setTypeface(settings.fonts.typefaceR(context));
        txt_subtotal.setTypeface(settings.fonts.typefaceR(context));
        txt_price.setTypeface(settings.fonts.typefaceR(context));
        txt_total.setTypeface(settings.fonts.typefaceR(context));
        lbl_observations.setTypeface(settings.fonts.typefaceR(context));
        lbl_details.setTypeface(settings.fonts.typefaceR(context));
        txt_observations.setTypeface(settings.fonts.typefaceR(context));
    }

    public void orderDetail(int order_id, final int position) {
        dialog.dialogProgress("Cargando detalles...");
        final String url = context.getString(R.string.url_api);
        String svice = "DETALLE_PEDIDO";
        String metodo = "json";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url)
                .setClient(new OkClient(Certificate.createClient()))
                .build();
        Api api = restAdapter.create(Api.class);
        api.orderDetail(svice, metodo, order_id, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray array, retrofit.client.Response response) {
                if(array.size() > 0) {
                    setupinfo(array);
                    setInfo(position);
                } else {
                    dialog.toastWarning("No hay datos");
                }
                dialog.cancelProgress();
            }
            @Override
            public void failure(RetrofitError error) {
                dialog.cancelProgress();
                try {
                    Log.d("AdapterRecyclerOrders(orderDetail)", "Errors body: " + error.getMessage());
                } catch (Exception ex) {
                    Log.e("AdapterRecyclerOrders(orderDetail)", "Error ret: " + error + "; Error ex: " + ex.getMessage());
                }
            }
        });
    }

    public void setupinfo(JsonArray array) {
        settings.detail.details = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            JsonObject data = array.get(i).getAsJsonObject();
            ArrayList<ingredient> ingredients = new ArrayList<>();
            ArrayList<addition> additions = new ArrayList<>();
            String dri = "";
            Integer drinkId = data.get("bebida").getAsInt();
            if(drinkId == 1) {
                JsonObject json_drink = data.getAsJsonObject("detalle_bebida");
                if(!json_drink.isJsonNull()) {
                    String name = json_drink.get("nombre_bebida").getAsString();
                    String size = json_drink.get("tamano").getAsString();
                    dri = name+" "+size;
                }
            }

            JsonArray array_ingredients = data.getAsJsonArray("detalle_ingre");
            if(array_ingredients.size() > 0) {
                for (int j = 0; j < array_ingredients.size(); j++) {
                    String nombre_ing = array_ingredients.get(i).getAsJsonObject().get("nombre_ing").getAsString();
                    ingredients.add(new ingredient(nombre_ing));
                }
            }

            JsonArray array_additions = data.getAsJsonArray("detalle_adi");
            if(array_additions.size() > 0) {
                for (int j = 0; j < array_additions.size(); j++) {
                    JsonObject json_addition = array_additions.get(i).getAsJsonObject();
                    Integer total = json_addition.get("cantidad").getAsInt() * json_addition.get("valor").getAsInt();
                    String name = json_addition.get("nombre_adicion").getAsString();
                    Integer quantity = json_addition.get("cantidad").getAsInt();
                    Integer value = json_addition.get("valor").getAsInt();
                    additions.add(new addition(name, quantity, value, total));
                }
            }

            settings.detail.details.add(new detail(
                    data.get("nombre_producto").getAsString(),
                    i,
                    data.get("foto_producto").getAsString(),
                    data.get("venta_producto").getAsInt(),
                    dri,
                    data.get("observaciones").getAsString(),
                    ingredients,
                    additions));
        }
    }

    public void setInfo(int position) {
        settings.detail.setNum(position);
        scroll_details.setVisibility(View.VISIBLE);
        txt_subtotal.setText("$" + format.format(settings.order.orders.get(settings.detail.getNum()).getSale()));
        txt_price.setText("$" + format.format(settings.order.orders.get(settings.detail.getNum()).getPrice()));
        Integer numTotal = settings.order.orders.get(settings.detail.getNum()).getSale()+settings.order.orders.get(settings.detail.getNum()).getPrice();
        txt_total.setText("$" + format.format(numTotal));
    }

}
