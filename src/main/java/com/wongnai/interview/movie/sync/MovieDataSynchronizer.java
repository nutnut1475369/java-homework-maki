package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.stream.Collectors;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public void forceSync() {
		movieRepository.saveAll(movieDataService.fetchAll().stream().map(movieData -> {
			Movie movie = new Movie(movieData.getTitle());
			movie.setActors(movieData.getCast());
			return movie;
		}).collect(Collectors.toList()));
	}
}
