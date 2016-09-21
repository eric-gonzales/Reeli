package com.ericgonzalesevans.reeli.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ericgonzalesevans.reeli.R;
import com.uwetrottmann.tmdb2.entities.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovieListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Movie> movies;
    private Context context;

    public MovieListAdapter(Context context, ArrayList<Movie> movies){
        this.movies = movies;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return movies.size();
    }

    @Override
    public Object getItem(int position){
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.list_item_movie, parent, false);
        TextView movieTitle = (TextView) rowView.findViewById(R.id.movie_list_title);
        TextView movieInfo = (TextView) rowView.findViewById(R.id.movie_list_info);
        TextView movieOverview = (TextView) rowView.findViewById(R.id.movie_list_overview);
        ImageView moviePoster = (ImageView) rowView.findViewById(R.id.movie_list_thumbnail);

        Movie movie = (Movie) getItem(position);
        String poster_url = "http://image.tmdb.org/t/p/w500/" + movie.poster_path;
        movieTitle.setText(movie.title);
        movieInfo.setText(new SimpleDateFormat("(yyyy)").format(movie.release_date));
        movieOverview.setText(movie.overview);
        Glide.with(context).load(poster_url).error(R.drawable.not_found).into(moviePoster);

        return rowView;
    }
}
