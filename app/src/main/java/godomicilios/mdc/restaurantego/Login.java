package godomicilios.mdc.restaurantego;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import butterknife.Bind;
import butterknife.ButterKnife;
import godomicilios.mdc.restaurantego.Service.Api;
import godomicilios.mdc.restaurantego.Utils.Connection;
import godomicilios.mdc.restaurantego.Utils.MaterialDialog;
import godomicilios.mdc.restaurantego.settings.settings;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class Login extends AppCompatActivity {

    @Bind(R.id.lbl_title_page)TextView lbl_title_page;
    @Bind(R.id.txt_username)EditText txt_username;
    @Bind(R.id.txt_password)EditText txt_password;
    @Bind(R.id.but_validate)Button but_validate;

    private Context context;
    private Connection connection;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupActivity();
    }

    public void setupActivity() {
        context = this;
        connection = new Connection(context);
        dialog = new MaterialDialog(context);
        settings.stablishment.isOnline(context);
        setupViews();
        validateCredentials();
    }

    public void setupViews() {
        lbl_title_page.setTypeface(settings.fonts.typefaceR(context));
        txt_username.setTypeface(settings.fonts.typefaceR(context));
        txt_password.setTypeface(settings.fonts.typefaceR(context));
        but_validate.setTypeface(settings.fonts.typefaceR(context));
    }

    public void validateCredentials() {
        but_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connection.isOnline()) {
                    String username = txt_username.getText().toString().trim();
                    String password = txt_password.getText().toString().trim();
                    if(username.isEmpty()) {
                        dialog.toastWarning("Antes debes agregar un usuario");
                    } else {
                        if(password.isEmpty()) {
                            dialog.toastWarning("Antes debes agregar una contraseña");
                        } else {
                            String svice = "LOGIN"; String metodo = "json";
                            login(svice, metodo, username, password);
                        }
                    }
                }
            }
        });
    }

    public void login(String svice, String metodo, String username, String password) {
        dialog.dialogProgress("Iniciando Sesión...");
        final String url = getString(R.string.url_api);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url)
                .build();
        Api api = restAdapter.create(Api.class);
        api.login(svice, metodo, username, password, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                String error = jsonObject.get("error").getAsString();
                if(error.equalsIgnoreCase("NO")) {
                    JsonArray array = jsonObject.getAsJsonArray("row");
                    JsonObject json_user = array.get(0).getAsJsonObject();
                    savePreferences(json_user);
                    startActivity(new Intent(Login.this, Principal.class));
                    finish();
                } else {
                    dialog.dialogWarnings("Error", "Usuario o contraseña incorrecta.");
                }
                dialog.cancelProgress();

            }
            @Override
            public void failure(RetrofitError error) {
                dialog.cancelProgress();
                dialog.toastWarning("Error de conexión");
                try {
                    Log.d("Login(login)", "Errors body: " + error.getMessage());
                } catch (Exception ex) {
                    Log.e("Login(login)", "Error ret: " + error + "; Error ex: " + ex.getMessage());
                }
            }
        });
    }

    public void savePreferences(JsonObject json_user) {
        String fullname = json_user.get("nombres").getAsString() + " " + json_user.get("apellidos").getAsString();
        SharedPreferences preferences = getSharedPreferences("session_preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("admin_id", json_user.get("id_admin").getAsInt());
        editor.putInt("sucursal_id", json_user.get("sucursal_id").getAsInt());
        editor.putString("identification", validateNull(json_user, "identificacion"));
        editor.putString("email", validateNull(json_user, "correo"));
        editor.putString("username", validateNull(json_user, "usr"));
        editor.putString("fullname", fullname);
        editor.putString("telephone", validateNull(json_user, "telefono"));
        editor.putString("avatar", validateNull(json_user, "imagen"));
        editor.putInt("profile_id", json_user.get("perfil").getAsInt());
        editor.putString("created_at", validateNull(json_user, "fecha_registro"));
        editor.putBoolean("session", true);
        editor.putString("token_firebase", tokenFirebase());
        editor.apply();
    }

    public String tokenFirebase() {
        String token_refresh = FirebaseInstanceId.getInstance().getToken();
        System.out.println("token: " + token_refresh);
        return token_refresh;
    }

    public String validateNull(JsonObject json, String key) {
        if(json.get(key).isJsonNull()) {
            return "no data";
        } else {
            return json.get(key).getAsString();
        }
    }

}
