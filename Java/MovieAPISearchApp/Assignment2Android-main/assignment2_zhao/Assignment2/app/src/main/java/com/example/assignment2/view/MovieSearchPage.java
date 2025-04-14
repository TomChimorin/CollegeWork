package com.example.assignment2.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.viewmodel.MovieViewModel;
import com.example.assignment2.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchPage extends AppCompatActivity {

    private MovieViewModel movieViewModel;
    private MovieSearchAdapter movieAdapter;
    private EditText etSearchMovie;
    private int currentPage = 1;
    private Button nextButton, previousButton;
    private TextView pageNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_search);

        RecyclerView recyclerViewMovies = findViewById(R.id.movieRecyclerView);
        etSearchMovie = findViewById(R.id.etSearchMovie);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);
        pageNumberTextView = findViewById(R.id.tvPageNumber);

        nextButton.setEnabled(false);
        previousButton.setEnabled(false);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieAdapter = new MovieSearchAdapter(new ArrayList<>(), this);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.setAdapter(movieAdapter);

        movieViewModel.getMovieData().observe(this, new Observer<List<MovieModel.Movie>>() {
            @Override
            public void onChanged(List<MovieModel.Movie> movies) {
                movieAdapter.updateMovies(movies);
                handleEmptyResults(movies);

                if (movies != null && !movies.isEmpty()) {
                    previousButton.setEnabled(currentPage > 1);
                    if (movieViewModel.getTotalPages().getValue() != null) {
                        nextButton.setEnabled(currentPage < movieViewModel.getTotalPages().getValue());
                    } else {
                        nextButton.setEnabled(false);
                    }
                }
            }
        });

        movieViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    Toast.makeText(MovieSearchPage.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        movieViewModel.getCurrentPage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer page) {
                currentPage = page;
                updatePageNumber(currentPage);
            }
        });

        movieViewModel.getTotalPages().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalPages) {
                if (totalPages != null) {
                    updatePageNumber(currentPage);
                }
            }
        });

        findViewById(R.id.btnSearchMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = etSearchMovie.getText().toString().trim();
                if (!movieTitle.isEmpty()) {
                    movieViewModel.searchMovie(movieTitle, 1);
                } else {
                    Toast.makeText(MovieSearchPage.this, "Please enter a movie title.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                movieViewModel.searchMovie(etSearchMovie.getText().toString().trim(), currentPage);
                updatePageNumber(currentPage);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 1) {
                    currentPage--;
                    movieViewModel.searchMovie(etSearchMovie.getText().toString().trim(), currentPage);
                    updatePageNumber(currentPage);
                }
            }
        });
    }

    private void updatePageNumber(int pageNumber) {
        pageNumberTextView.setText("Page " + pageNumber);

        previousButton.setEnabled(pageNumber > 1);

        if (movieViewModel.getTotalPages().getValue() != null) {
            nextButton.setEnabled(pageNumber < movieViewModel.getTotalPages().getValue());
        } else {
            nextButton.setEnabled(false);
        }
    }

    private void handleEmptyResults(List<MovieModel.Movie> movies) {
        if (movies == null || movies.isEmpty()) {
            nextButton.setEnabled(false);
            previousButton.setEnabled(false);
            Toast.makeText(MovieSearchPage.this, "No movies found.", Toast.LENGTH_SHORT).show();
        } else {
            previousButton.setEnabled(currentPage > 1);
            if (movieViewModel.getTotalPages().getValue() != null) {
                nextButton.setEnabled(currentPage < movieViewModel.getTotalPages().getValue());
            } else {
                nextButton.setEnabled(false);
            }
        }
    }
}