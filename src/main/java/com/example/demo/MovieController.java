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

    private final WebClient reviewClient;
    private final MovieRepository movieRepository;
    MovieService movieService;

    public MovieController(MovieService movieService, WebClient.Builder reviewClient, MovieRepository movieRepository) {
        this.reviewClient = reviewClient.baseUrl("http://localhost:8082").build();
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getAllMovies(){
        List<Review>  reviews = reviewClient.get()
                .uri("/reviews").retrieve().bodyToFlux(Review.class).collectList().block();
        if(reviews != null){
            for(Review review: reviews){
                System.out.println(review);
            }

        }

        return movieService.getMovie();
    }



    @GetMapping("/{id}")
    public List<Review> getMovieReviews(@PathVariable("id") Long movieId) {
        List<Review> allReviews = reviewClient.get()
                .uri("/reviews")
                .retrieve()
                .bodyToFlux(Review.class)
                .collectList()
                .block();

        if (allReviews == null || allReviews.isEmpty()) {
            System.out.println("No reviews found.");
            return Collections.emptyList();
        }

        List<Review> filteredReviews = allReviews.stream()
                .filter(review -> review.getMovieId().equals(movieId))
                .collect(Collectors.toList());

        if(filteredReviews.isEmpty()){
            System.out.println("No reviews for that movie found.");
            return Collections.emptyList();
        }

        return filteredReviews;
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
