package com.example.stasenkopavel.searchtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stasenkopavel on 1/25/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<SearchItem> items;
    private Context context;

    public SearchAdapter(ArrayList<SearchItem> items) {
        this.items = items;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.search_item, parent, false);
        SearchViewHolder holder = new SearchViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        SearchItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setEmptyAdapter() {
        this.items = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<SearchItem> newItems, boolean toEnd) {
        if (toEnd)
            this.items.addAll(newItems);
        else
            items.addAll(0, newItems);
        this.notifyDataSetChanged();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleTextView) TextView titleTextView;
        @BindView(R.id.linkTextView) TextView linkTextView;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(SearchItem item) {
            titleTextView.setText(item.getTitle());
            linkTextView.setText(item.getLink());
        }
    }
}
