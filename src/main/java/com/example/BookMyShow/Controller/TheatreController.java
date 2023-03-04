package com.example.BookMyShow.Controller;

import com.example.BookMyShow.EntryDtos.TheatreEntryDto;
import com.example.BookMyShow.Service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("theatre")
public class TheatreController {
    @Autowired
    TheatreService theatreService;

    @PostMapping("addTheatre")
    public ResponseEntity addTheatre (@RequestBody TheatreEntryDto theatreEntryDto) {
        try {
            return theatreService.addTheatre(theatreEntryDto);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
