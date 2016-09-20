package com.ericgonzalesevans.reeli.data.api;

public interface TmdbApi {
    public static final String ENDPOINT_MOVIES_BOXOFFICE = "http://api.themoviedb.org/3/movie/now_playing";
    public static final String ENDPOINT_MOVIES_UPCOMING = "http://api.themoviedb.org/3/movie/upcoming";

    public static final String BASELINK_IMAGES = "http://image.tmdb.org/t/p/w500";
}
