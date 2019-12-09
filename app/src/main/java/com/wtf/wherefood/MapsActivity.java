package com.wtf.wherefood;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import mumayank.com.airlocationlibrary.AirLocation;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wtf.wherefood.Share.AppConfig;
import com.wtf.wherefood.Share.ConstString;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;

public class MapsActivity extends FragmentActivity{

    private GoogleMap mMap;
    private AirLocation airLocation;
    LatLng FOODLOCATION = new LatLng(-33.88, 151.21);
    String mName = "";
    Location mLocation;
    LatLng MYLOCATION = new LatLng(-1, -1);
    String firstlinkpic = "";
    LatLng CenterLocation;
    String RestaurantAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                    }
                });

        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                    }
                });

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            Double lat = extras.getDouble("lat");
            Double mlong = extras.getDouble("long");
            mName = extras.getString("name");
            FOODLOCATION = new LatLng(lat, mlong);
            firstlinkpic = extras.getString("link");
            RestaurantAddress = extras.getString("RestaurantAddress");
        }

        ImageView FirstThumbnail = findViewById(R.id.img_map_thum);
        Picasso.get().load(AppConfig.GLOBAL_URL+firstlinkpic).into(FirstThumbnail);
        TextView txtAdd = findViewById(R.id.txt_location);
        txtAdd.setText(RestaurantAddress);
        TextView txtName = findViewById(R.id.txt_map_foodname);
        txtName.setText(mName);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        airLocation = new AirLocation(this, true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(@NotNull Location location) {
                mLocation = location;
                //Toast.makeText(MapsActivity.this, "My location " + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mMap = googleMap;

                        MYLOCATION = new LatLng(location.getLatitude(), location.getLongitude());

                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_food_location);
                        mMap.getUiSettings().setAllGesturesEnabled(true);
                        CenterLocation = new LatLng((MYLOCATION.latitude + FOODLOCATION.latitude)/2, (MYLOCATION.longitude + FOODLOCATION.longitude)/2);

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(CenterLocation).zoom(15.0f).build();


                        BitmapDescriptor icon2 = BitmapDescriptorFactory.fromResource(R.drawable.ic_pin);


                        MarkerOptions markerOptions1 = new MarkerOptions().position(FOODLOCATION)
                                .title(mName).icon(icon);
                        MarkerOptions markerOptions2 = new MarkerOptions().position(MYLOCATION).title("You are here").icon(icon2);




                        mMap.addMarker(markerOptions2);
                        mMap.addMarker(markerOptions1);

                     //   LatLng origin = new LatLng(37.7849569, -122.4068855);
                        String serverKey = ConstString.GG_API_KEY;//"AIzaSyAnhGXHdbrHMqtLaFKKh_jyWnCoFTad_0c";
//                        LatLng origin = new LatLng(37.7849569, -122.4068855);
//                        LatLng destination = new LatLng(37.7814432, -122.4460177);
//                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        GoogleDirection.withServerKey(serverKey)
                                .from(MYLOCATION)
                                .to(FOODLOCATION)
                                .transportMode(TransportMode.DRIVING)
                                .execute(new DirectionCallback() {
                                             @Override
                                             public void onDirectionSuccess(Direction direction) {
                                                            if(direction.isOK())
                                                            {
                                                                Route route = direction.getRouteList().get(0);
                                                                Leg leg = route.getLegList().get(0);
                                                                Info distanceInfo = leg.getDistance();
                                                                Info durationInfo = leg.getDuration();
                                                                String distance = distanceInfo.getText();
                                                                String duration = durationInfo.getText();
                                                                // Toast.makeText(MapsActivity.this,"distance " +distance + " duration "+duration,Toast.LENGTH_SHORT).show();

                                                                TextView tvds = findViewById(R.id.txtdistance);
                                                                TextView tvdur = findViewById(R.id.txtduration);
                                                                tvds.setText(distance);
                                                                tvdur.setText(duration);

                                                                List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                                                                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(MapsActivity.this, stepList, 5, Color.RED, 3, Color.BLUE);
                                                                for (PolylineOptions polylineOption : polylineOptionList) {
                                                                    mMap.addPolyline(polylineOption);
                                                                }
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(MapsActivity.this,"Cannot find the way form your current location to restaurant" +direction.getStatus(),Toast.LENGTH_SHORT).show();
                                                            }
                                             }

                                             @Override
                                             public void onDirectionFailure(Throwable t) {
                                                 Toast.makeText(MapsActivity.this,"Cannot find the way form your current location to restaurant",Toast.LENGTH_SHORT).show();
                                             }
                                         });


                                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                                        mMap.moveCamera(cameraUpdate);

                    }
                });
                //Toast.makeText(FoodAcivity.this,"MY LOCATION:" + mtlocation.getLatitude() + " - "+ mtlocation.getLongitude(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailed(@NotNull AirLocation.LocationFailedEnum locationFailedEnum) {
                // do something
            }
        });


    }


    // override and call airLocation object's method by the same name
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);
    }

    // override and call airLocation object's method by the same name
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


}
