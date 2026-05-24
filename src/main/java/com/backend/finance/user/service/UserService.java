package com.backend.finance.user.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.backend.finance.common.exception.EmailAlreadyExistsException;
import com.backend.finance.user.dto.CreateUserRequest;
import com.backend.finance.user.dto.UserResponse;
import com.backend.finance.user.entity.User;
import com.backend.finance.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponse create(CreateUserRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException(request.email());
        }

        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        User savedUser = userRepository.save(user);

        return new UserResponse(
            savedUser.getId(),
            savedUser.getName(),
            savedUser.getEmail(),
            savedUser.getCreatedAt()
        );
    }
    
}
