package com.jhianpol.socialnetwork.services;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jhianpol.socialnetwork.entities.User;
import com.jhianpol.socialnetwork.exceptions.AppException;
import com.jhianpol.socialnetwork.mappers.UserMapper;
import com.jhianpol.socialnetwork.repositories.UserRepository;
import com.jhianpol.socialnetwork.dto.ProfileDto;
import com.jhianpol.socialnetwork.dto.SignUpDto;
import com.jhianpol.socialnetwork.dto.UserDto;
import com.jhianpol.socialnetwork.dto.UserSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public ProfileDto getProfile(Long userId) {
        User user = getUser(userId);
        return userMapper.userToProfileDto(user);
    }

    public void addFriend(UserDto userDto, Long friendId) {
        User user = getUser(userDto.getId());

        User newFriend = getUser(friendId);

        if (user.getFriends() == null) {
            user.setFriends(new ArrayList<>());
        }

        user.getFriends().add(newFriend);

        userRepository.save(user);
    }

    public List<UserSummaryDto> searchUsers(String term) {
        List<User> users = userRepository.search("%" + term + "%");
        List<UserSummaryDto> usersToBeReturned = new ArrayList<>();

        users.forEach(user ->
                usersToBeReturned.add(new UserSummaryDto(user.getId(), user.getFirstName(), user.getLastName()))
        );

        return usersToBeReturned;
    }

    public UserDto signUp(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
    }
}
