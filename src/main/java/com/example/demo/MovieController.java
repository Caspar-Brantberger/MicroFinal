package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    MovieService movieService;

    public MovieController(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getAllMovies(){
        return movieService.getMovie();
    }

    @GetMapping("/{id}")
    public List<Review> getMovieReviews(@PathVariable("id") Long movieId) {
        return movieService.getMovieReviews(movieId);
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        Movie newMovie = movieService.addMovie(movie);
        return ResponseEntity.ok(newMovie);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id,@RequestBody Movie movie1){
        Movie movie = movieService.updateMovie(id,movie1);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
    }
}
