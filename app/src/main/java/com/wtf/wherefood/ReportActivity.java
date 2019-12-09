package com.wtf.wherefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.android.gms.maps.model.LatLng;
import com.wtf.wherefood.Model.CommentSurvey;
import com.wtf.wherefood.Share.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {

    /**
     * This is JSON INPUT
     * {
     *     "UserAccount"    : "mothaiba",
     *     "FoodID"                 : "BMHTN",
     *     "MetaData2"      : "Description",
     *     "ReportDatetime" : "898102800000"
     * }
     */

    private String UserAccount;
    private String FoodID;
    private String MetaData2;
    private String ReportDatetime;
    EditText edtReport;
    com.github.ybq.android.spinkit.SpinKitView spinKitView;
    com.ornach.nobobutton.NoboButton btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();


        if (extras != null) {

            UserAccount = extras.getString("UserAccount");
            FoodID = extras.getString("FoodID");
        }


//        sendReport();

        spinKitView = findViewById(R.id.spin_kit);
        spinKitView.setVisibility(View.INVISIBLE);
        edtReport = findViewById(R.id.txtReport);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MetaData2 = edtReport.getText().toString();
                Calendar calendar = Calendar.getInstance();
                long timeMilli2 = calendar.getTimeInMillis();
                ReportDatetime = String.valueOf(timeMilli2);

                sendReport();

            }
        });

        TextView tv1 = findViewById(R.id.txtgs1);
        TextView tv2 = findViewById(R.id.txtgs2);
        TextView tv3 = findViewById(R.id.txtgs3);
        TextView tv4 = findViewById(R.id.txtgs4);
        TextView tv5 = findViewById(R.id.txtgs5);
        TextView tv6 = findViewById(R.id.txtgs6);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessTouch(v);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessTouch(v);
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessTouch(v);
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessTouch(v);
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessTouch(v);
            }
        });

        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessTouch(v);
            }
        });

    }


    public void guessTouch(View v)
    {
        TextView tv = (TextView) v;
        edtReport.setText((edtReport.getText() + " " + tv.getText()).trim());
    }

    public void sendReport(){

        btnSubmit.setVisibility(View.INVISIBLE);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserAccount", UserAccount);
            jsonObject.put("FoodID", FoodID);
            jsonObject.put("MetaData2", MetaData2);
            jsonObject.put("ReportDatetime",ReportDatetime);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(AppConfig.GLOBAL_URL +"public/api/userreport/insertuserreport")
                .addHeaders("content-type","application/json")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        btnSubmit.setVisibility(View.VISIBLE);
                        startActivity(new Intent(ReportActivity.this,ThankyouActivity.class));
                        finish();
                    }
                    @Override
                    public void onError(ANError error) {
                        btnSubmit.setVisibility(View.VISIBLE);
                        Toast.makeText(ReportActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

    }

}
