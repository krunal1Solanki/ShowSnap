package com.example.BookMyShow.Convertors;

import com.example.BookMyShow.Entities.ShowEntity;
import com.example.BookMyShow.EntryDtos.ShowEntryDto;

public class ShowEntryConverter {
    public static ShowEntity entryConverter(ShowEntryDto showEntryDto) {
        return ShowEntity.builder().localTime(showEntryDto.getLocalTime()).localDate(showEntryDto.getLocalDate()).showType(showEntryDto.getShowType()).seatPrice(showEntryDto.getSeatPrice()).build();
    }
}
