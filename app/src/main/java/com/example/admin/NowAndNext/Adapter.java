package com.example.admin.NowAndNext;

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

    private Context context;
    LayoutInflater inflater;
    List<Channel> channels;
    private PaginationCallBack callback;

    public Adapter(Context ctx, List<Channel> channels, PaginationCallBack callback) {
        this.inflater = LayoutInflater.from(ctx);
        this.channels = channels;
        this.callback = callback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.channelTitle.setText(channels.get(position).getTitle());
        holder.programTitle.setText(channels.get(position).getCurrentProgram().getProgramTitle());
        holder.nextProgramTitle.setText(channels.get(position).getCurrentProgram().getNextProgramTitle());
        Picasso.get().load(channels
                .get(position).getCurrentProgram()
                .getCoverImageURL()).into(holder.CoverImage);


        if (position + 1 == channels.size()) {


            callback.loadNextPage();  // Callback
        }


    }


    @Override
    public int getItemCount() {
        return channels.size();
    }


    public void updateDataSet(List<Channel> newList) {

        if (newList != null) {
            channels = newList;
        }

        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView channelTitle, programTitle, nextProgramTitle;
        ImageView CoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            channelTitle = itemView.findViewById(R.id.channelTitle);
            programTitle = itemView.findViewById(R.id.programTitle);
            nextProgramTitle = itemView.findViewById(R.id.nextProgramTitle);
            CoverImage = itemView.findViewById(R.id.coverImage);
        }
    }


}
