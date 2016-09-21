package com.ericgonzalesevans.reeli.data.api;

import java.text.SimpleDateFormat;

public interface TmdbApi {
    String ENDPOINT_MOVIES_BOXOFFICE = "http://api.themoviedb.org/3/movie/now_playing";
    String ENDPOINT_MOVIES_UPCOMING = "http://api.themoviedb.org/3/movie/upcoming";
    String ENDPOINT_MOVIES_SEARCH = "http://api.themoviedb.org/3/search/movie";

    String BASELINK_IMAGES = "http://image.tmdb.org/t/p/w500";

    String PARAMETER_API_KEY = "api_key";
    String PARAMETER_QUERY = "query";

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");
}
