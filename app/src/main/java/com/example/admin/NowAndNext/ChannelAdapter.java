package com.example.admin.NowAndNext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {

    List<Channel> channelList;
    Context context;

    public ChannelAdapter(Context context, List<Channel> channels) {
        this.context = context;
        channelList = channels;
    }

    @NonNull
    @Override
    public ChannelAdapter.ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.custom_list_layout, parent, false);
        return new ChannelAdapter.ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelAdapter.ChannelViewHolder holder, int position) {
        Channel channel = channelList.get(position);
        holder.title.setText("title : " + channel.getTitle());
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    public class ChannelViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.channelTitle);

        }
    }

    //
    //
    //

}

