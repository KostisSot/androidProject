package com.example.project1.ui.directions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.model.FirstAidTopic;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(FirstAidTopic topic);
    }

    private List<FirstAidTopic> topicList;
    private OnItemClickListener listener;

    public TopicAdapter(List<FirstAidTopic> topicList, OnItemClickListener listener) {
        this.topicList = topicList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_topic_card, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        FirstAidTopic topic = topicList.get(position);
        holder.title.setText(topic.getTitle());
        holder.icon.setImageResource(topic.getImageResId());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(topic));
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.topicIcon);
            title = itemView.findViewById(R.id.topicTitle);
        }
    }
}
