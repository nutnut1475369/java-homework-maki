package com.wongnai.interview.movie.search;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("databaseMovieSearchService")
public class DatabaseMovieSearchService implements MovieSearchService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> search(String queryText) {
        return movieRepository.findByNameContainsIgnoreCase(queryText);
    }
}
