package com.example.BookMyShow.EntryDtos;

import com.example.BookMyShow.Entities.ShowEntity;
import com.example.BookMyShow.Entities.TheatreEntity;
import com.example.BookMyShow.Enums.Genre;
import com.example.BookMyShow.Enums.Language;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class MovieEntryDto {
    private String movieName;

    private Language language;

    private Genre genre;

    private Double rating;

    private Double duration;
}
