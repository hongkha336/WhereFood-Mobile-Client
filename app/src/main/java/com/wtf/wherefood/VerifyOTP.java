package com.wtf.wherefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

public class VerifyOTP extends AppCompatActivity {

    private OtpView otpView;
    private String KEY = "";
    private String phone = "+09876543210";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            KEY  = extras.getString("key");
            phone = extras.getString("phone");
        }

        TextView tv = findViewById(R.id.txt_ver_phone_no);
        tv.setText(phone);


        TextView tv_false = findViewById(R.id.txt_otp_false);
        tv_false.setVisibility(View.INVISIBLE);

        otpView = findViewById(R.id.otp_view);

        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {

                otpView.clearFocus();
                if(!otp.equals(KEY))
                {
                    tv_false.setVisibility(View.VISIBLE);
                    otpView.setText("");
                }
                else
                {
                    //write to temp storage
                    //confirm successfull
                    tv_false.setVisibility(View.VISIBLE);
                    tv_false.setText("Verify successfully");
                    //Toast.makeText(VerifyOTP.this,"Verify success full",Toast.LENGTH_SHORT).show();


                    startActivity(new Intent(VerifyOTP.this,InfoSignUpActivity.class)
                    .putExtra("phone",phone));
                    //Do something
                    finish();

                }

            }
        });


    }
}
