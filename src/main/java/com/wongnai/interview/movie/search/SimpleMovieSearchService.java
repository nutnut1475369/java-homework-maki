package com.wongnai.interview.movie.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		return movieDataService.fetchAll()
				.stream()
				.filter(movieData -> {
					List<String> titleSubString = Arrays.asList(movieData.getTitle().toLowerCase().split("\\s+"));
					return titleSubString.contains(queryText.toLowerCase());
				})
				.map(movieData -> {
					Movie movie = new Movie(movieData.getTitle());
					movie.setActors(movieData.getCast());
					return movie;
				})
				.collect(Collectors.toList());
	}
}
