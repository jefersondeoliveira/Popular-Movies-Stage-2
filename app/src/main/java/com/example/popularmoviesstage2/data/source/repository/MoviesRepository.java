package com.example.popularmoviesstage2.data.source.repository;

import android.arch.lifecycle.LiveData;

import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.model.MovieResponse;
import com.example.popularmoviesstage2.data.model.ReviewResponse;
import com.example.popularmoviesstage2.data.model.TrailerResponse;

import java.util.List;

import io.reactivex.Single;

public interface MoviesRepository {

    Single<MovieResponse> getMoviesBySort(String sort);

    Single<TrailerResponse> getMoviesTrailerById(Long id);

    Single<ReviewResponse> getMoviesReviewsById(Long id);

    LiveData<List<Movie>> getAllMovies();

    void saveMovie(Movie movie);

    void deleteMovie(Movie movie);

    LiveData<Movie> getMovieById(Long id);

}
