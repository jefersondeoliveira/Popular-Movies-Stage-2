package com.example.popularmoviesstage2.features.main.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.popularmoviesstage2.R;
import com.example.popularmoviesstage2.data.model.Movie;
import com.example.popularmoviesstage2.data.source.repository.MovieRepositoryImpl;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;
import com.example.popularmoviesstage2.features.main.adapter.MovieAdapter;
import com.example.popularmoviesstage2.features.main.viewmodel.MainViewModel;
import com.example.popularmoviesstage2.features.main.viewmodel.MainViewModelFactory;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRvMovies;
    private MovieAdapter adapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesRepository repository = new MovieRepositoryImpl();
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        viewModel.getMoviesObservable("popular");

        bindViews();
        setAdapter();
        observableBinds();
    }

    private void bindViews(){
        mRvMovies = findViewById(R.id.rvMovies);
    }

    private void setAdapter(){
        adapter = new MovieAdapter(movie -> {
//                Intent intentToStartDetailActivity = new Intent(MainActivity.this, DetailActivity.class);
//                intentToStartDetailActivity.putExtra("movie", movie);
//                startActivity(intentToStartDetailActivity);
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRvMovies.setLayoutManager(layoutManager);
        mRvMovies.setHasFixedSize(true);
        mRvMovies.setAdapter(adapter);
    }

    private void observableBinds(){
        viewModel.getShowLoad().observe(this, showLoad -> {

        });
        viewModel.getMoviesLive().observe(this, movies -> {
            adapter.updateData(movies);
        });
        viewModel.getOnError().observe(this, throwable -> {

        });
    }

    @Override
    public void onClick(Movie movie) {

    }
}
