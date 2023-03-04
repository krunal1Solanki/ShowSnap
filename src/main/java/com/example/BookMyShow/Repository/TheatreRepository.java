package com.example.BookMyShow.Repository;

import com.example.BookMyShow.Entities.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<TheatreEntity, Integer> {
}
