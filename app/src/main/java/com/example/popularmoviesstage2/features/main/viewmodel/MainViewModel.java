package com.example.popularmoviesstage2.features.main.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> moviesLive = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> favoriteMoviesLive = new MutableLiveData<>();
    private MutableLiveData<Boolean> showLoad = new MutableLiveData<>();
    private MutableLiveData<Throwable> onError = new MutableLiveData<>();

    private MoviesRepository mMoviesRepository;
    private LifecycleOwner mLifecycleOwner;

    MainViewModel(MoviesRepository moviesRepository, LifecycleOwner lifecycleOwner) {
        this.mMoviesRepository = moviesRepository;
        this.mLifecycleOwner = lifecycleOwner;
    }

    @SuppressLint("CheckResult")
    public void getMoviesObservable(SearchType sort) {
        mMoviesRepository.getMoviesBySort(sort.label).doOnSubscribe(disposable -> {
            showLoad.postValue(true);
        }).subscribe(movies -> {
            showLoad.postValue(false);
            moviesLive.postValue(movies.getResults());
        }, throwable -> {
            showLoad.postValue(false);
            onError.postValue(throwable);
        });
    }

    public void getFavoriteMoviesObservable(){
        mMoviesRepository.getAllMovies().observe(mLifecycleOwner,
                movies -> favoriteMoviesLive.postValue(movies));
    }

    public MutableLiveData<List<Movie>> getMoviesLive() {
        return moviesLive;
    }

    public MutableLiveData<List<Movie>> getFavoriteMoviesLive() {
        return favoriteMoviesLive;
    }

    public MutableLiveData<Boolean> getShowLoad() {
        return showLoad;
    }

    public MutableLiveData<Throwable> getOnError() {
        return onError;
    }


    public enum SearchType {
        POPULAR("popular"),
        TOP("top_rated"),
        FAVORITES("favorites");

        public final String label;

        SearchType(String label) {
            this.label = label;
        }
    }

}
