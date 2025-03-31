package com.example.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @PersistenceContext
    private EntityManager em;

    MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository, EntityManager entityManager) {
        this.movieRepository = movieRepository;
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
