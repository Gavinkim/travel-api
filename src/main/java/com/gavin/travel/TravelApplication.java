package com.gavin.travel;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavin.travel.domain.Difficulty;
import com.gavin.travel.domain.Region;
import com.gavin.travel.domain.Tour;
import com.gavin.travel.service.TourPackageService;
import com.gavin.travel.service.TourService;
import com.gavin.travel.web.dto.TourDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class TravelApplication implements CommandLineRunner {


    @Autowired
    private TourPackageService tourPackageService;
    @Autowired
    private TourService tourService;


    public static void main(String[] args) {
        SpringApplication.run(TravelApplication.class, args);
    }


    /**
     * Method invoked after this class has been instantiated by Spring container
     * Initializes the in-memory database with all the TourPackages and Tours.
     *
     * @param strings
     * @throws Exception if problem occurs.
     */
    @Override
    public void run(String... strings) throws Exception {
        //Create the default tour packages
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
        System.out.println("Number of tours packages =" + tourPackageService.total());

        //Persist the Tours to the database
        TourFromFile.importTours().forEach(t->tourService.createTour(
                TourDto.builder()
                        .title(t.title)
                        .description(t.description)
                        .blurb(t.blurb)
                        .price(Integer.parseInt(t.price))
                        .duration(t.duration)
                        .bullets(t.bullets)
                        .keywords(t.keywords)
                        .tourPackageName(t.packageName)
                        .difficulty(Difficulty.valueOf(t.difficulty))
                        .region(Region.findByLabel(t.region))
                        .build()
        ));
        System.out.println("Number of tours =" + tourService.total());

    }

    /**
     * Helper class to import the records in the ExploreCalifornia.json
     */
    static class TourFromFile {
        //attributes as listed in the .json file
        private String packageName, title, description, blurb, price, duration, bullets, keywords,  difficulty, region;

        /**
         * Open the travel.json, unmarshal every entry into a TourFromFile Object.
         */
        static List<TourFromFile> importTours() throws IOException {
            return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).
                    readValue(TourFromFile.class.getResourceAsStream("/Travel.json"),new TypeReference<List<TourFromFile>>(){});
        }
    }



}

