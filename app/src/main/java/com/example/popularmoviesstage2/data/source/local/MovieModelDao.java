package com.example.popularmoviesstage2.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.popularmoviesstage2.data.model.Movie;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieModelDao {

    @Query("select * from Movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("select * from Movie where id = :id")
    LiveData<Movie> getMoviebyId(Long id);

    @Insert(onConflict = REPLACE)
    void addMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

}
