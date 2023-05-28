package godomicilios.mdc.restaurantego;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import godomicilios.mdc.restaurantego.Utils.User;

public class ChatRoom extends AppCompatActivity {

    //Views
    @Bind(R.id.toolbar)Toolbar toolbar;
    //Inits
    private Context context;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ButterKnife.bind(this);
        setupActivity();
    }

    public void setupActivity() {
        context = this;
        setupToolbar();
    }}

    /*public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolbar.setContentInsetStartWithNavigation(0);
        }
    }

}
