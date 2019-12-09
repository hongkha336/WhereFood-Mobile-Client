package com.wtf.wherefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.wtf.wherefood.Share.AppConfig;
import com.wtf.wherefood.Share.UserInformation;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       Intent intent = new Intent(this, first_searchbar.class);


        SharedPreferences preferences = getSharedPreferences(AppConfig.namePreferences, MODE_PRIVATE);
        String user = preferences.getString("user", "");
        UserInformation.userAccount = user;
        //this bellow intent using for testing
        //Intent intent = new Intent(this, AddFoodActivity.class);
        startActivity(intent);
        finish();
    }
}
