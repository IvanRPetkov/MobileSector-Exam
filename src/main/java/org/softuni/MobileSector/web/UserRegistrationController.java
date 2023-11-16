package org.softuni.MobileSector.web;

import jakarta.validation.Valid;
import org.softuni.MobileSector.model.dto.UserRegistrationDTO;
import org.softuni.MobileSector.service.UserService;
import org.softuni.MobileSector.service.impl.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserRegistrationController {
    private final UserService userService;

    private final LoggedUser loggedUser;

    public UserRegistrationController(UserService userService, LoggedUser loggedUser) {
        this.userService = userService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegistrationDTO")
                                 UserRegistrationDTO userRegistrationDTO) {
        if(loggedUser.isLogged()) {
            return new ModelAndView("redirect:/index");
        }
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegistrationDTO")
                                 @Valid UserRegistrationDTO userRegistrationDTO,
                                 BindingResult bindingResult) {
        if(loggedUser.isLogged()) {
            return new ModelAndView("redirect:/index");
        }
        if(bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        boolean hasSuccessfulRegistration = userService.registerUserCheck(userRegistrationDTO);

        if(!hasSuccessfulRegistration) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("hasRegistrationError", true);
            return modelAndView;
        }

        return new ModelAndView("redirect:/login");
    }
}
