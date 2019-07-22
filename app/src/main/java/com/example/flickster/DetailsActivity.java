package com.example.flickster;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flickster.Models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends YouTubeBaseActivity {
    TextView tvTitle;
    RatingBar ratingBar;
    TextView tvOverview2;
    Movie movie;
    TextView tvDate;
    TextView tvPop;
    private static final String YOUTUBE_API_KEY = "AIzaSyC3Nrt5PcQ222vt1x_U1blxo9juaF16KtY";
    private  static final String TRAILERS_API = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvTitle=findViewById(R.id.tvTitle);
        ratingBar = findViewById(R.id.ratingBar);
        tvOverview2 = findViewById(R.id.tvOverview2);
        tvDate = findViewById(R.id.tvDate);
        tvPop = findViewById(R.id.tvPop);
        youTubePlayerView  = findViewById(R.id.player);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));


        tvTitle.setText(movie.getTitle());
        tvOverview2.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getVoteAvrega());
        tvDate.setText("Released date: "+movie.getTvDate());
        tvPop.setText("Popularity: "+movie.getTvPop());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(TRAILERS_API, movie.getMovieId()), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // super.onSuccess(statusCode, headers, response);(On a enleve)
                try {
                    JSONArray results = response.getJSONArray("results");
                    if(results.length()==0){
                        return;
                    }
                    JSONObject movieTrailer =results.getJSONObject(0);
                    String youtubekey = movieTrailer.getString("key");
                    initializeYoutubePlayer(youtubekey);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    private void initializeYoutubePlayer(final String youtubekey ) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("smile", "On init success");

                if (movie.getVoteAvrega()>5) {

                youTubePlayer.loadVideo(youtubekey);

            } else{
                youTubePlayer.cueVideo(youtubekey);}
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("smile", "On init failure");

            }
        });
    }
}
