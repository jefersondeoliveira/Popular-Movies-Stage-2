package com.example.popularmoviesstage2.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Movie implements Serializable {

    @PrimaryKey()
    private Long id;
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("release_date")
    private String release;
    @SerializedName("vote_average")
    private String rate;
    private String overview;

    public Movie(Long id, String title, String poster, String release, String rate, String overview) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.release = release;
        this.rate = rate;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getRelease() {
        return release;
    }

    public String getRate() {
        return rate;
    }

    public String getOverview() {
        return overview;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
