package com.example.popularmoviesstage2.data.source.remote;

import com.example.popularmoviesstage2.data.model.MovieResponse;
import com.example.popularmoviesstage2.data.model.TrailerResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface MovieApiService {

    @GET("3/movie/{sort}")
    Single<MovieResponse> getMoviesBySort(@Path("sort") String sort);

    @GET("3/movie/{id}/videos")
    Single<TrailerResponse> getMoviesTrailerById(@Path("id") Long id);

    //https://api.themoviedb.org/3/movie/301528/videos?api_key=85a08e715cb64d5e0d9f87daf23a3b60&language=en-US

    //https://api.themoviedb.org/3/movie/301528/reviews?api_key=85a08e715cb64d5e0d9f87daf23a3b60&language=en-US

}
