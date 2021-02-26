package com.example.messengerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie;
    final String VIDEO_URL="https://api.themoviedb.org/3/movie/";
    final String VIDEO_API_KEY="/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    final String KEY="CDGaSyDBcXnkbughUIDiSTwAhNmL3m7WgOahiUk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        YouTubePlayerView trailerPlayer= findViewById(R.id.player);
        TextView description = findViewById(R.id.movie_description);
        RatingBar rating = findViewById(R.id.rbVoteAverage);

        description.setText(movie.description);
        rating.setRating((float)(movie.rating/2.0f));

    }
}