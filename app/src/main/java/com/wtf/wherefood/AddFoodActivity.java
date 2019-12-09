package com.wtf.wherefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.wtf.wherefood.Share.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AppCompatActivity {

    Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        /*
        {
    "RestaurantID"          : "NhaHang",
    "RestaurantAddress" : "Mô tả của nhà hàng",
    "Longitude"             : "106.765809",
    "Latitude"                        : "10.849737",
    "UserAccount"           : "mothaiba",
    "ReportTime"                : "898102800000",
    "FoodID"                    : "MMMMM",
    "FoodName"                        : "Món mới",
    "PictureToken"                : "12",
    "Prices"                    : 50000,
    "ShortDescription"        : "Descriptionádasd",
    "LongDescription"   : "Descriptionqqewqweqweqwe"
}
         */

        getSupportActionBar().hide();
        dropdown = findViewById(R.id.spinner1);
        getAllLocation();
//        String[] items = new String[]{"Location Name 1", "This is the LONGGER NAME OF THE LOCATION This is the LONGGER NAME OF THE LOCATION", "UNLOCATED LOCATION"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        dropdown.setAdapter(adapter);

        com.ornach.nobobutton.NoboButton btnOpenMap = findViewById(R.id.btnOpenInMap);
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddFoodActivity.this,PickLocationActivity.class),12345);
            }
        });
}

public void setAdapter(String[] items)
{
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    dropdown.setAdapter(adapter);
}


    public void getAllLocation(){

        AndroidNetworking.get(AppConfig.GLOBAL_URL +"public/api/restaurant/getallrestaurant")
                .addHeaders("content-type","application/json")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String[] items  = new String[response.length()];
                        for (int i = 0; i < response.length(); i++) {
                            try {
                            JSONObject c = response.getJSONObject(i);
                                String RestaurantID = c.getString("RestaurantID");
                                String RestaurantName = c.getString("RestaurantName");
                                String RestaurantAddress = c.getString("RestaurantAddress");
                                String Longitude = c.getString("Longitude");
                                String Latitude = c.getString("Latitude");
                                items[i] =RestaurantName;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        setAdapter(items);

                    }
                    @Override
                    public void onError(ANError error) {


                    }
                });

    }


}
