package com.example.popularmoviesstage2.features.details.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.model.Trailer;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;

import java.util.List;

import static com.example.popularmoviesstage2.features.details.activity.DetailActivity.MOVIE_KEY;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<Movie> movie = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();

    private MoviesRepository mMoviesRepository;

    DetailViewModel(MoviesRepository moviesRepository) {
        this.mMoviesRepository = moviesRepository;
    }

    public void setMovieFromExtras(Intent intent){
        if(intent != null && intent.hasExtra(MOVIE_KEY))
            movie.postValue( (Movie) intent.getSerializableExtra(MOVIE_KEY));
    }

    public LiveData<Movie> getMovieById(Long id){
        return mMoviesRepository.getMovieById(id);
    }

    public MutableLiveData<Movie> getMovieLiveData() {
        return movie;
    }

    public MutableLiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public void saveMovie(Movie movie){
        mMoviesRepository.saveMovie(movie);
    }

    public void deleteMovie(Movie movie){
        mMoviesRepository.deleteMovie(movie);
    }

    @SuppressLint("CheckResult")
    public void getTrailersObservable(Long id) {
        mMoviesRepository.getMoviesTrailerById(id).doOnSubscribe(disposable -> {
            //showLoad.postValue(true);
        }).subscribe(response -> {
            //showLoad.postValue(false);
            trailers.postValue(response.getResults());
        }, throwable -> {
//            showLoad.postValue(false);
//            onError.postValue(throwable);
        });
    }

}
