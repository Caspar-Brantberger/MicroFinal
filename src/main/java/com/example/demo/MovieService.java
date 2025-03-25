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
    public Movie getMovieById(Long id){
        Movie movie = movieRepository.findById(id).get();
        return movie;
    }
    public void deleteMovie(Long id){
        movieRepository.deleteById(id);
        em.clear();
    }
    public Movie updateMovie(Long id,String newTitle,String newGenre){
        Movie movie = movieRepository.findById(id).get();
        movie.setTitle(newTitle);
        movie.setGenre(newGenre);
        return movieRepository.save(movie);
    }
}
