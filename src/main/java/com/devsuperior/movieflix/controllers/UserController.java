package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/profile")
    public ResponseEntity<UserDTO> getLogedUserProfile() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO entityDTO = userService.getLoggedUser();
        return ResponseEntity.ok().body(entityDTO);
    }
}
