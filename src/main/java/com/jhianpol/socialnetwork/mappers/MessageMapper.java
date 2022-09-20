package com.jhianpol.socialnetwork.mappers;

import java.util.List;

import com.jhianpol.socialnetwork.dto.MessageDto;
import com.jhianpol.socialnetwork.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
    uses = {UserMapper.class})
public interface MessageMapper {

    List<MessageDto> messagesToMessageDtos(List<Message> messages);

    @Mapping(target = "userDto", source = "user")
    MessageDto messageToMessageDto(Message message);

    Message messageDtoToMessage(MessageDto messageDto);
}
