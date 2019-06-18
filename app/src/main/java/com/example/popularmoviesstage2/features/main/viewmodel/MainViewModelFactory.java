package com.example.popularmoviesstage2.features.main.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

        private MoviesRepository mRepository;

        public MainViewModelFactory(@NonNull MoviesRepository repository) {
            mRepository = repository;
        }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainViewModel(mRepository);
    }
}
