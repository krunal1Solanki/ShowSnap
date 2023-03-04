package com.example.BookMyShow.Controller;

import com.example.BookMyShow.EntryDtos.MovieEntryDto;
import com.example.BookMyShow.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movie")
public class MovieController {
    @Autowired
    MovieService movieService;
    @PostMapping("addMovie")
    public ResponseEntity addMovie(@RequestBody MovieEntryDto movieEntryDto) {
        return new ResponseEntity(movieService.addMovie(movieEntryDto), HttpStatus.CREATED);
    }
}
