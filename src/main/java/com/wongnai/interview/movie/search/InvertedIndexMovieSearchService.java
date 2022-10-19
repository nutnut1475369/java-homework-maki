package com.wongnai.interview.movie.search;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService {
    @Autowired
    private MovieRepository movieRepository;

    private Multimap<String, Long> invertedIndexMap = ArrayListMultimap.create();

    @Override
    public List<Movie> search(String queryText) {

        List<String> queryListSubString = Arrays.asList(queryText.split("\\s+"));
        List<Long> resultIds = new ArrayList<>();
        List<Movie> resultMovies = new ArrayList<>();

        if (invertedIndexMap.isEmpty()) createInvertedIndexMap();
        if (queryListSubString.isEmpty()) return Collections.emptyList();

        for (String querySubString : queryListSubString) {
            if (invertedIndexMap.get(querySubString.toLowerCase()).isEmpty()) return Collections.emptyList();
            if (resultIds.isEmpty()) {
                resultIds.addAll(invertedIndexMap.get(querySubString.toLowerCase()));
            } else {
                resultIds.retainAll(invertedIndexMap.get(querySubString.toLowerCase()));
            }
        }

        resultIds.forEach(id -> resultMovies.add(movieRepository.findById(id).orElse(null)));
        return resultMovies;
    }

    public void createInvertedIndexMap() {
        invertedIndexMap.clear();
        movieRepository.findAll().forEach(movie -> Arrays.asList(movie.getName().split("\\s+")).forEach(name -> invertedIndexMap.put(name.toLowerCase(), movie.getId())));
    }
}
