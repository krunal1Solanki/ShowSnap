package com.example.BookMyShow.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;
import java.util.List;

@Entity
@Table(name="theatre")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheatreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String theatreName;

    private String location;

    @OneToMany(mappedBy = "theatreEntity", cascade = CascadeType.ALL)
    private List<TheatreSeatEntity> theatreSeatEntityList;

    @OneToMany(mappedBy = "theatreEntity", cascade = CascadeType.ALL)
    List<MovieEntity> movieEntityList;

    @OneToMany(mappedBy = "theatreEntity", cascade = CascadeType.ALL)
    List<ShowEntity> showEntityList;
}
// DTO - > Entity