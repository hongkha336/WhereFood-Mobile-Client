package com.wtf.wherefood.Fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.wtf.wherefood.FoodAcivity;
import com.wtf.wherefood.MapsActivity;
import com.wtf.wherefood.Model.Food;
import com.wtf.wherefood.R;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import mumayank.com.airlocationlibrary.AirLocation;

public class MappFragment extends Fragment {
    private SupportMapFragment mSupportMapFragment;

Context context;
LatLng MYLOCATION = new LatLng(-33.88,151.21);
String FoodName = "";
String firstLinkpic ="";
String RestaurantAddress;
        public  MappFragment(Context  con, Food myFood)
        {
            MYLOCATION = new LatLng(myFood.getLat(),myFood.getLong());
            this.FoodName = myFood.getFoodName();
            this.context = con;
            this.firstLinkpic = myFood.getFirstPermarkLink();
            this.RestaurantAddress = myFood.getRestaurantAddress();
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_location, null);

       // BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_food_location);

        com.ornach.nobobutton.NoboButton btnBring = convertView.findViewById(R.id.btnBringMe);
        btnBring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MapsActivity.class);
                i.putExtra("lat", MYLOCATION.latitude );
                i.putExtra("long", MYLOCATION.longitude);
                i.putExtra("name", FoodName);
                i.putExtra("link",firstLinkpic);
                i.putExtra("RestaurantAddress",RestaurantAddress);
                startActivity(i);
            }
        });


        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapwhere);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapwhere, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(false);

                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_food_location);
                         CameraPosition cameraPosition = new CameraPosition.Builder().target(MYLOCATION).zoom(15.0f).build();
                        googleMap.addMarker(new MarkerOptions().position(MYLOCATION)
                                .title(FoodName).icon(icon));
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.moveCamera(cameraUpdate);





                    }

                }
            });

        }
        return convertView;
    }


//    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//        // Mode
//        String mode = "mode=" + directionMode;
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + mode;
//        // Output format
//        String output = "json";
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
//        return url;
//    }

}