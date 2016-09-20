package com.ericgonzalesevans.movieapp.data.model;

import android.graphics.Bitmap;

public class Movie {
    private int id;
    private Bitmap poster;
    private String poster_path;
    private String overview;
    private String release_date;
    private String title;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Bitmap getPoster() {
        return poster;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    public String getPoster_path(){
        return poster_path;
    }

    public void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    }

    public String getOverview(){
        return overview;
    }

    public void setOverview(String overview){
        this.overview = overview;
    }

    public String getRelease_date(){
        return release_date;
    }

    public void setRelease_date(String release_date){
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
