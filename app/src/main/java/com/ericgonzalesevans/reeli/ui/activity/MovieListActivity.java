package com.ericgonzalesevans.reeli.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ericgonzalesevans.reeli.R;
import com.ericgonzalesevans.reeli.data.api.TmdbApi;
import com.ericgonzalesevans.reeli.data.model.Movie;
import com.ericgonzalesevans.reeli.ui.adapter.MovieListAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieListActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView listView;
    private ArrayList<Movie> movies;

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
        OkHttpClient client = new OkHttpClient();
        String api_endpoint = getIntent().getStringExtra("ApiEndpoint");
        HttpUrl.Builder builder = HttpUrl.parse(api_endpoint).newBuilder();
        builder.addQueryParameter(TmdbApi.PARAMETER_API_KEY, getString(R.string.api_key_tmdb));
        if(getIntent().getStringExtra("query") != null){
            builder.addQueryParameter(TmdbApi.PARAMETER_QUERY, getIntent().getStringExtra("query"));
        }
        String url = builder.build().toString();

        Request request = new Request.Builder().url(url).build();

        //creates a new worker thread to dispatch the network request
        client.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                else{
                    //Read data on the worker thread
                    final String rawJSON = response.body().string();
                    try {
                        JSONObject jsonArray = new JSONObject(rawJSON);
                        JSONArray boxOfficeMovies = jsonArray.getJSONArray("results");

                        movies = new ArrayList<>();
                        progressBar.setMax(boxOfficeMovies.length());

                        for(int i = 0; i < boxOfficeMovies.length(); i++){
                            Gson gson = new Gson();
                            Movie movie = gson.fromJson(boxOfficeMovies.getString(i), Movie.class);
                            movies.add(movie);
                            progressBar.incrementProgressBy(1);
                        }

                        //we will need to run the onMovieFetchFinish() method to update the views on the main UI thread
                        MovieListActivity.this.runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                onMovieFetchFinish(movies);
                            }
                        });
                    } catch(JSONException e){
                        Log.e("JSONException", "Error: " + e.toString());
                    }
                }
            }
        });
    }

    public void onMovieFetchFinish(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.GONE); //get rid of the progress bar
        MovieListAdapter movieListAdapter = new MovieListAdapter(this, movies);
        listView.setAdapter(movieListAdapter);
    }

}
