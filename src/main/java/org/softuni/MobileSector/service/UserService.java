package org.softuni.MobileSector.service;

import org.softuni.MobileSector.model.dto.UserRegistrationDTO;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);
    boolean registerUserCheck(UserRegistrationDTO userRegistrationDTO);

}
