package com.gavin.travel.service;

import com.gavin.travel.domain.TourPackage;
import com.gavin.travel.repository.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gavinkim at 2019-01-17
 */
@Service
public class TourPackageService {

    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourPackageService(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    public TourPackage createTourPackage(String code, String name) {
        return !tourPackageRepository.existsById(code) ?
                tourPackageRepository.save(new TourPackage(code, name)):null;
    }
    public Iterable<TourPackage> lookup(){
        return tourPackageRepository.findAll();
    }

    public long total() {
        return tourPackageRepository.count();
    }

}
