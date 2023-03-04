package com.example.BookMyShow.Repository;

import com.example.BookMyShow.Entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, String> {
}
