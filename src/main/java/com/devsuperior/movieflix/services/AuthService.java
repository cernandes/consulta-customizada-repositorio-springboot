package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ForbiddenException;
import com.devsuperior.movieflix.services.exceptions.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User userAuthenticated() {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(userName);
        } catch (Exception e) {
            throw new UnAuthorizedException("Invalid user");
        }
    }

    public void validateSelfOrMember(Long userId) {
        User user = userAuthenticated();
        if (!user.getId().equals(userId) && !user.hasRole("ROLE_MEMBER")) {
            throw new ForbiddenException("Access denied");
        }
    }
}
