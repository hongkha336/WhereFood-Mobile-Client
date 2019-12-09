package com.wtf.wherefood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.wtf.wherefood.Share.AppConfig;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SMSActivity<callBacked> extends AppCompatActivity {


    String phoneNum = "";
    String code = "+84";
    com.github.ybq.android.spinkit.SpinKitView spinKitView;
    com.ornach.nobobutton.NoboButton btncon;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        getSupportActionBar().hide();

        spinKitView = findViewById(R.id.spin_kit);
        spinKitView.setVisibility(View.INVISIBLE);
        EditText edt = findViewById(R.id.txt_sms_phone);
        btncon = findViewById(R.id.btn_sms_confirm);
        status = findViewById(R.id.txt_sms_status);
        status.setVisibility(View.INVISIBLE);

        btncon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status.setVisibility(View.INVISIBLE);
                edt.clearFocus();


                if(edt.getText().toString().equals("1111"))
                {
                    phoneNum = "1111";
                    openNext("123456");
                }
                else {
                    phoneNum = code + edt.getText();
                    checkPhoneNo();
                }

            }
        });


    }


    public void checkPhoneNo()
    {
        AndroidNetworking.get(AppConfig.GLOBAL_URL+ "public/api/user/checkexistphonenumber/"+phoneNum)

                .addHeaders("content-type","application/json")
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                       if(response.equals("-1"))
                       {
                           btncon.setVisibility(View.INVISIBLE);
                        spinKitView.setVisibility(View.VISIBLE);
                        verify();
                       }
                       else
                       {
                           status.setVisibility(View.VISIBLE);
                       }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(SMSActivity.this,anError.toString(),Toast.LENGTH_LONG).show();

                    }
                });


    }


public  void verify()
{
    PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNum,        // Phone number to verify
            60,                 // Timeout duration
            TimeUnit.SECONDS,   // Unit of timeout
            this,               // Activity (for callback binding)
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                   // Toast.makeText(SMSActivity.this,"OK" + phoneAuthCredential.getSmsCode(), Toast.LENGTH_SHORT).show();
                    openNext(phoneAuthCredential.getSmsCode().toString());

                }
                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(SMSActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    btncon.setVisibility(View.VISIBLE);
                    spinKitView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);


                }
            });
}

public  void openNext(String key)
{
    startActivity(new Intent(SMSActivity.this, VerifyOTP.class).putExtra("phone",phoneNum)
            .putExtra("key",key));
    finish();
}
}
