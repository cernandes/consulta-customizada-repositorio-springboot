package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        authService.validateSelfOrMember(dto.getId());
        Review rev = new Review();
        User user = new User();

        rev.setText(dto.getText());
        rev.setMovie(new Movie(dto.getMovieId(), null, null, null,
                null, null, null));
        user.setId(userService.getLoggedUser().getId());
        user.setName(userService.getLoggedUser().getName());
        user.setEmail(userService.getLoggedUser().getEmail());
        rev.setUser(user);
        rev = reviewRepository.save(rev);
        return new ReviewDTO(rev, rev.getUser());
    }

    public List<ReviewDTO> findReviewsOfMovie(Long id) {
        List<Review> list = reviewRepository.findReviewOfMovie(id);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Entity not found");
        }
        return list.stream().map(x -> new ReviewDTO(x, x.getUser())).collect(Collectors.toList());
    }
}