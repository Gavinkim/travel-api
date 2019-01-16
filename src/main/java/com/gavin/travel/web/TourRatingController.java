package com.gavin.travel.web;

import com.gavin.travel.domain.Tour;
import com.gavin.travel.domain.TourRating;
import com.gavin.travel.domain.TourRatingPk;
import com.gavin.travel.repository.TourRatingRepository;
import com.gavin.travel.repository.TourRepository;
import com.gavin.travel.web.dto.RatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Created by gavinkim at 2019-01-17
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {

    TourRatingRepository tourRatingRepository;
    TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") Long tourId, @RequestBody @Validated RatingDto ratingDto) {
        Tour tour = verifyTour(tourId);
        TourRatingPk tourRatingPk = TourRatingPk.builder()
                .tourIdx(tour)
                .customerId(ratingDto.getCustomerId())
                .build();
        TourRating tourRating = TourRating.builder()
                .pk(tourRatingPk)
                .score(ratingDto.getScore())
                .comment(ratingDto.getComment())
                .build();
        tourRatingRepository.save(tourRating);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") Long tourId, Pageable pageable) {
        Tour tour = verifyTour(tourId);
        Page<TourRating> tourRatingPage = tourRatingRepository.findByPkTourId(tour.getTourIdx(), pageable);
        List<RatingDto> ratingDtoList = tourRatingPage.getContent().stream().map(tourRating -> toDto(tourRating)).collect(Collectors.toList());
        return new PageImpl<RatingDto>(ratingDtoList, pageable, tourRatingPage.getTotalPages());
    }
    /**
     * Convert the TourRating entity to a RatingDto
     */
    private RatingDto toDto(TourRating tourRating) {
        return new RatingDto(tourRating.getScore(), tourRating.getComment(), tourRating.getPk().getCustomerId());
    }

    /**
     * Calculate the average Score of a Tour.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/average")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(value = "tourId") Long tourId) {
        List<TourRating> ratings = tourRatingRepository.findByPkTourId(tourId);
        OptionalDouble average = ratings.stream().mapToInt(TourRating::getScore).average();
        double result = average.isPresent() ? average.getAsDouble():null;
        return new AbstractMap.SimpleEntry<String, Double>("average", result);
    }

    /**
     * Update score and comment of a Tour Rating
     */
    @RequestMapping(method = RequestMethod.PUT)
    public RatingDto updateWithPut(@PathVariable(value = "tourId") Long tourId, @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        rating.setScore(ratingDto.getScore());
        rating.setComment(ratingDto.getComment());
        return toDto(tourRatingRepository.save(rating));
    }
    /**
     * Verify and return the TourRating for a particular tourId and Customer
     */
    private TourRating verifyTourRating(Long tourId, int customerId) throws NoSuchElementException {
        TourRating rating = tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId);
        if (rating == null) {
            throw new NoSuchElementException("Tour-Rating pair for request("
                    + tourId + " for customer" + customerId);
        }
        return rating;
    }

    /**
     * Update score or comment of a Tour Rating
     */
    @RequestMapping(method = RequestMethod.PATCH)
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") Long tourId, @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        if (ratingDto.getScore() != null) {
            rating.setScore(ratingDto.getScore());
        }
        if (ratingDto.getComment() != null) {
            rating.setComment(ratingDto.getComment());
        }
        return toDto(tourRatingRepository.save(rating));
    }

    /**
     * Delete a Rating of a tour made by a customer
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") Long tourId, @PathVariable(value = "customerId") int customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    /**
     * Verify and return the Tour given a tourId.
     */
    private Tour verifyTour(Long tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(()->
                new NoSuchElementException("Tour does not exist " + tourId));
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }
}
