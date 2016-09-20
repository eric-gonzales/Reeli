package com.ericgonzalesevans.movieapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ericgonzalesevans.movieapp.R;
import com.ericgonzalesevans.movieapp.data.api.TmdbApi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toBoxOfficeMovies(View view){
        Intent intent = new Intent(this, MovieListActivity.class);
        intent.putExtra("Title", getString(R.string.box_office));
        intent.putExtra("ApiEndpoint", TmdbApi.ENDPOINT_MOVIES_BOXOFFICE);
        startActivity(intent);
    }

    public void toUpcomingMovies(View view){
        Intent intent = new Intent(this, MovieListActivity.class);
        intent.putExtra("Title", getString(R.string.upcoming));
        intent.putExtra("ApiEndpoint", TmdbApi.ENDPOINT_MOVIES_UPCOMING);
        startActivity(intent);
    }

}
