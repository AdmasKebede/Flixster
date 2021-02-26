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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer_player);

        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        double movieRating = movie.rating;

        YouTubePlayerView trailerPlayer= findViewById(R.id.player);
       TextView description = findViewById(R.id.movie_description);
        TextView title = findViewById(R.id.movie_trailer_title_textView);
       RatingBar rating = findViewById(R.id.rbVoteAverage);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(VIDEO_URL+movie.id+getString(R.string.Video_api_key), new TextHttpResponseHandler() {
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

                    trailerPlayer.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                            if(movieRating > 5){
                                youTubePlayer.loadVideo(trailerId);
                            }else{
                                youTubePlayer.cueVideo(trailerId);
                            }

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
        rating.setRating((float)movie.rating);
        title.setText(movie.title);
    }
}