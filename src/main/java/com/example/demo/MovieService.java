package com.example.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @PersistenceContext
    private EntityManager em;

    private WebClient reviewClient;

    MovieRepository movieRepository;


    public MovieService(MovieRepository movieRepository, EntityManager entityManager, WebClient.Builder reviewClient) {
        this.movieRepository = movieRepository;
        this.reviewClient = reviewClient.baseUrl("http://localhost:8082").build();
        this.em = entityManager;
    }
    public Movie addMovie(Movie movie) {
        em.clear();
        return movieRepository.save(movie);
    }

   public List<Movie> getMovie(){
       List<Movie>movies = (List<Movie>) movieRepository.findAll();
        return movies;
    }
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

    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
        em.clear();
    }
    public Movie updateMovie(Long id, Movie newMovie){
        Movie movie = movieRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Movie not found with id:"));
        movie.setTitle(newMovie.getTitle());
        movie.setGenre(newMovie.getGenre());
        return movieRepository.save(movie);
    }
}
