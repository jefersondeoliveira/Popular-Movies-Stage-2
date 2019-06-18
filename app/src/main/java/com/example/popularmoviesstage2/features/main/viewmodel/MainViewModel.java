package com.example.popularmoviesstage2.features.main.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;

import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;

public class MainViewModel extends ViewModel {

    private MoviesRepository mMoviesRepository;

    MainViewModel(MoviesRepository moviesRepository) {
        this.mMoviesRepository = moviesRepository;
    }

    @SuppressLint("CheckResult")
    public void getMoviesObservable(String sort) {

        mMoviesRepository.getMoviesBySort(sort).doOnSubscribe(disposable -> {

            //load

        }).subscribe(movies -> {

           //data

        }, throwable -> {

            // error

        });

    }

}
