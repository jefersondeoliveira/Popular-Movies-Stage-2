package com.example.popularmoviesstage2.features.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmoviesstage2.R;
import com.example.popularmoviesstage2.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movie> mMovieData = new ArrayList<>();
    private final MovieAdapterOnClickHandler mClickHandler;
    private Context mContext;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mContext = context;
    }

    public void updateData(List<Movie> movies){
        mMovieData = movies;
        notifyDataSetChanged();
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    private Movie getMovie(int position){
        return mMovieData.get(position);
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {

        Movie item = mMovieData.get(position);

        Picasso.get()
                .load(String.format(mContext.getString(R.string.poster_base_url), item.getPoster()))
                .into(holder.mMovieListImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieData.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mMovieListImageView;

        private MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieListImageView = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(getMovie(adapterPosition));
        }
    }
}
