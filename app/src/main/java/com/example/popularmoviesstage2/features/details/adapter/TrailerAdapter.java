package com.example.popularmoviesstage2.features.details.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmoviesstage2.R;
import com.example.popularmoviesstage2.data.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MovieAdapterViewHolder> {

    private List<Trailer> mTrailerData = new ArrayList<>();
    private final AdapterOnClickHandler mClickHandler;
    private Context mContext;

    public TrailerAdapter(AdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mContext = context;
    }

    public void updateData(List<Trailer> trailers){
        mTrailerData = trailers;
        notifyDataSetChanged();
    }

    public interface AdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    private Trailer getTrailer(int position){
        return mTrailerData.get(position);
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_trailer, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {

        Trailer item = mTrailerData.get(position);

        Picasso.get()
                .load(String.format(mContext.getString(R.string.youtube_img_url), item.getKey()))
                .placeholder(R.drawable.placeholder)
                .into(holder.mTrailerImageView);
    }

    @Override
    public int getItemCount() {
        return mTrailerData.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mTrailerImageView;

        private MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mTrailerImageView = itemView.findViewById(R.id.ivImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(getTrailer(adapterPosition));
        }
    }
}
