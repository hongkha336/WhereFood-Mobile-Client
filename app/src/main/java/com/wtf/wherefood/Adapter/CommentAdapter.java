package com.wtf.wherefood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wtf.wherefood.Model.Comment;
import com.wtf.wherefood.Model.Food;
import com.wtf.wherefood.R;
import com.wtf.wherefood.Share.AppConfig;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private List<Comment> listData;
    private Context context;
    public CommentAdapter(Context aContext,  List<Comment> listData) {
        this.context = aContext;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.comment,null);

        TextView tv_userID = convertView.findViewById(R.id.txt_cm_user_account);
        TextView tv_content =convertView.findViewById(R.id.txt_cm_content);

        tv_content.setText(listData.get(p).getContent());
        tv_userID.setText(listData.get(p).getUserAccount());


        LinearLayout linearLayout = convertView.findViewById(R.id.layout_comment_picture);
//        Picasso.get().load(AppConfig.GLOBAL_URL+listData.get(position).getFirstPermarkLink()).into(FirstThumbnail);
        for(String link : listData.get(p).getPicturelink())  {
            View view = inflater.inflate(R.layout.comment_item_picture, null);
            ImageView img = view.findViewById(R.id.img_comments);
            Picasso.get().load(AppConfig.GLOBAL_URL+link).into(img);
            linearLayout.addView(view);
        }


        LinearLayout sur_leyout = convertView.findViewById(R.id.layout_cmt_survey);


        Double d_star = Double.valueOf(listData.get(p).getSurveyPoint());


        int i_start = d_star.intValue();
        for(int i = 0; i <i_start; i++) {
            View starView = inflater.inflate(R.layout.star_survey, null);
            sur_leyout.addView(starView);
        }

        Double p_star = d_star -i_start;
        if(p_star >= 0.65)
        {
            View starView = inflater.inflate(R.layout.star_survey, null);
            sur_leyout.addView(starView);
            i_start ++;
        }
        else {
            if (p_star >= 0.25) {
                View starView = inflater.inflate(R.layout.haft_star_survey, null);
                sur_leyout.addView(starView);
                i_start++;
            }
        }
        int grey_star = 5 - i_start;

        for(int i = 0; i <grey_star; i++) {
            View starView = inflater.inflate(R.layout.star_grey_survey, null);
            sur_leyout.addView(starView);
        }

        TextView tv_date = convertView.findViewById(R.id.txt_cmt_datetime);
        Double d_date = listData.get(p).getDatetime_comment();
        String datetime = String.valueOf(d_date.intValue());
        tv_date.setText(ConvertMilliSecondsToFormattedDate(datetime));

        return convertView;
    }

    public  String dateFormat = "dd/MM/YYYY hh:mm";
    private  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);


    public  String ConvertMilliSecondsToFormattedDate(String milliSeconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliSeconds));
        return simpleDateFormat.format(calendar.getTime());
    }
}
