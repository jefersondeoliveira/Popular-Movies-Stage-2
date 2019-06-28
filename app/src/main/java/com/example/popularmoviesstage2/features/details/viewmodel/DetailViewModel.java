package com.example.popularmoviesstage2.features.details.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.model.Review;
import com.example.popularmoviesstage2.data.model.Trailer;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;

import java.util.List;

import static com.example.popularmoviesstage2.features.details.activity.DetailActivity.MOVIE_KEY;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<Movie> movie = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    private MutableLiveData<Boolean> showTrailerLoad = new MutableLiveData<>();
    private MutableLiveData<Boolean> showReviewLoad = new MutableLiveData<>();

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

    public MutableLiveData<List<Review>> getReviews() {
        return reviews;
    }

    public MutableLiveData<Boolean> getShowTrailerLoad() {
        return showTrailerLoad;
    }

    public MutableLiveData<Boolean> getShowReviewLoad() {
        return showReviewLoad;
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
            showTrailerLoad.postValue(true);
        }).subscribe(response -> {
            showTrailerLoad.postValue(false);
            trailers.postValue(response.getResults());
        }, throwable -> {
            showTrailerLoad.postValue(false);
        });
    }

    @SuppressLint("CheckResult")
    public void getReviewsObservable(Long id) {
        mMoviesRepository.getMoviesReviewsById(id).doOnSubscribe(disposable -> {
            showReviewLoad.postValue(true);
        }).subscribe(response -> {
            showReviewLoad.postValue(false);
            reviews.postValue(response.getResults());
        }, throwable -> {
            showReviewLoad.postValue(false);
        });
    }

}
