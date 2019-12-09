package com.wtf.wherefood.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wtf.wherefood.Model.Comment;
import com.wtf.wherefood.Model.CommentSurvey;

import com.wtf.wherefood.Model.Food;
import com.wtf.wherefood.Network.ImageSenderInfo;
import com.wtf.wherefood.Network.NetworkCall;
import com.wtf.wherefood.R;
import com.wtf.wherefood.Share.AppConfig;
import com.wtf.wherefood.Share.UserInformation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import kotlin.jvm.internal.PropertyReference0Impl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyFragment extends Fragment {


    private LinearLayout linear_sur_pic;
    private ImageView imgadd;
    private Context context;
    private String filePath;
    private static final int PICK_PHOTO = 1958;
    private static final int FIX_PICK_PHOTO = 1998;
    private ImageView img_temp = null;
    private View view_temp = null;
    private Food food;
    private int pic_count = 0;
    private int survey_point = 0;
    private TextView lb_add_photo;
    private View del_view_temp = null;
//    public List<PathModel> filePaths;


    public SurveyFragment(Context c , Food food)
    {

        this.context = c;
        this.food = food;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_survey, null);
        EditText txtcmt = convertView.findViewById(R.id.txt_cmt_content);
        com.ornach.nobobutton.NoboButton btn = convertView.findViewById(R.id.btnSubmit);
         imgadd = convertView.findViewById(R.id.btn_add_pic);
         linear_sur_pic = convertView.findViewById(R.id.layout_sur_picture);
         lb_add_photo = convertView.findViewById(R.id.lb_add_photo);
         //filePaths = new ArrayList<>();

        ImageView img1 = convertView.findViewById(R.id.sur_start1);
        ImageView img2 = convertView.findViewById(R.id.sur_start2);
        ImageView img3 = convertView.findViewById(R.id.sur_start3);
        ImageView img4 = convertView.findViewById(R.id.sur_start4);
        ImageView img5 = convertView.findViewById(R.id.sur_start5);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                img3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                img4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                img5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                survey_point =1;
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                img4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                img5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                survey_point =2;
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                img5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                survey_point =3;
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star_grey));
                survey_point =4;
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                img5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.star));
                survey_point =5;
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommentSurvey cmt = new CommentSurvey(food.getFoodID(), UserInformation.userAccount,txtcmt.getText().toString(),survey_point);


                int count = linear_sur_pic.getChildCount()-1;
                for(int index = 0; index < count; index++)
                {
                    View myView = linear_sur_pic.getChildAt(index);
                    ImageView img = myView.findViewById(R.id.pic_sur_cmt);
                    String path_pic = img.getTag().toString();
                Thread thread = new Thread(){
                    public void run(){
                        NetworkCall networkCall = new NetworkCall();
                   networkCall.fileUpload(path_pic,cmt);
                    }
                };
                thread.start();

                }

                sendPost(cmt);

            }
        });




        imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = inflater.inflate(R.layout.add_pic_layout, null);
                view_temp = view;
                pic_count = linear_sur_pic.getChildCount();
                linear_sur_pic.addView(view,pic_count-1);
                if(pic_count == 4)
                {
                    imgadd.setVisibility(View.INVISIBLE);
                }else
                {
                    imgadd.setVisibility(View.VISIBLE);
                }

                addPhoto(v);
            }
        });
        return convertView;
    }




    public void sendPost(CommentSurvey cmt){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("FoodID", cmt.getFoodID());
            jsonObject.put("UserID", cmt.getUserID());
            jsonObject.put("CommentToken", cmt.getCommentToken());
            jsonObject.put("CommentContent", cmt.getCommentContent());
            jsonObject.put("SurveyPoint",cmt.getSurveyPoint());
            jsonObject.put("datetime_comment", cmt.getDatetime_comment());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(AppConfig.GLOBAL_URL +"public/api/foodcomment/insertfoodcomment")
                .addHeaders("content-type","application/json")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                       showResponse("OK");
                    }
                    @Override
                    public void onError(ANError error) {
                        showResponse(String.valueOf(error.getErrorCode()));
                    }
                });

    }

    public void showResponse(String response) {
        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
    }


    public void addPhoto(View view) {
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO);
    }

    public void addPhotoToImg(View v)
    {
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, FIX_PICK_PHOTO);
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK ) {
            if (requestCode == PICK_PHOTO) {
                Uri imageUri = data.getData();
                filePath = getPath(imageUri);

//                filePaths.add(filePath);
//      PathModel pathModel = new PathModel(filePath,view_temp.getId());
//     filePaths.add(pathModel);

                ImageView imageView = view_temp.findViewById(R.id.pic_sur_cmt);
                imageView.setTag(filePath);
                //Uri myUri = imageView.getImage
                imageView.setImageURI(imageUri);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        img_temp = (ImageView) v;
                        addPhotoToImg(v);
                    }
                });

                Button btn_del = view_temp.findViewById(R.id.btn_del_pic);
                btn_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        del_view_temp = (View) v.getParent();
                        //ImageView pic = del_view_temp.findViewById(R.id.pic_sur_cmt);
                        linear_sur_pic.removeView(del_view_temp);



                        imgadd.setVisibility(View.VISIBLE);
                        int count = linear_sur_pic.getChildCount() -1;
                        if(count == 0)
                        {
                            lb_add_photo.setText("Add photo");
                        }
                        else
                        {
                            lb_add_photo.setText("Photo "+count + "/4");
                        }
                        //Toast.makeText(context,"you have just remove " + pic.getTag(), Toast.LENGTH_SHORT).show();
                       // removePathfileByViewId(del_view_temp.getId());
                    }
                });

                int count = linear_sur_pic.getChildCount() -1;
                if(count == 0)
                {
                    lb_add_photo.setText("Add photo");
                }
                else
                {
                    lb_add_photo.setText("Photo "+count + "/4");
                }

                if(count != 4)
                {
                    imgadd.setVisibility(View.VISIBLE);
                }


            }

            if(requestCode == FIX_PICK_PHOTO){
                Uri imageUri = data.getData();
                filePath = getPath(imageUri);
                ImageView imageView = img_temp;
                imageView.setImageURI(imageUri);
            }


        }
        else
        {
        // REMOVE TEMVIEW
            if(requestCode == PICK_PHOTO) {
                if (view_temp != null) {
                    linear_sur_pic.removeView(view_temp);
                    view_temp = null;
                }


                int count = linear_sur_pic.getChildCount() -1;
                if(count == 0)
                {
                    lb_add_photo.setText("Add photo");
                }
                else
                {
                    lb_add_photo.setText("Photo "+count + "/4");
                }
                if(count != 4)
                {
                    imgadd.setVisibility(View.VISIBLE);
                }

            }

        }
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}