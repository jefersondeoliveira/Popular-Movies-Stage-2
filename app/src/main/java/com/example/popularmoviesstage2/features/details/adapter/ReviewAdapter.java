package com.example.popularmoviesstage2.features.details.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmoviesstage2.R;
import com.example.popularmoviesstage2.data.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MovieAdapterViewHolder> {

    private List<Review> mReviewData = new ArrayList<>();

    public void updateData(List<Review> reviews){
        mReviewData = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_review, viewGroup, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {

        Review item = mReviewData.get(position);
        holder.mReview.setText(item.getContent());
        holder.mAuthor.setText(item.getAuthor());

    }

    @Override
    public int getItemCount() {
        return mReviewData.size();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView mReview;
        private final TextView mAuthor;

        private MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mReview = itemView.findViewById(R.id.tvReview);
            mAuthor = itemView.findViewById(R.id.tvAuthor);
        }
    }
}
