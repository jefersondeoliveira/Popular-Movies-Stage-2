package com.example.popularmoviesstage2.data.source.remote;

import com.example.popularmoviesstage2.data.model.MovieResponse;
import com.example.popularmoviesstage2.data.model.ReviewResponse;
import com.example.popularmoviesstage2.data.model.TrailerResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface MovieApiService {

    @GET("3/movie/{sort}")
    Single<MovieResponse> getMoviesBySort(@Path("sort") String sort);

    @GET("3/movie/{id}/videos")
    Single<TrailerResponse> getMoviesTrailerById(@Path("id") Long id);

    @GET("3/movie/{id}/reviews")
    Single<ReviewResponse> getMoviesReviewsById(@Path("id") Long id);

}
