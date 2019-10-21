package com.wfteam.wherefood.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.wfteam.wherefood.Holder.SuggestionHolder;
import com.wfteam.wherefood.Model.Product;
import com.wfteam.wherefood.R;

public class CustomSuggestionsAdapter extends SuggestionsAdapter<Product, SuggestionHolder> {

    public CustomSuggestionsAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public void onBindSuggestionHolder(Product suggestion, SuggestionHolder holder, int position) {
        holder.title.setText(suggestion.getTitle());
        holder.subtitle.setText("Giá: " + suggestion.getPrice() + "VNĐ");
    }

    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_custom_suggestion, parent, false);
        return new SuggestionHolder(view);
    }

    @Override
    public int getSingleViewHeight() {
        return 60;
    }

}