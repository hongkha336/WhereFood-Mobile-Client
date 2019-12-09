package com.wtf.wherefood.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wtf.wherefood.Adapter.CommentAdapter;
import com.wtf.wherefood.FoodAcivity;
import com.wtf.wherefood.HelperClass.CommentHelper;
import com.wtf.wherefood.Model.Comment;
import com.wtf.wherefood.Model.MetaComment;
import com.wtf.wherefood.R;
import com.wtf.wherefood.Share.ConstString;
import com.wtf.wherefood.ViewPictureActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class CommentFragment extends Fragment {


    List<Comment> myListComment;
    Context conn;
        public  CommentFragment(Context con, List<Comment> listData)
    {
        conn = con;
        this.myListComment = listData;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if ( myListComment.size() >0 ) {
            View convertView = inflater.inflate(R.layout.fragment_comment, null);

            ListView lv = convertView.findViewById(R.id.lv_comments);
            CommentAdapter commentAdapter = new CommentAdapter(conn, myListComment);
            lv.setAdapter(commentAdapter);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                    int count =  myListComment.get(position).getPicturelink().size();
                    if(count != 0) {
                        Intent i = new Intent(conn, ViewPictureActivity.class);
                        i.putExtra("count", count);
                        for (int j = 0; j < count; j++) {
                            i.putExtra("pic_" + j, myListComment.get(position).getPicturelink().get(j));
                        }

                        i.putExtra("cap", myListComment.get(position).getContent());
                        startActivity(i);
                    }

                }
            });
            return convertView;
        } else {
            View convertView = inflater.inflate(R.layout.no_content_alert, null);
            TextView textView =  convertView.findViewById(R.id.txt_no_contents);
            textView.setText(ConstString.NO_CONTENT_STRING);
            return  convertView;

        }
    }
}
