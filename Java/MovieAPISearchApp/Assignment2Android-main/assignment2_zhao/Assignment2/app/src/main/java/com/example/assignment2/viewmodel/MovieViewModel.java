package com.example.assignment2.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment2.BuildConfig;
import com.example.assignment2.model.MovieModel;
import com.example.assignment2.utils.ApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieViewModel extends ViewModel {

    private final MutableLiveData<List<MovieModel.Movie>> movieData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentPage = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> totalPages = new MutableLiveData<>();

    public LiveData<List<MovieModel.Movie>> getMovieData() {
        return movieData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Integer> getCurrentPage() {
        return currentPage;
    }

    public LiveData<Integer> getTotalPages() {
        return totalPages;
    }

    public void searchMovie(String movieTitle, int page) {
        if (movieTitle == null || movieTitle.isEmpty()) {
            return;
        }

        isLoading.postValue(true);

        try {
            String urlString = "https://www.omdbapi.com/?s=" + URLEncoder.encode(movieTitle, "UTF-8") +
                    "&page=" + page + "&apikey=" + BuildConfig.OMDB_API_KEY + "&r=json&maxResults=100";
            Log.d("MovieViewModel", "Request URL: " + urlString);

            ApiClient.get(urlString, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("MovieViewModel", "API call failed: " + e.getMessage());
                    isLoading.postValue(false);
                    errorMessage.postValue("Failed to connect to the API.");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        Log.e("MovieViewModel", "API call failed with code: " + response.code());
                        isLoading.postValue(false);
                        errorMessage.postValue("Failed to retrieve data.");
                        return;
                    }

                    String responseData = response.body().string();
                    Log.d("MovieViewModel", "Response: " + responseData);

                    try {
                        JSONObject json = new JSONObject(responseData);
                        if (json.has("Error")) {
                            Log.e("OMDB_ERROR", json.getString("Error"));
                            isLoading.postValue(false);
                            errorMessage.postValue(json.getString("Error"));
                            return;
                        }

                        int totalResults = Integer.parseInt(json.getString("totalResults"));
                        int totalPagesValue = (int) Math.ceil(totalResults / 10.0);
                        totalPages.postValue(totalPagesValue);

                        JSONArray searchResults = json.getJSONArray("Search");
                        List<MovieModel.Movie> moviesList = new ArrayList<>();

                        for (int i = 0; i < searchResults.length(); i++) {
                            if (i >= 10) break;
                            JSONObject movieJson = searchResults.getJSONObject(i);
                            String title = movieJson.getString("Title");
                            String year = movieJson.getString("Year");
                            String imdbID = movieJson.getString("imdbID");

                            String detailsUrl = "https://www.omdbapi.com/?i=" + imdbID + "&apikey=" + BuildConfig.OMDB_API_KEY;

                            ApiClient.get(detailsUrl, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.e("MovieViewModel", "Details API call failed: " + e.getMessage());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if (!response.isSuccessful()) {
                                        Log.e("MovieViewModel", "Details API call failed with code: " + response.code());
                                        return;
                                    }

                                    try {
                                        String detailsResponseData = response.body().string();
                                        JSONObject detailsJson = new JSONObject(detailsResponseData);

                                        String plot = detailsJson.optString("Plot", "No plot available");
                                        String studio = detailsJson.optString("Production", "Unknown Studio");

                                        // Parse ratings
                                        List<MovieModel.Rating> ratingsList = new ArrayList<>();
                                        if (detailsJson.has("Ratings")) {
                                            JSONArray ratingsArray = detailsJson.getJSONArray("Ratings");
                                            for (int j = 0; j < ratingsArray.length(); j++) {
                                                JSONObject ratingObj = ratingsArray.getJSONObject(j);
                                                String source = ratingObj.getString("Source");
                                                String value = ratingObj.getString("Value");
                                                ratingsList.add(new MovieModel.Rating(source, value));
                                            }
                                        }

                                        String posterURL = detailsJson.optString("Poster", "");

                                        MovieModel.Movie movie = new MovieModel.Movie(title, year, studio, plot, ratingsList, posterURL);
                                        moviesList.add(movie);
                                        movieData.postValue(moviesList);

                                    } catch (JSONException e) {
                                        Log.e("MovieViewModel", "JSON Parsing error: " + e.getMessage());
                                    }
                                }
                            });
                        }

                        isLoading.postValue(false);

                    } catch (JSONException e) {
                        Log.e("MovieViewModel", "JSON Parsing error: " + e.getMessage());
                        isLoading.postValue(false);
                        errorMessage.postValue("Error parsing data.");
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            Log.e("MovieViewModel", "URL encoding error: " + e.getMessage());
            isLoading.postValue(false);
            errorMessage.postValue("URL encoding error.");
        }
    }
}