package com.example.assignment2.model;

import java.io.Serializable;
import java.util.List;

public class MovieModel {

    public static class Movie implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String title;
        private final String year;
        private final String studio;
        private final String plot;
        private final List<Rating> ratings;
        private final String posterURL;

        public Movie(String title, String year, String studio, String plot,
                     List<Rating> ratings, String posterURL) {
            this.title = title;
            this.year = year;
            this.studio = studio;
            this.plot = plot;
            this.ratings = ratings;
            this.posterURL = posterURL;
        }

        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }

        public String getStudio() {
            return studio;
        }

        public String getPlot() {
            return plot;
        }

        public List<Rating> getRatings() {
            return ratings;
        }

        public String getPosterURL() {
            return posterURL;
        }

        public String getFirstRatingValue() {
            if (ratings != null && !ratings.isEmpty()) {
                return ratings.get(0).getValue();
            }
            return "N/A";
        }
    }

    public static class Rating implements Serializable {
        private static final long serialVersionUID = 1L;
        private String source;
        private String value;

        public Rating(String source, String value) {
            this.source = source;
            this.value = value;
        }

        public String getSource() {
            return source;
        }

        public String getValue() {
            return value;
        }
    }
}