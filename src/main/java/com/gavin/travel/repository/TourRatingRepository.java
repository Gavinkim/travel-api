package com.gavin.travel.repository;

import com.gavin.travel.domain.TourRating;
import com.gavin.travel.domain.TourRatingPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by gavinkim at 2019-01-17
 */
@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {

    List<TourRating> findByPkTourId(Long tourId);

    Page<TourRating> findByPkTourId(Long tourId, Pageable pageable);

    TourRating findByPkTourIdAndPkCustomerId(Long tourId, Integer customerId);
}
