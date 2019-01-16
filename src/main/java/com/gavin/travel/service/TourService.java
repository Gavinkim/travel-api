package com.gavin.travel.service;

import com.gavin.travel.domain.Tour;
import com.gavin.travel.domain.TourPackage;
import com.gavin.travel.repository.TourPackageRepository;
import com.gavin.travel.repository.TourRepository;
import com.gavin.travel.web.dto.TourDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gavinkim at 2019-01-17
 */
@Service
public class TourService {

    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(TourDto tourDto) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourDto.getTourPackageName()).orElseThrow(() -> new RuntimeException("Tour package does not exist: " + tourDto.getTourPackageName()));
        return tourRepository.save(Tour.builder()
                .title(tourDto.getTitle())
                .description(tourDto.getDescription())
                .blurb(tourDto.getBlurb())
                .price(tourDto.getPrice())
                .duration(tourDto.getDuration())
                .bullets(tourDto.getBullets())
                .keywords(tourDto.getKeywords())
                .tourPackage(tourPackage)
                .difficulty(tourDto.getDifficulty())
                .region(tourDto.getRegion())
                .build());
    }

    public long total() {
        return tourRepository.count();
    }
}
