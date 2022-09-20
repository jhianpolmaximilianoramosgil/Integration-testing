package com.jhianpol.socialnetwork.services;

import java.nio.CharBuffer;
import javax.transaction.Transactional;

import com.jhianpol.socialnetwork.entities.User;
import com.jhianpol.socialnetwork.exceptions.AppException;
import com.jhianpol.socialnetwork.mappers.UserMapper;
import com.jhianpol.socialnetwork.repositories.UserRepository;
import com.jhianpol.socialnetwork.dto.CredentialsDto;
import com.jhianpol.socialnetwork.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    public UserDto authenticate(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {

            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Token not found", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }
}
