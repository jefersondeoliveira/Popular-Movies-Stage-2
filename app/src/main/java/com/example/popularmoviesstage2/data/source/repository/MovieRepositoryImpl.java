package com.example.popularmoviesstage2.data.source.repository;

import com.example.popularmoviesstage2.data.model.MovieResponse;
import com.example.popularmoviesstage2.data.source.remote.MovieApiService;
import com.example.popularmoviesstage2.data.source.remote.RetrofitClientInstance;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieRepositoryImpl implements MoviesRepository {

    private MovieApiService mMovieApiService;

    public MovieRepositoryImpl() {
        mMovieApiService = RetrofitClientInstance
                .getRetrofitInstance().create(MovieApiService.class);
    }

    @Override
    public Single<MovieResponse> getMoviesBySort(String sort) {
        return mMovieApiService.getMoviesBySort(sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
