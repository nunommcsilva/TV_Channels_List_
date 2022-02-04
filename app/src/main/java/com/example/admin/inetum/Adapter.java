package com.example.admin.inetum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    LayoutInflater inflater;
    List<Channel> channels;

    public Adapter(Context ctx, List<Channel> channels) {
        this.inflater = LayoutInflater.from(ctx);
        this.channels = channels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_list_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.channelTitle.setText(channels.get(position).getTitle());
        holder.programTitle.setText(channels.get(position).getCurrentProgram().getProgramTitle());
        holder.synopsis.setText(channels.get(position).getCurrentProgram().getSynopsis());
        Picasso.get().load(channels
                .get(position).getCurrentProgram()
                .getCoverImageURL()).into(holder.CoverImage);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView channelTitle, programTitle, synopsis;
        ImageView CoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            channelTitle = itemView.findViewById(R.id.channelTitle);
            programTitle = itemView.findViewById(R.id.programTitle);
            synopsis = itemView.findViewById(R.id.synopsis);
            CoverImage = itemView.findViewById(R.id.coverImage);
        }
    }


}
