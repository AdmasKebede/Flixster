package com.example.messengerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private final String URL="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private final String IMAGE_URL="https://image.tmdb.org/t/p/w342";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        List<Movie> movieList=new ArrayList<Movie>();
        RecyclerView rv = findViewById(R.id.recyclerView);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new TextHttpResponseHandler() {
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

                    for(int i=0;i<results.length();i++){
                        JSONObject movie = (JSONObject) results.get(i);
                        movieList.add(new Movie(movie.getString("original_title"),movie.getString("overview"),IMAGE_URL + movie.getString("poster_path"),Double.parseDouble(movie.getString("vote_average")),Integer.parseInt(movie.getString("id"))));
                        Log.d("title",movie.getString("original_title"));
                        Log.d("description",movie.getString("overview"));
                        Log.d("image",movie.getString("poster_path"));
                    }

                    rv.setAdapter(new MovieAdaptor(movieList));

                    rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        rv.setAdapter(new MovieAdaptor(movieList));

        rv.setLayoutManager(new LinearLayoutManager(this));
    }

}