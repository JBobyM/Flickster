package com.example.flickster.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel // Annotation that indicates class is parcelable
public class Movie {
    double voteAvrega;
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    String tvDate;
    double tvPop;
    int movieId;


    // no-arg, empty constructor required for Parceler library
    public Movie() {}

    public Movie(JSONObject JsonObject) throws JSONException {
        posterPath = JsonObject.getString("poster_path");
        title = JsonObject.getString("title");
        overview = JsonObject.getString("overview");
        backdropPath=JsonObject.getString("backdrop_path");
        voteAvrega= JsonObject.getDouble("vote_average");
        tvDate = JsonObject.getString("release_date");
        movieId = JsonObject.getInt("id");
        tvPop = JsonObject.getDouble("popularity");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException{
        List<Movie> movies = new ArrayList<>();

        for (int x=0; x<movieJsonArray.length(); x++){
            movies.add(new Movie(movieJsonArray.getJSONObject(x)));
        }
        return movies;

        }

    public String getPosterPath() {
        // For what exactly???
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath );
    }
    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAvrega() {
        return voteAvrega;
    }

    public String getTvDate() {
        return tvDate;
    }

    public int getMovieId() {
        return movieId;
    }

    public double getTvPop() {
        return tvPop;
    }
}
