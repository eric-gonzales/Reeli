package com.ericgonzalesevans.reeli.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ericgonzalesevans.reeli.R;
import com.ericgonzalesevans.reeli.ui.adapter.MovieListAdapter;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import com.uwetrottmann.tmdb2.services.SearchService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView listView;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movielist);
        setTitle(getIntent().getStringExtra("Title"));

        listView = (ListView) findViewById(R.id.movieListView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        fetchMovies();
    }

    public void fetchMovies(){
        Tmdb tmdb = new Tmdb(getString(R.string.api_key_tmdb));
        MoviesService moviesService = tmdb.moviesService();
        Call <MovieResultsPage> movieResultsPageCall;
        Log.i("MovieResulsPageCall", getIntent().getStringExtra("MovieResultsPageCall"));
        if(getIntent().getStringExtra("MovieResultsPageCall").equals("BoxOffice")){
            movieResultsPageCall = moviesService.nowPlaying(1, "en");
        }
        else if(getIntent().getStringExtra("MovieResultsPageCall").equals("Upcoming")){
            movieResultsPageCall = moviesService.upcoming(1, "en");
        }
        else{
            SearchService searchService = tmdb.searchService();
            movieResultsPageCall = searchService.movie(getIntent().getStringExtra("query"), 1, "en", null, null, null, null);
        }
        movieResultsPageCall.enqueue(new Callback<MovieResultsPage>(){
            @Override
            public void onResponse(Call<MovieResultsPage> call, Response<MovieResultsPage> response){
                if(response.isSuccessful()){
                    MovieResultsPage movieResultsPage = response.body();
                    for(Movie movie : movieResultsPage.results){
                        movies.add(movie);
                        progressBar.incrementProgressBy(1);
                    }
                    MovieListActivity.this.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            onMovieFetchFinish(movies);
                        }
                    });
                }
                else{
                    Log.i("ERROR", "There was an error fetching movie content.");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t){
                t.printStackTrace();
            }
        });
    }

    public void onMovieFetchFinish(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.GONE); //get rid of the progress bar
        MovieListAdapter movieListAdapter = new MovieListAdapter(this, movies);
        listView.setAdapter(movieListAdapter);
    }

}
