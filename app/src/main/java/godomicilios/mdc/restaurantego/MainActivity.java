package godomicilios.mdc.restaurantego;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import godomicilios.mdc.restaurantego.settings.CustomSSLSocketFactory;
import godomicilios.mdc.restaurantego.settings.settings;

public class MainActivity extends AppCompatActivity {

    Integer continueNow =0;
    String pss="";
    String usr="";
    String suc_id;
    Button button;
    TextView textView2;
    EditText editText2, editText;

    public static final String MyPREFERENCES= "myPreferenced";
    public static final String User = "user";
    public static final String Password = "password";
    public static final String SUC_ID  = "sucId";
    public static final String Status = "status";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setTypeface(settings.fonts.typefaceR(this));
        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setTypeface(settings.fonts.typefaceR(this));
        editText = (EditText) findViewById(R.id.editText);
        editText.setTypeface(settings.fonts.typefaceR(this));
        editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setTypeface(settings.fonts.typefaceR(this));
        settings.stablishment.isOnline(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedpreferences.getAll().size()>0 ){

            settings.stablishment.setStatus( sharedpreferences.getString(Status, ""));
            settings.stablishment.setUser(sharedpreferences.getString(User, ""));
            settings.stablishment.setPss(sharedpreferences.getString(Password, ""));
            settings.stablishment.setIdSuc(sharedpreferences.getString(SUC_ID, ""));

            if(settings.stablishment.getStatus().equals("a")&&Integer.parseInt(settings.stablishment.getIdSuc())!=0){
                Intent go = new Intent(MainActivity.this, head.class);
                startActivity(go);
            }
        }

        /*Intent go = new Intent (MainActivity.this, head.class);
        startActivity(go);*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Context context = getApplicationContext();
                CharSequence text = "Usuario o contraseña incorrectos!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/

                if(editText2.getText().toString().equals("")){
                     String text= "Digita un usuario!";
                    Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(editText.getText().toString().equals("")){
                    String text= "Digita una contraseña!";
                    Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {

                    usr=editText2.getText().toString();
                    pss= editText.getText().toString();
                    String url = settings.url.url+ "LOGIN&metodo=json&usr="+usr+"&pwd="+pss;
                    try {
                        http(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void http (final String url) throws Exception{

        final RequestQueue queue = Volley.newRequestQueue(this,new HurlStack(
                null, CustomSSLSocketFactory.getSSLSocketFactory(MainActivity.this)));

        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading. Please wait...", true);
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
                                    settings.stablishment.setIdSuc(sta.getString("sucursal_id"));
                                }
                                dialog.dismiss();

                                suc_id = settings.stablishment.getIdSuc().toString();
                                    String n  = usr;
                                    String ph  = pss;
                                    String si = suc_id;
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(User, n);
                                    editor.putString(Password, ph);
                                    editor.putString(Status,"a");
                                    editor.putString(SUC_ID, suc_id);
                                    editor.commit();

                                if(continueNow ==0){
                                    Intent go = new Intent (MainActivity.this, head.class);
                                    startActivity(go);
                                }



                            }
                            else{
                                dialog.dismiss();
                                Toast toast = Toast.makeText(MainActivity.this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT);
                                toast.show();
                                InputMethodManager inputMethodManager =  (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                editText.setText("");
                            }


                        }
                        catch (Exception e){

                            String mensajee ="Usuario o contraseña incorrectos";

                            Toast toast1 =
                                    Toast.makeText(getApplicationContext(),
                                            mensajee, Toast.LENGTH_SHORT);

                            toast1.show();
                            dialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
            }
        }
        );
        queue.add(jsonObjectRequest);
    }
    private class loginUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return null;
        }
    }
}
