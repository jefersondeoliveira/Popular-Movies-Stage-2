package com.example.popularmoviesstage2.data.source.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.model.MovieResponse;
import com.example.popularmoviesstage2.data.model.ReviewResponse;
import com.example.popularmoviesstage2.data.model.TrailerResponse;
import com.example.popularmoviesstage2.data.source.local.AppDatabase;
import com.example.popularmoviesstage2.data.source.remote.MovieApiService;
import com.example.popularmoviesstage2.data.source.remote.RetrofitClientInstance;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieRepositoryImpl implements MoviesRepository {

    private MovieApiService mMovieApiService;
    private AppDatabase mAppDatabase;

    public MovieRepositoryImpl(Application application) {
        mMovieApiService = RetrofitClientInstance
                .getRetrofitInstance().create(MovieApiService.class);
        mAppDatabase = AppDatabase.getDatabase(application);
    }

    @Override
    public Single<MovieResponse> getMoviesBySort(String sort) {
        return mMovieApiService.getMoviesBySort(sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<TrailerResponse> getMoviesTrailerById(Long id) {
        return mMovieApiService.getMoviesTrailerById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<ReviewResponse> getMoviesReviewsById(Long id) {
        return mMovieApiService.getMoviesReviewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public LiveData<List<Movie>> getAllMovies(){
        return mAppDatabase.movieDao().getAllMovies();
    }

    @Override
    public LiveData<Movie> getMovieById(Long id){
        return mAppDatabase.movieDao().getMoviebyId(id);
    }

    @Override
    public void saveMovie(Movie movie) {
        new saveAsyncTask(mAppDatabase).execute(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        new deleteAsyncTask(mAppDatabase).execute(movie);
    }

    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            db.movieDao().deleteMovie(params[0]);
            return null;
        }

    }

    private static class saveAsyncTask extends AsyncTask<Movie, Void, Void> {

        private AppDatabase db;

        saveAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            db.movieDao().addMovie(params[0]);
            return null;
        }

    }

}
