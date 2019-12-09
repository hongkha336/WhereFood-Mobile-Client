package com.wtf.wherefood;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.wtf.wherefood.Adapter.SliderAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_picture);
        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();
        int count = 0;
        String caption ="";
        List<String> pics = new ArrayList<>();

        if(extras !=null)
        {
            count = extras.getInt("count");
            caption  = extras.getString("cap");
        }

        for(int i = 0; i<count; i++)
        {
            String pic = extras.getString("pic_"+i);
            pics.add(pic);
        }

        SliderView sliderView = findViewById(R.id.imageSlider);


        SliderAdapter adapter = new SliderAdapter( ViewPictureActivity.this,pics,caption);
        adapter.setCount(pics.size());
        sliderView.setSliderAdapter(adapter);

        sliderView.setAutoCycle(false);
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);


    }
}
