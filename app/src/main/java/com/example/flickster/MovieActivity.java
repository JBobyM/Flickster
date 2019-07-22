package com.example.flickster;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickster.Adapters.MoviesAdapter;
import com.example.flickster.Models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
    private static final String MOVIE_URL= "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    List<Movie> movies;


    // Add RecyclerView support library to the Gradle build file = Done
    // Define a model class to use as the data source = Done (Movie.java)
    // Add a RecyclerView to your activity to display the items = Done
    // Create a custom row layout XML file to visualize the item = Done
    // Create a RecyclerView.Adapter and ViewHolder to render the item = Done
    // Bind the adapter to the data source to populate the RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        final MoviesAdapter adapter = new MoviesAdapter(this, movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
        rvMovies.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIE_URL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               JSONArray movieJsonArray = null;

                try {
                    movieJsonArray = response.getJSONArray("results");
                   movies.addAll(Movie.fromJsonArray(movieJsonArray));
                   adapter.notifyDataSetChanged();
                    Log.d("DEBUG", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this ads items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_flickster, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onComposeAction(MenuItem mi){
        Toast.makeText(this, "Pour Tester", Toast.LENGTH_SHORT).show();
    }
}
