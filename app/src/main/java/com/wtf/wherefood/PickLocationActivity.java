package com.wtf.wherefood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wtf.wherefood.Model.Restaurant;
import com.wtf.wherefood.Share.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickLocationActivity extends AppCompatActivity {

    SupportMapFragment mSupportMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);
        getSupportActionBar().hide();
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        getAllLocation();


    }


    public void getAllLocation(){
        AndroidNetworking.get(AppConfig.GLOBAL_URL +"public/api/restaurant/getallrestaurant")
                .addHeaders("content-type","application/json")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                       List<Restaurant> ress  = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject c = response.getJSONObject(i);
                                String RestaurantID = c.getString("RestaurantID");
                                String RestaurantName = c.getString("RestaurantName");
                                String RestaurantAddress = c.getString("RestaurantAddress");
                                String Longitude = c.getString("Longitude");
                                String Latitude = c.getString("Latitude");
                                ress.add(new Restaurant(RestaurantID,RestaurantName,RestaurantAddress,Longitude,Latitude));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (mSupportMapFragment != null) {
                            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap googleMap) {
                                    if (googleMap != null) {



                                      //  googleMap.getUiSettings().setAllGesturesEnabled(false);
                                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_food_location);

                                        for(Restaurant r : ress) {
                                            LatLng MYLOCATION = new LatLng(Double.valueOf(r.getLatitude()), Double.valueOf(r.getLongitude()));

                                            CameraPosition cameraPosition = new CameraPosition.Builder().target(MYLOCATION).zoom(15.0f).build();
                                            googleMap.addMarker(new MarkerOptions().position(MYLOCATION)
                                                    .title(r.getRestaurantName()).icon(icon));
                                            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                                            googleMap.moveCamera(cameraUpdate);
                                        }
                                    }

                                }
                            });

                        }


                    }
                    @Override
                    public void onError(ANError error) {


                    }
                });

    }
}
