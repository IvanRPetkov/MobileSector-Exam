package org.softuni.MobileSector.service.impl;

import org.apache.catalina.User;
import org.softuni.MobileSector.model.dto.UserRegistrationDTO;
import org.softuni.MobileSector.model.entity.UserEntity;
import org.softuni.MobileSector.model.events.UserRegisteredEvent;
import org.softuni.MobileSector.repository.UserRepository;
import org.softuni.MobileSector.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher appEventPublisher;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher appEventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appEventPublisher = appEventPublisher;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        userRepository.save(map(userRegistrationDTO));

        appEventPublisher.publishEvent(new UserRegisteredEvent(
                "UserService",
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.fullName()
        ));
    }

    @Override
    public boolean registerUserCheck(UserRegistrationDTO userRegistrationDTO) {
        if(!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())) {
            return false;
        }

        boolean existsByEmail = userRepository
                .existsByEmail(userRegistrationDTO.getEmail());

        if(existsByEmail) {
            return false;
        }

        UserEntity user = new UserEntity();

        user.setEmail(userRegistrationDTO.getEmail());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        userRepository.save(user);

        return true;
    }

    private UserEntity map(UserRegistrationDTO userRegistrationDTO) {
        return new UserEntity()
                .setActive(false)
                .setFirstName(userRegistrationDTO.getFirstName())
                .setLastName(userRegistrationDTO.getLastName())
                .setEmail(userRegistrationDTO.getEmail())
                .setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
    }
}
