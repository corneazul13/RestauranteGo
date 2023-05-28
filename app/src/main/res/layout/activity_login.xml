package godomicilios.mdc.restaurantego;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Welcome extends AppCompatActivity {

    @Bind(R.id.img_logo)ImageView img_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        setupActivity();
    }

    public void setupActivity() {
        animateLogo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupPreferences();
            }
        }, 2500);
    }

    public void animateLogo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.FadeIn).duration(700).playOn(img_logo);
                img_logo.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    public void setupPreferences() {
        SharedPreferences preferences = getSharedPreferences("session_preferences", Context.MODE_PRIVATE);
        boolean session = preferences.getBoolean("session", false);
        if(session) {
            Intent principal = new Intent (Welcome.this, Principal.class);
            startActivity(principal);
        } else {
            preferences.getInt("admin_id", 0);
            preferences.getInt("sucursal_id", 0);
            preferences.getString("identification", "");
            preferences.getString("email", "");
            preferences.getString("username", "");
            preferences.getString("fullname", "");
            preferences.getString("telephone", "");
            preferences.getString("avatar", "");
            preferences.getInt("profile_id", 0);
            preferences.getString("created_at", "");
            preferences.getString("token_firebase", tokenFirebase());
            Intent login = new Intent (Welcome.this, Login.class);
            startActivity(login);
        }
        finish();
    }

    public String tokenFirebase() {
        String token_refresh = FirebaseInstanceId.getInstance().getToken();
        System.out.println("token: " + token_refresh);
        return token_refresh;
    }

}
