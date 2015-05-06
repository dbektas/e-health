package com.smartcomet.e_health;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

public class LoginActivity extends Activity {

    EditText email, password;
    Button login;
    public String emailtxt, passtxt;
    List<NameValuePair> params;
    SharedPreferences pref;
    ServerRequest sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sr = new ServerRequest();

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);

        pref = getSharedPreferences("AppPref", MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailtxt = email.getText().toString();
                passtxt = password.getText().toString();
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", emailtxt));
                params.add(new BasicNameValuePair("password", passtxt));
                ServerRequest sr = new ServerRequest();

                JSONObject json = sr.getJSON("http://10.0.2.2:8080/login",params);

                if(json != null){
                    try{
                        String jsonstr = json.getString("response");
                        if(json.getBoolean("res")){
                            SharedPreferences.Editor edit = pref.edit();

                            edit.apply();
                            Intent mainActivity= new Intent(LoginActivity.this, MainActivity.class);

                            startActivity(mainActivity);
                            finish();
                        }

                        Toast.makeText(getApplication(), jsonstr, Toast.LENGTH_LONG).show();

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
