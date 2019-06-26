package com.example.popularmoviesstage2.features.details.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.popularmoviesstage2.data.source.repository.MoviesRepository;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

        private MoviesRepository mRepository;

        public DetailViewModelFactory(@NonNull MoviesRepository repository) {
            mRepository = repository;
        }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(mRepository);
    }
}
