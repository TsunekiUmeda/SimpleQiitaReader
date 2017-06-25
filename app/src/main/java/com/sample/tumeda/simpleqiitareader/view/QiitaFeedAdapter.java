package com.sample.tumeda.simpleqiitareader.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.tumeda.simpleqiitareader.R;
import com.sample.tumeda.simpleqiitareader.data.rss.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QiitaFeedAdapter
        extends RecyclerView.Adapter<QiitaFeedAdapter.ItemViewHolder> {

    private static final String TAG = QiitaFeedAdapter.class.getName();
    private List<Entry> mItems;
    private final OnFeedItemClickListener onFeedItemClickListener;

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private View rssFeedView;

        ItemViewHolder(View v) {
            super(v);
            rssFeedView = v;
        }
    }

    interface OnFeedItemClickListener {
        void onFeedItemClick(Entry item);
    }

    QiitaFeedAdapter(List<Entry> items, OnFeedItemClickListener onFeedItemClickListener) {
        mItems = items;
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        final Entry item = mItems.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeedItemClickListener.onFeedItemClick(item);
            }
        });
        ((TextView) holder.rssFeedView.findViewById(R.id.titleText)).setText(item.getTitle());
        ((TextView) holder.rssFeedView.findViewById(R.id.author)).setText(item.getAuthor().getName());
        ((TextView) holder.rssFeedView.findViewById(R.id.lastupdate)).setText(formatStringDate(item.getUpdated()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private String formatStringDate(String update) {
        String stringDate = null;
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormatter.parse(update);
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");
            stringDate = format2.format(date);

        } catch (ParseException | NullPointerException e) {
            Log.d(TAG, e.getMessage());
        }
        if (stringDate != null) {
            return stringDate;
        } else {
            return null;
        }
    }

}

