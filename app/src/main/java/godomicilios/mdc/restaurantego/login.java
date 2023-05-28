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

    

}
