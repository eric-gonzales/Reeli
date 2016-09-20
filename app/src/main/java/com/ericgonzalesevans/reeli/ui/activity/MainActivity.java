package com.ericgonzalesevans.reeli.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.ericgonzalesevans.reeli.R;
import com.ericgonzalesevans.reeli.data.api.TmdbApi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        final Intent intent = new Intent(this, MovieListActivity.class);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                intent.putExtra("Title", getString(R.string.search));
                intent.putExtra("ApiEndpoint", TmdbApi.ENDPOINT_MOVIES_SEARCH);
                intent.putExtra("query", query);
                startActivity(intent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
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
