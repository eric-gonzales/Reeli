package com.ericgonzalesevans.reeli.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ericgonzalesevans.reeli.R;
import com.ericgonzalesevans.reeli.data.api.TmdbApi;
import com.ericgonzalesevans.reeli.data.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        try{
            Movie movie = (Movie) getItem(position);
            String poster_url = TmdbApi.BASELINK_IMAGES + movie.getPoster_path();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("(yyyy)");
            Date release_date = TmdbApi.DATE_FORMAT.parse(movie.getRelease_date());

            movieTitle.setText(movie.getTitle());
            movieInfo.setText(simpleDateFormat.format(release_date));
            movieOverview.setText(movie.getOverview());
            Glide.with(context).load(poster_url).error(R.drawable.not_found).into(moviePoster);
        }
        catch(ParseException e){
            Log.e("ParseException", "Error: " + e.toString());
        }

        return rowView;
    }
}
