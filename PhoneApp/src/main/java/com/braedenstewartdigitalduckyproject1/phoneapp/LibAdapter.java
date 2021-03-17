package com.braedenstewartdigitalduckyproject1.phoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.braedenstewartdigitalduckyproject1.api.CustomAdapter;
import com.braedenstewartdigitalduckyproject1.api.ThoughtTrain;

public class LibAdapter extends CustomAdapter<ThoughtTrain>{
    protected ObservableArrayList<String> dataIds;

    public LibAdapter(ObservableArrayList<ThoughtTrain> data, ObservableArrayList<String> dataIds){
        super(data);

        this.dataIds = dataIds;
        this.dataIds.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<String>>() {
            @Override
            public void onChanged(ObservableList<String> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<String> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<String> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<String> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<String> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.item_thot_train;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThoughtTrain thotTrain = data.get(position);
        TextView title = holder.getItemView().findViewById(R.id.thot_train_title);
        TextView date = holder.getItemView().findViewById(R.id.thot_pub_date);
        ConstraintLayout con = holder.getItemView().findViewById(R.id.thot_train_con);
        title.setText(thotTrain.getTitle());
        date.setText(thotTrain.getPublishDate().toString());

        con.setOnClickListener(view -> {
            Intent intent = new Intent(holder.getItemView().getContext(), ChatActivity.class);

            Bundle extras = new Bundle();
            extras.putString("TITLE", thotTrain.getTitle());
            extras.putString("ID", dataIds.get(position));

            intent.putExtras(extras);

            holder.getItemView().getContext().startActivity(intent);
        });
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder){
        holder.getItemView().findViewById(R.id.thot_train_con).setOnClickListener(null);
    }
}
