package com.wtf.wherefood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.wtf.wherefood.Share.UserInformation;

public class first_searchbar extends AppCompatActivity {


    private final int LOGIN_CODE =12376;
    TextView textView;
    public  static String str = "Login now!";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_searchbar);
        //getSupportActionBar().hide();




         textView = findViewById(R.id.lb_login);

         if(UserInformation.userAccount.length() > 0)
         {
             textView.setText("Hi, " +UserInformation.userAccount);
         }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.getText().equals(str))
                {
                    startActivityForResult(new Intent(first_searchbar.this, LoginActivity.class),LOGIN_CODE);
                }
            }
        });

        final MaterialSearchBar searchBar = findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                searchBar.clearFocus();
                String keyword = String.valueOf(text);
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtra("keyword",keyword);
                startActivity(i);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_CODE)
        {

            if(UserInformation.userAccount.length() > 0)
            {
                textView.setText("Hi, " +UserInformation.userAccount+"!");
            }
            else {
                textView.setText(str);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final MaterialSearchBar searchBar = findViewById(R.id.searchBar);
        searchBar.setText("");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
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
                startActivity(new Intent(first_searchbar.this,AddFoodActivity.class));
                return true ;
            }


            case R.id.report: {
                startActivity(new Intent(first_searchbar.this,ReportActivity.class));
                return true ;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //rest
}
