package com.movieNewApiTrailer.movies.service;

import com.movieNewApiTrailer.movies.Repository.ReviewRepository;
import com.movieNewApiTrailer.movies.entity.Movie;
import com.movieNewApiTrailer.movies.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview (String reviewBody,String imdbId){
        Review review = repository.insert(new Review(reviewBody, LocalDateTime.now(), LocalDateTime.now()));

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviews").value(review))
                .first();

        return review;
            }
}
