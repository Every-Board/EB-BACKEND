package com.java.everyboard.user.mapper;

import com.java.everyboard.user.dto.UserPatchDto;
import com.java.everyboard.user.dto.UserPostDto;
import com.java.everyboard.user.dto.UserResponseDto;
import com.java.everyboard.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User postDtoToUser(UserPostDto requestBody);

    User patchDtoToUser(UserPatchDto requestBody);

    UserResponseDto userToUserResponseDto(User user);
}
