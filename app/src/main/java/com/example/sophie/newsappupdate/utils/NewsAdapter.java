package com.example.sophie.newsappupdate.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sophie.newsappupdate.R;
import com.example.sophie.newsappupdate.data.NewsObject;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsObject> newsList;

    public NewsAdapter(List newsList) {

        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(newsList.get(position));
        NewsObject view = newsList.get(position);

        final String url = view.getUrl();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                v.getContext().startActivity(intent);
            }
        });

        String aut_dt_string = view.getAuthor() + " " + view.getDate();
        holder.title.setText(view.getTitle());
        holder.section.setText(view.getSection());
        holder.author_date.setText(aut_dt_string);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView section;
        public TextView author_date;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.article_title);
            section = itemView.findViewById(R.id.article_section);
            author_date = itemView.findViewById(R.id.article_author_date);
        }
    }

}

