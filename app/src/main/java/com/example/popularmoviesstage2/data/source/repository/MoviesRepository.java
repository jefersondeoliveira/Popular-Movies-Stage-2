package com.example.popularmoviesstage2.data.source.repository;

import com.example.popularmoviesstage2.data.model.MovieResponse;

import io.reactivex.Single;

public interface MoviesRepository {

    Single<MovieResponse> getMoviesBySort(String sort);

}
