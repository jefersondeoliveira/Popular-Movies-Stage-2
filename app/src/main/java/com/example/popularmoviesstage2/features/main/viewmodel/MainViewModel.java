package com.example.popularmoviesstage2.features.main.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    MutableLiveData<List<Movie>> moviesLive = new MutableLiveData<>();
    MutableLiveData<Boolean> showLoad = new MutableLiveData<>();
    MutableLiveData<Throwable> onError = new MutableLiveData<>();

    private MoviesRepository mMoviesRepository;

    MainViewModel(MoviesRepository moviesRepository) {
        this.mMoviesRepository = moviesRepository;
    }

    @SuppressLint("CheckResult")
    public void getMoviesObservable(String sort) {

        mMoviesRepository.getMoviesBySort(sort).doOnSubscribe(disposable -> {
            showLoad.postValue(true);
        }).subscribe(movies -> {
            showLoad.postValue(false);
            moviesLive.postValue(movies.getResults());
        }, throwable -> {
            showLoad.postValue(false);
            onError.postValue(throwable);
        });

    }

    public MutableLiveData<List<Movie>> getMoviesLive() {
        return moviesLive;
    }

    public MutableLiveData<Boolean> getShowLoad() {
        return showLoad;
    }

    public MutableLiveData<Throwable> getOnError() {
        return onError;
    }
}
