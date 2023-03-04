package com.example.BookMyShow.Convertors;

import com.example.BookMyShow.Entities.UserEntity;
import com.example.BookMyShow.EntryDtos.UserEntryDto;

public class UserEntryConverter {
    public static UserEntity entryConverter(UserEntryDto userEntityDto) {
        return UserEntity.builder().address(userEntityDto.getAddress()).age(userEntityDto.getAge()).mobNo(userEntityDto.getMobNo()).email(userEntityDto.getEmail()).userName(userEntityDto.getUserName()).build();
    }
}
