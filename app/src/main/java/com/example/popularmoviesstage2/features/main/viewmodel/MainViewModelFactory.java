package com.example.popularmoviesstage2.features.main.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

        private MoviesRepository mRepository;
        private LifecycleOwner mLifecycleOwner;

        public MainViewModelFactory(@NonNull MoviesRepository repository, @NonNull LifecycleOwner lifecycleOwner) {
            mRepository = repository;
            mLifecycleOwner = lifecycleOwner;
        }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainViewModel(mRepository, mLifecycleOwner);
    }
}
