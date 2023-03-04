package com.example.BookMyShow.Controller;

import com.example.BookMyShow.EntryDtos.ShowEntryDto;
import com.example.BookMyShow.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("show")
public class ShowController {
    @Autowired
    ShowService showService;

    @PostMapping("addShow")
    public ResponseEntity addShow(@RequestBody ShowEntryDto showEntryDto) throws Exception {
        try {
            return showService.addShow(showEntryDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
