package com.wfteam.wherefood.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wfteam.wherefood.R;

    public class SuggestionHolder extends RecyclerView.ViewHolder{
    public TextView title;
    public TextView subtitle;

    public SuggestionHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);
    }
}

