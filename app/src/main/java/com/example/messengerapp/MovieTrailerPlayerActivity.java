package com.example.messengerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
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

public class MovieTrailerPlayerActivity extends YouTubeBaseActivity {

    Movie movie;
    final String VIDEO_URL="https://api.themoviedb.org/3/movie/";
    final String VIDEO_API_KEY="/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    final String KEY="AIzaSyDBcXnkbughUIDiSTwAhNmL3m7WgOahiUk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer_player);

        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        YouTubePlayerView trailerPlayer= findViewById(R.id.player);
       TextView description = findViewById(R.id.movie_description);
        TextView title = findViewById(R.id.movie_trailer_title_textView);
       RatingBar rating = findViewById(R.id.rbVoteAverage);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(VIDEO_URL+movie.id+VIDEO_API_KEY, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failure","failed");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("success",responseString);

                try {
                    JSONObject response = new JSONObject(responseString);
                    JSONArray results = response.getJSONArray("results");
                    JSONObject movie = (JSONObject) results.get(0);
                    String trailerId= (String)movie.get("key");

                    trailerPlayer.initialize(KEY, new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            Log.d("id",trailerId);
                            youTubePlayer.loadVideo(trailerId);
                            //youTubePlayer.cueVideo(trailerId);
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                            Log.d("error",youTubeInitializationResult.toString());
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        description.setText(movie.description);
        rating.setRating(Float.parseFloat(String.valueOf(movie.rating)));
        title.setText(movie.title);
    }
}