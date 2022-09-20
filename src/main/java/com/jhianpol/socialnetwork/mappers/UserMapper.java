package com.jhianpol.socialnetwork.mappers;

import java.util.List;

import com.jhianpol.socialnetwork.dto.ImageDto;
import com.jhianpol.socialnetwork.dto.ProfileDto;
import com.jhianpol.socialnetwork.dto.SignUpDto;
import com.jhianpol.socialnetwork.dto.UserDto;
import com.jhianpol.socialnetwork.dto.UserSummaryDto;
import com.jhianpol.socialnetwork.entities.Image;
import com.jhianpol.socialnetwork.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    UserSummaryDto toUserSummary(User user);

    List<UserSummaryDto> toUserSummaryDtos(List<User> users);

    @Mapping(target = "userDto.id", source = "id")
    @Mapping(target = "userDto.firstName", source = "firstName")
    @Mapping(target = "userDto.lastName", source = "lastName")
    ProfileDto userToProfileDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

    @Mapping(target = "userDto", source = "user")
    ImageDto imageToImageDto(Image image);

    List<ImageDto> imagesToImageDtos(List<Image> images);
}
