package com.example.popularmoviesstage2.data.source.remote;

import com.example.popularmoviesstage2.data.model.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface MovieApiService {

    @GET("3/movie/{sort}")
    Single<MovieResponse> getMoviesBySort(@Path("sort") String sort);

}
