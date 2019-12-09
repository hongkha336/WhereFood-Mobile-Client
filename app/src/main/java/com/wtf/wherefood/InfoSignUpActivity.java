package com.wtf.wherefood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.wtf.wherefood.Model.CommentSurvey;
import com.wtf.wherefood.Share.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class InfoSignUpActivity extends AppCompatActivity {

    com.ornach.nobobutton.NoboButton btnsign;
    com.github.ybq.android.spinkit.SpinKitView spinKitView;
    EditText edt_account;
    EditText edit_p1;
    EditText edit_p2;
    String phone;
    TextView textView_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sign_up);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            phone = extras.getString("phone");
        }

        btnsign = findViewById(R.id.btn_infor_signup);
        spinKitView = findViewById(R.id.spin_kit);
        spinKitView.setVisibility(View.INVISIBLE);
        edt_account = findViewById(R.id.edit_info_account);
        edit_p1 = findViewById(R.id.edit_info_p1);
        edit_p2 = findViewById(R.id.edit_info_p2);
        textView_status = findViewById(R.id.txt_info_status);
        textView_status.setVisibility(View.INVISIBLE);

        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit_p1.getText().toString().equals(edit_p2.getText().toString()))
                {
                    textView_status.setVisibility(View.VISIBLE);
                    textView_status.setText("Confirm password error");
                }
                else
                {
                    if(edit_p1.getText().toString().length() <= 6)
                    {
                        textView_status.setVisibility(View.VISIBLE);
                        textView_status.setText("Password length must be more than 6");
                    }
                    else
                    {
                        if(edt_account.getText().toString().length() <=6)
                        {
                            textView_status.setVisibility(View.VISIBLE);
                            textView_status.setText("Password length must be more than 6");
                        }
                        else
                        {
                            textView_status.setVisibility(View.INVISIBLE);
                            btnsign.setVisibility(View.INVISIBLE);
                            spinKitView.setVisibility(View.VISIBLE);
                            sendPost();
                        }
                    }
                }
            }
        });





    }


    public void sendPost(){
        JSONObject jsonObject = new JSONObject();
        try {
            Calendar calendar = Calendar.getInstance();
            //Returns current time in millis
            long timeMilli2 = calendar.getTimeInMillis();
            jsonObject.put("UserAccount", edt_account.getText().toString());
            jsonObject.put("HashPassWord", edit_p1.getText().toString());
            jsonObject.put("RegisteredTime", timeMilli2);
            jsonObject.put("FullName", edt_account.getText().toString());
            jsonObject.put("DateOfBirth",timeMilli2);
            jsonObject.put("PhoneNumber", phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(AppConfig.GLOBAL_URL +"public/api/user/registeraccount")
                .addHeaders("content-type","application/json")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("-5"))
                        {
                            textView_status.setVisibility(View.VISIBLE);
                            btnsign.setVisibility(View.VISIBLE);
                            textView_status.setText("Your account is not available");
                            spinKitView.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            textView_status.setVisibility(View.VISIBLE);
                            textView_status.setText("Sign up successfully");
                            Toast.makeText(InfoSignUpActivity.this, "Sign up successfully",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        textView_status.setVisibility(View.VISIBLE);
                        btnsign.setVisibility(View.VISIBLE);
                        textView_status.setText("Sign up error");
                        spinKitView.setVisibility(View.INVISIBLE);
                    }
                });

    }
}
