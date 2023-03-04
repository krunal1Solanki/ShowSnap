package com.example.BookMyShow.Service;

import com.example.BookMyShow.Convertors.ShowEntryConverter;
import com.example.BookMyShow.Entities.MovieEntity;
import com.example.BookMyShow.Entities.ShowEntity;
import com.example.BookMyShow.Entities.TheatreEntity;
import com.example.BookMyShow.EntryDtos.ShowEntryDto;
import com.example.BookMyShow.Repository.MovieRepository;
import com.example.BookMyShow.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;

@Service
public class ShowService {

    @Autowired
    TheatreRepository theatreRepository;

    @Autowired
    MovieRepository movieRepository;

    public ResponseEntity addShow(ShowEntryDto showEntryDto) throws Exception{
        ShowEntity showEntity = ShowEntryConverter.entryConverter(showEntryDto);

        boolean toCheckShowOverlap = toCheckShowOverlap(showEntryDto);
        if(!toCheckShowOverlap) throw new Exception("Cannot enter show, conflict with other show!!");

        TheatreEntity theatreEntity = theatreRepository.findById(showEntryDto.getTheatreId()).get();
        theatreEntity.getShowEntityList().add(showEntity);

        MovieEntity movie = movieRepository.findById(showEntryDto.getMovieId()).get();

        showEntity.setMovieEntity(movie);
        showEntity.setTheatreEntity(theatreEntity);

        theatreRepository.save(theatreEntity);
        return new ResponseEntity<>("Show added successfully", HttpStatus.CREATED);
    }

    private boolean toCheckShowOverlap(ShowEntryDto showEntity) {
        LocalTime start = showEntity.getLocalTime();
        LocalTime end = start.plusHours(3);

        TheatreEntity theatreEntity = theatreRepository.findById(showEntity.getTheatreId()).get();
        List<ShowEntity> listOfShows = theatreEntity.getShowEntityList();
        if(listOfShows.isEmpty()) return true;

        for(ShowEntity show : listOfShows) {
            LocalTime newStart = show.getLocalTime();
            LocalTime newEnd = newStart.plusHours(3);
            if(newStart.isAfter(start) && newStart.isBefore(end) || newEnd.isAfter(start) && newEnd.isBefore(end))
                return false;
        }

        return true;
    }
}
