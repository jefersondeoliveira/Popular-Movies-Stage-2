package com.example.popularmoviesstage2.features.main.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmoviesstage2.R;
import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.source.repository.MovieRepositoryImpl;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;
import com.example.popularmoviesstage2.features.details.activity.DetailActivity;
import com.example.popularmoviesstage2.features.main.adapter.MovieAdapter;
import com.example.popularmoviesstage2.features.main.viewmodel.MainViewModel;
import com.example.popularmoviesstage2.features.main.viewmodel.MainViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRvMovies;
    private MovieAdapter mAdapter;
    private ProgressBar mLoad;
    private TextView mTvError;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesRepository repository = new MovieRepositoryImpl(getApplication());
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        mViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

        bindViews();
        setAdapter();
        observableBinds();

        loadMovies(SearchType.POPULAR);
    }

    private void loadMovies(SearchType type){
        mViewModel.getMoviesObservable(type.label);
    }

    private void loadFavoriteMovies(){
        mViewModel.getAllFavoriteMovies().observe(this, movies -> showData(movies));
    }

    private void bindViews(){
        mRvMovies = findViewById(R.id.rvMovies);
        mLoad = findViewById(R.id.load);
        mTvError = findViewById(R.id.tvError);
    }

    private void setAdapter(){
        mAdapter = new MovieAdapter(movie -> {
                Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
                intentToStartDetailActivity.putExtra(DetailActivity.MOVIE_KEY, movie);
                startActivity(intentToStartDetailActivity);
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRvMovies.setLayoutManager(layoutManager);
        mRvMovies.setHasFixedSize(true);
        mRvMovies.setAdapter(mAdapter);
    }

    private void observableBinds(){
        mViewModel.getShowLoad().observe(this, this::showLoad);
        mViewModel.getMoviesLive().observe(this, this::showData);
        mViewModel.getOnError().observe(this, throwable -> showError());
    }

    private void showData(List<Movie> movies){
        mRvMovies.setVisibility(View.VISIBLE);
        mAdapter.updateData(movies);
    }

    private void showError(){
        mRvMovies.setVisibility(View.GONE);
        mTvError.setVisibility(View.VISIBLE);
    }

    private void showLoad(Boolean showLoad ){
        mLoad.setVisibility(showLoad != null && showLoad ? View.VISIBLE : View.GONE);
        mTvError.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();

        if (menuItemSelected == R.id.actionPopular) {
            loadMovies(SearchType.POPULAR);
            return true;
        }

        if (menuItemSelected == R.id.actionTopRated) {
            loadMovies(SearchType.TOP);
            return true;
        }

        if (menuItemSelected == R.id.actionFavorites) {
            loadFavoriteMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Movie movie) {

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
