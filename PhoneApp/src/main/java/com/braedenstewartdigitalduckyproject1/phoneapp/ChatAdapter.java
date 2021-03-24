package com.braedenstewartdigitalduckyproject1.phoneapp;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.braedenstewartdigitalduckyproject1.api.CustomAdapter;
import com.braedenstewartdigitalduckyproject1.api.Message;

public class ChatAdapter extends CustomAdapter<Message> {
    public ChatAdapter(ObservableArrayList<Message> data){
        super(data);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_message;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = data.get(position);
        TextView author = holder.getItemView().findViewById(R.id.author_tv);
        TextView date = holder.getItemView().findViewById(R.id.date_tv);
        TextView content = holder.getItemView().findViewById(R.id.content_tv);
        author.setText(message.getAuthor());
        date.setText(message.getPublishDate().toString());
        content.setText(message.getContent());
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder){
    }
}
