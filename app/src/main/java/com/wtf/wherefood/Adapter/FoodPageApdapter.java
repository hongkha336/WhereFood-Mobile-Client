package com.wtf.wherefood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

import com.wtf.wherefood.Fragments.CommentFragment;
import com.wtf.wherefood.Fragments.FoodFragment;
import com.wtf.wherefood.Fragments.MappFragment;
import com.wtf.wherefood.Fragments.SurveyFragment;
import com.wtf.wherefood.Fragments.Tab1Fragment;
import com.wtf.wherefood.Model.Comment;
import com.wtf.wherefood.Model.Food;
import com.wtf.wherefood.Model.MetaComment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FoodPageApdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    List<Comment> commentsList;

    Food myFood;
    public FoodPageApdapter(Context c, FragmentManager fm, int totalTabs , Food myFood, List<Comment> comm) {

        super(fm);
        this.myFood = myFood;
        context = c;
        this.totalTabs = totalTabs;
        this.commentsList =comm;

    }


    @Override
    public Fragment getItem(int position) {


        switch (position) {

            case 0:
                FoodFragment foofFrag = new FoodFragment(context,myFood);
                return foofFrag;
            case 1:
                SurveyFragment surveyFragment = new SurveyFragment(context,myFood);
                return surveyFragment;
            case 2:
                MappFragment mapfrag = new MappFragment(context,myFood);
                return mapfrag;
            case 3:

                CommentFragment cmtfrag = new CommentFragment(context,commentsList);
                return cmtfrag;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }

}
