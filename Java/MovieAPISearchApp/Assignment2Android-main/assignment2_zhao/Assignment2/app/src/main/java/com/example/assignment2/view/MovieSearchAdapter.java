package com.example.assignment2.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.example.assignment2.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieViewHolder> {

    private final List<MovieModel.Movie> movies;
    private final Context context;

    public MovieSearchAdapter(List<MovieModel.Movie> movies, Context context) {
        this.movies = movies != null ? movies : new ArrayList<>();
        this.context = context;
    }

    public void updateMovies(List<MovieModel.Movie> newMovies) {
        if (newMovies != null) {
            movies.clear();
            movies.addAll(newMovies);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieModel.Movie movie = movies.get(position);
        holder.bind(movie);

        holder.contView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsPage.class);
            intent.putExtra("MOVIE_DATA", movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitleTextView;
        TextView movieYearTextView;
        TextView movieRatingTextView;
        ImageView posterImageView;
        LinearLayout contView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitleTextView = itemView.findViewById(R.id.detailsTitleTextView);
            movieYearTextView = itemView.findViewById(R.id.detailsYearTextView);
            movieRatingTextView = itemView.findViewById(R.id.detailsRatingTextView);
            posterImageView = itemView.findViewById(R.id.detailsPosterImageView);
            contView = itemView.findViewById(R.id.contView);
        }

        public void bind(MovieModel.Movie movie) {
            movieTitleTextView.setText(movie.getTitle());
            movieYearTextView.setText(String.format("Release Year: %s", movie.getYear()));
            movieRatingTextView.setText(String.format("Rating: %s", movie.getFirstRatingValue()));

            if (movie.getPosterURL() != null && !movie.getPosterURL().isEmpty()) {
                Picasso.get()
                        .load(movie.getPosterURL())
                        .placeholder(R.drawable.placeholder_poster)
                        .error(R.drawable.error_poster)
                        .into(posterImageView);
            } else {
                posterImageView.setImageResource(R.drawable.placeholder_poster);
            }
        }
    }
}