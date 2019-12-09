package com.wtf.wherefood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import mumayank.com.airlocationlibrary.AirLocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.readystatesoftware.viewbadger.BadgeView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wtf.wherefood.Adapter.CustomFoodAdapter;
import com.wtf.wherefood.Adapter.FoodPageApdapter;
import com.wtf.wherefood.Adapter.SliderAdapter;
import com.wtf.wherefood.Handler.HttpHandler;
import com.wtf.wherefood.HelperClass.CommentHelper;
import com.wtf.wherefood.Model.Comment;
import com.wtf.wherefood.Model.Food;
import com.wtf.wherefood.Model.MetaComment;
import com.wtf.wherefood.Share.AppConfig;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodAcivity extends AppCompatActivity {

    public List<String> listLinkPicture;
    private  String FoodID;
    private String FoodName;
    private Double AvgSurvey;
    private Integer Prices;
    private String LongDescription;
    private String ShortDescription;
    Location mtlocation = null;
    private AirLocation airLocation;
    Double longti;
    Double lati;
    public  List<MetaComment> listComt;
    public  String firstLink;
    public  String RestaurantAddress;
    int tabindex =0;

    public BottomSheetBehavior bottomSheetBehavior;

    public  Food myFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
      //  getSupportActionBar().hide();
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();

        Bundle extras = getIntent().getExtras();
        listComt = new ArrayList<>();


        if(extras !=null)
        {
            FoodID = extras.getString("FoodID");
            FoodName = extras.getString("FoodName");
            AvgSurvey = extras.getDouble("AvgSurvey");
            Prices= extras.getInt("Prices");
            LongDescription = extras.getString("LongDescription");
            ShortDescription =extras.getString("ShortDescription");
            longti = extras.getDouble("long");
            lati = extras.getDouble("lat");
            firstLink = extras.getString("link");
            RestaurantAddress = String.valueOf(extras.getString("RestaurantAddress"));

            myFood = new Food();
            myFood.setFoodID(FoodID);
            myFood.setFoodName(FoodName);
            myFood.setAvgSurvey(AvgSurvey);
            myFood.setPrices(Prices);
            myFood.setLongDescriptioon(LongDescription);
            myFood.setShortDescription(ShortDescription);
            myFood.setLong(longti);
            myFood.setLat(lati);
            myFood.setFirstPermarkLink(firstLink);
            myFood.setRestaurantAddress(RestaurantAddress);

        }
//        TextView m_RestaurantAddress = findViewById(R.id.txt_address);
//        m_RestaurantAddress.setText(RestaurantAddress);
//        TextView txtFoodName = findViewById(R.id.txt_info_name);
//        TextView txtCost = findViewById(R.id.txt_info_cost);
//        TextView txtDesc = findViewById(R.id.txtinfo_long_des);
//        txtFoodName.setText(FoodName);
//        txtCost.setText(String.valueOf(Prices) + " VNĐ");
//        txtDesc.setText(LongDescription);
//        LinearLayout layout = findViewById(R.id.layout_info_sur);
//        Double d_star = AvgSurvey;
//        int i_start = d_star.intValue();
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        for(int i = 0; i <i_start; i++) {
//            View starView = inflater.inflate(R.layout.star_survey, null);
//            layout.addView(starView);
//        }
//        Double p_star = d_star -i_start;
//
//        if(p_star >= 0.65)
//        {
//            View starView = inflater.inflate(R.layout.star_survey, null);
//            layout.addView(starView);
//            i_start ++;
//        }
//        else {
//            if (p_star >= 0.25) {
//                View starView = inflater.inflate(R.layout.haft_star_survey, null);
//                layout.addView(starView);
//                i_start++;
//            }
//        }
//
//        int grey_star = 5 - i_start;
//        for(int i = 0; i <grey_star; i++) {
//            View starView = inflater.inflate(R.layout.star_grey_survey, null);
//            layout.addView(starView);
//        }
//
//        listLinkPicture = new ArrayList<>();
//        new FoodAcivity.GetPicture(FoodID).execute();




        LinearLayout linearLayout = findViewById(R.id.layout_tab_food);
//        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);

        //////////////////========/////
        viewPager = (ViewPager) linearLayout.findViewById(R.id.viewpaper);
        tabLayout = (TabLayout) linearLayout.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.food_ic));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.heart));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_location));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_comments));

        //,

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final FoodPageApdapter adapter = new FoodPageApdapter(this,getSupportFragmentManager(), tabLayout.getTabCount(), myFood, null);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               tabindex = tab.getPosition();
               viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//                try
//                {
//                    Thread.sleep(1000);
//                }
//                catch(InterruptedException ex)
//                {
//                    Thread.currentThread().interrupt();
//                }

//                new GetComments(FoodID).execute();
                //tabLayout.

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        new GetComments(FoodID).execute();
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                    } else {
                        // Oups permission denied
                    }
                });

        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                    } else {
                        // Oups permission denied
                    }
                });






    }

    private TabLayout tabLayout;
    private ViewPager viewPager;
//    // override and call airLocation object's method by the same name
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        airLocation.onActivityResult(requestCode, resultCode, data);
//    }
//
//    // override and call airLocation object's method by the same name
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//    }

    private class GetPicture extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        private String API = "public/api/permalink/getpermalinkbyid/";
        private String SEARCH_KEY = "";
        public  GetPicture(String ID)
        {

            SEARCH_KEY = ID;
        }

        String TAG = "JSON_DOWNLOAD";

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //https://testserver.22domain.com/public/api/food/getfood/ca
            String url = AppConfig.GLOBAL_URL + API + SEARCH_KEY;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray foodsArray = new JSONArray(jsonStr);
                    // looping through All FoodsArray
                    for (int i = 0; i < foodsArray.length(); i++) {
                        JSONObject c = foodsArray.getJSONObject(i);
                        String PicturePermalink = c.getString("PicturePermalink");
                        //Add to list Food
                        listLinkPicture.add(PicturePermalink);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Check your connection");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get data from server. Check your connection!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            SliderView sliderView = findViewById(R.id.imageSlider);

            SliderAdapter adapter = new SliderAdapter( FoodAcivity.this,listLinkPicture,"");
            adapter.setCount(listLinkPicture.size());
            sliderView.setSliderAdapter(adapter);

            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderView.setIndicatorSelectedColor(Color.WHITE);
            sliderView.setIndicatorUnselectedColor(Color.GRAY);


        }


    }

    private class GetComments extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Toast.makeText(FoodAcivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
            listComt = new ArrayList<>();
        }


        private String API = "public/api/foodcomment/getcommentcontentandpicturebyfoodidjointable/";
        private String SEARCH_KEY = "";

        public GetComments(String text) {

            SEARCH_KEY = text;
        }

        String TAG = "JSON_DOWNLOAD";

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url = AppConfig.GLOBAL_URL + API + SEARCH_KEY;
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONArray foodsArray = new JSONArray(jsonStr);
                    // looping through All FoodsArray
                    for (int i = 0; i < foodsArray.length(); i++) {
                        JSONObject c = foodsArray.getJSONObject(i);
                        int CommentID = c.getInt("CommentID");
                        String UserAccount = c.getString("UserAccount");
                        String CommentContent = c.getString("CommentContent");
                        String PicturePermalink = c.getString("PicturePermalink");
                        int survey_point = c.getInt("SurveyPoint");
                        Double datetime_comment = c.getDouble("datetime_comment");
                        //Create Food Object
                        MetaComment cmt = new MetaComment(CommentID,UserAccount,CommentContent,PicturePermalink);
                        cmt.setSurveyPoint(survey_point);
                        cmt.setDatetime_comment(datetime_comment);

                        listComt.add(cmt);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Check your connection");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get data from server. Check your connection!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            List<Comment> cmts = new ArrayList<>();
            cmts = CommentHelper.convertMetaCommentToComment(listComt);

            if(cmts.size() != 0) {
                BadgeView badge = new BadgeView(FoodAcivity.this, tabLayout);
                badge.setText(String.valueOf(cmts.size()));
                badge.show();
            }


            FoodPageApdapter adapter = new FoodPageApdapter(FoodAcivity.this,getSupportFragmentManager(), tabLayout.getTabCount(), myFood, cmts);
            viewPager.setAdapter(adapter);
            TabLayout.Tab tab = tabLayout.getTabAt(tabindex);
            tab.select();
            viewPager.setCurrentItem(tab.getPosition());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuinfood, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout: {
                //logout code
                return true;

            }
            case R.id.addFood: {
                startActivity(new Intent(FoodAcivity.this,AddFoodActivity.class));
                return true ;
            }


            case R.id.report: {
                startActivity(new Intent(FoodAcivity.this,ReportActivity.class));
                return true ;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}