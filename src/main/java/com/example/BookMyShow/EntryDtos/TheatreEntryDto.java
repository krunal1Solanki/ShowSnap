package com.example.BookMyShow.EntryDtos;

import com.example.BookMyShow.Entities.MovieEntity;
import com.example.BookMyShow.Entities.ShowEntity;
import com.example.BookMyShow.Entities.TheatreSeatEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TheatreEntryDto {
    private String theatreName;

    private String location;
    private int standardSeats;
    private int reclinerSeats;
    private int sofaSeats;
    private int vipSeats;
}
