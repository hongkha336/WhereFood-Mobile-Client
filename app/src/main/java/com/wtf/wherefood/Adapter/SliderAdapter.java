package com.wtf.wherefood.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.wtf.wherefood.R;
import com.wtf.wherefood.Share.AppConfig;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {
    private Context context;
    private List<String>  linkPer;
    private int mCount;
    String caption ="";
    public SliderAdapter(Context context, List<String> linkPer, String caption) {
        this.context = context;
        this.linkPer = linkPer;
        this.caption = caption;
    }
    public void setCount(int count) {
        this.mCount = count;
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        Picasso.get()
                .load(AppConfig.GLOBAL_URL+linkPer.get(position))
                .into(viewHolder.imageViewBackground);
        //viewHolder.textViewDescription.setText(caption);

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);

            imageViewBackground.setAdjustViewBounds(true);
            imageViewBackground.setScaleType(ImageView.ScaleType.FIT_CENTER);
            /*
            android:adjustViewBounds="true"
        android:scaleType="centerCrop"
             */
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);

            this.itemView = itemView;
        }
    }
}

