package com.example.popularmoviesstage2.features.main.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.popularmoviesstage2.R;
import com.example.popularmoviesstage2.data.source.repository.MovieRepositoryImpl;
import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;
import com.example.popularmoviesstage2.features.main.viewmodel.MainViewModel;
import com.example.popularmoviesstage2.features.main.viewmodel.MainViewModelFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesRepository repository = new MovieRepositoryImpl();
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        MainViewModel viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);

        viewModel.getMoviesObservable("popular");

    }
}
