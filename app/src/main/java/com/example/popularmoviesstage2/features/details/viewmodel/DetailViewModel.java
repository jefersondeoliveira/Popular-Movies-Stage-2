package com.example.popularmoviesstage2.features.details.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;

import static com.example.popularmoviesstage2.features.details.activity.DetailActivity.MOVIE_KEY;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<Movie> movie = new MutableLiveData<>();

    private MoviesRepository mMoviesRepository;

    DetailViewModel(MoviesRepository moviesRepository) {
        this.mMoviesRepository = moviesRepository;
    }

    public void setMovieFromExtras(Intent intent){
        if(intent != null && intent.hasExtra(MOVIE_KEY))
            movie.postValue( (Movie) intent.getSerializableExtra(MOVIE_KEY));
    }

    public MutableLiveData<Movie> getMovie() {
        return movie;
    }

    public void saveMovie(Movie movie){
        mMoviesRepository.saveMovie(movie);
    }

    public void deleteMovie(Movie movie){
        mMoviesRepository.deleteMovie(movie);
    }

}
