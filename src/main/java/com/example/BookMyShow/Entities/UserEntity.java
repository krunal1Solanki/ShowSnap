package com.example.BookMyShow.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    @Column(unique = true, nullable = false)
    private String email;
    @NonNull
    @Column(unique = true)
    private String mobNo;

    private int age;
    private String address;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    List<TicketEntity> bookedTicketList;
}
