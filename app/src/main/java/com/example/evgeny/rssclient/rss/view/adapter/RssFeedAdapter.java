package com.example.evgeny.rssclient.rss.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evgeny.rssclient.R;
import com.example.evgeny.rssclient.rss.api.model.RssItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер списка новостей. Ответает за формирование и обновление ленты.
 */
public class RssFeedAdapter extends RecyclerView.Adapter<RssFeedAdapter.RssFeedHolder> {

    private List<RssItem> rssFeedList = new ArrayList<>();

    @NonNull
    @Override
    public RssFeedAdapter.RssFeedHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View rssItemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.rss_feed_item, parent, false
        );
        final RssFeedHolder rssFeedHolder = new RssFeedHolder(rssItemView);
        rssFeedHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int descriptionVisibility = rssFeedHolder.rssFeedDescription.getVisibility();
                CharSequence description = rssFeedHolder.rssFeedDescription.getText();
                if (description != null && !description.toString().isEmpty()) {
                    rssFeedHolder.rssFeedDescription.setVisibility(
                            descriptionVisibility == View.GONE ? View.VISIBLE : View.GONE
                    );
                }
            }
        });

        return rssFeedHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RssFeedHolder holder, int position) {
        RssItem rssItem = rssFeedList.get(position);

        if (rssItem.link != null) {
            holder.rssFeedSource.setText(rssItem.link);
        } else {
            holder.rssFeedSource.setVisibility(View.GONE);
        }

        if (rssItem.title != null) {
            holder.rssFeedTitle.setText(rssItem.title);
        } else {
            holder.rssFeedTitle.setVisibility(View.GONE);
        }

        if (rssItem.enclosure != null) {
            holder.rssFeedImage.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(rssItem.enclosure.url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.rssFeedImage);
        } else {
            holder.rssFeedImage.setVisibility(View.GONE);
        }

        if (rssItem.description != null) {
            holder.rssFeedDescription.setText(
                    Html.fromHtml(Html.fromHtml(rssItem.description).toString())
            );
        }
    }

    @Override
    public int getItemCount() {
        return rssFeedList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setRssFeedList(List<RssItem> rssFeedList) {
        this.rssFeedList = rssFeedList;
        notifyDataSetChanged();
    }

    public void addRssFeedList(List<RssItem> rssFeedList) {
        int lastPosition = this.rssFeedList.isEmpty() ? 0 : this.rssFeedList.size();
        this.rssFeedList.addAll(rssFeedList);
        notifyItemRangeInserted(lastPosition, rssFeedList.size());
    }

    public static class RssFeedHolder extends RecyclerView.ViewHolder {
        public TextView rssFeedSource;
        public TextView rssFeedTitle;
        public ImageView rssFeedImage;
        public TextView rssFeedDescription;

        public RssFeedHolder(View rssFeedView) {
            super(rssFeedView);
            rssFeedSource = rssFeedView.findViewById(R.id.rss_feed_source);
            rssFeedTitle = rssFeedView.findViewById(R.id.rss_feed_title);
            rssFeedImage = rssFeedView.findViewById(R.id.rss_feed_image);
            rssFeedDescription = rssFeedView.findViewById(R.id.rss_feed_description);
        }
    }
}
