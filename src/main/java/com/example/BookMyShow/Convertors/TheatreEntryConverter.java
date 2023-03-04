package com.example.BookMyShow.Convertors;

import com.example.BookMyShow.Entities.TheatreEntity;
import com.example.BookMyShow.EntryDtos.TheatreEntryDto;

public class TheatreEntryConverter {
    public static TheatreEntity entryConverter(TheatreEntryDto theatreEntryDto) {
        return TheatreEntity.builder().theatreName(theatreEntryDto.getTheatreName()).location(theatreEntryDto.getLocation()).build();
    }
}
