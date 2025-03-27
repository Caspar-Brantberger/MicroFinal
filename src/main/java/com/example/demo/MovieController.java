package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final WebClient reviewClient;
    MovieService movieService;

    public MovieController(MovieService movieService, WebClient.Builder reviewClient) {
        this.reviewClient = reviewClient.baseUrl("http://localhost:8082").build();
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies(){
        return movieService.getMovie();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getAllMovieswithId(@PathVariable Long id){
        Movie movie = movieService.getMovieById(id);
       return ResponseEntity.ok(movie);
   }
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        Movie newMovie = movieService.addMovie(movie);
        return ResponseEntity.ok(newMovie);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id,@RequestBody String title, @RequestBody String genre){
        Movie movie = movieService.updateMovie(id,title,genre);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
    }
}
