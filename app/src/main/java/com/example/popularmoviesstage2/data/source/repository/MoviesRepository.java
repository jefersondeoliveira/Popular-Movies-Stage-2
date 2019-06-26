package com.example.popularmoviesstage2.data.source.repository;

import android.arch.lifecycle.LiveData;

import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.model.MovieResponse;

import java.util.List;

import io.reactivex.Single;

public interface MoviesRepository {

    Single<MovieResponse> getMoviesBySort(String sort);

    LiveData<List<Movie>> getAllMovies();

    void saveMovie(Movie movie);

    void deleteMovie(Movie movie);

}
