package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT DISTINCT obj FROM Movie obj INNER JOIN obj.genre genres WHERE " +
            "(:genre IS NULL OR :genre IN genres) AND " +
            "(LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%'))) " +
            "ORDER BY obj.title ASC")
    Page<Movie> find(Genre genre, String title, Pageable pageable);

    @Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj IN :movie")
    List<Movie> findMovieWithGenre(List<Movie> movie);

    @Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj IN :movie")
    Movie findMovieWithGenre(Movie movie);

}
