package com.wtf.wherefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.wtf.wherefood.Share.AppConfig;
import com.wtf.wherefood.Share.UserInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    com.ornach.nobobutton.NoboButton btnLogin;
    com.ornach.nobobutton.NoboButton btnFoget;
    com.ornach.nobobutton.NoboButton btnSignup;
    com.github.ybq.android.spinkit.SpinKitView spinKitView;
    EditText txt_account;
    EditText txt_password;
    TextView txt_or;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        spinKitView = findViewById(R.id.spin_kit);
        spinKitView.setVisibility(View.INVISIBLE);


txt_or = findViewById(R.id.txt_or);
btnFoget = findViewById(R.id.btnforget);
btnLogin = findViewById(R.id.btnLogin);
btnSignup = findViewById(R.id.btnSignUp);
txt_account = findViewById(R.id.txt_account);
txt_password = findViewById(R.id.txt_password);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SMSActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HideButton(View.INVISIBLE);
                spinKitView.setVisibility(View.VISIBLE);
                sendPost();
            }
        });
    }



    public void HideButton(int k)
    {
        txt_or.setVisibility(k);
        btnFoget.setVisibility(k);
        btnLogin.setVisibility(k);
        btnSignup.setVisibility(k);
    }


    public void sendPost(){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("UserAccount", txt_account.getText().toString());
            jsonObject.put("HashPassWord", txt_password.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(AppConfig.GLOBAL_URL +"public/api/user/loginaccount")
                .addHeaders("content-type","application/json")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1"))
                        {
                            Toast.makeText(LoginActivity.this,"Login successfully",Toast.LENGTH_SHORT).show();
                            UserInformation.userAccount = txt_account.getText().toString();
                            SharedPreferences preferences = getSharedPreferences(AppConfig.namePreferences, MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("user",UserInformation.userAccount);
                            editor.commit();
                            finish();
                        }
                        else
                        {
                            HideButton(View.VISIBLE);
                            spinKitView.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,"Login fail!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(LoginActivity.this,"Login fail!" + anError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
