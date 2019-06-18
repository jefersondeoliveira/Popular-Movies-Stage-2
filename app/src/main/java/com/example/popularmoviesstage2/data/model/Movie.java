package com.example.popularmoviesstage2.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    private int id;
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("release_date")
    private String release;
    @SerializedName("vote_average")
    private String rate;
    private String overview;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
