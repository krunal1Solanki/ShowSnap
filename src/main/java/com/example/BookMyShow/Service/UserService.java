package com.example.BookMyShow.Service;

import com.example.BookMyShow.Convertors.UserEntryConverter;
import com.example.BookMyShow.Entities.UserEntity;
import com.example.BookMyShow.EntryDtos.UserEntryDto;
import com.example.BookMyShow.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public String addUser(UserEntryDto userEntityDto) {
        UserEntity user = UserEntryConverter.entryConverter(userEntityDto);
        userRepository.save(user);
        return "User Added Successfully";
    }
}
