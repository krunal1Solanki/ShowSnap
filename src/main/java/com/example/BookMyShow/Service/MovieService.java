package com.example.BookMyShow.Service;

import com.example.BookMyShow.Convertors.MovieEntryConverter;
import com.example.BookMyShow.Entities.MovieEntity;
import com.example.BookMyShow.Entities.ShowEntity;
import com.example.BookMyShow.Entities.TheatreEntity;
import com.example.BookMyShow.EntryDtos.MovieEntryDto;
import com.example.BookMyShow.Repository.MovieRepository;
import com.example.BookMyShow.Repository.ShowRepository;
import com.example.BookMyShow.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;
    public String addMovie(MovieEntryDto movieEntryDto) {
        MovieEntity movie = MovieEntryConverter.movieEntryConvertor(movieEntryDto);
        movieRepository.save(movie);
        return "Successfully Added";
    }
}
