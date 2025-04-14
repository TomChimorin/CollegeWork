package com.example.assignment2.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.R;
import com.example.assignment2.model.MovieModel;
import com.squareup.picasso.Picasso;

public class MovieDetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        MovieModel.Movie movie = (MovieModel.Movie) getIntent().getSerializableExtra("MOVIE_DATA");

        if (movie != null) {
            ImageView posterImageView = findViewById(R.id.detailsPosterImageView);
            TextView titleTextView = findViewById(R.id.detailsTitleTextView);
            TextView yearTextView = findViewById(R.id.detailsYearTextView);
            TextView ratingTextView = findViewById(R.id.detailsRatingTextView);
            TextView studioTextView = findViewById(R.id.detailsStudioTextView);
            TextView plotTextView = findViewById(R.id.detailsPlotTextView);

            if (movie.getPosterURL() != null && !movie.getPosterURL().isEmpty()) {
                Picasso.get()
                        .load(movie.getPosterURL())
                        .placeholder(R.drawable.placeholder_poster)
                        .error(R.drawable.error_poster)
                        .into(posterImageView);
            } else {
                posterImageView.setImageResource(R.drawable.placeholder_poster);
            }

            titleTextView.setText(movie.getTitle());
            yearTextView.setText(movie.getYear());
            ratingTextView.setText(String.format("Rating: %s", movie.getFirstRatingValue()));
            studioTextView.setText(movie.getStudio() != null ? movie.getStudio() : "Unknown Studio");
            plotTextView.setText(movie.getPlot());
        }
    }
}