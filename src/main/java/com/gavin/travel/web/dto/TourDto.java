package com.gavin.travel.web.dto;

import com.gavin.travel.domain.Difficulty;
import com.gavin.travel.domain.Region;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TourDto {

    private  String title;
    private  String description;
    private  String blurb;
    private  Integer price;
    private  String duration;
    private  String bullets;
    private  String keywords;
    private  String tourPackageName;
    private  Difficulty difficulty;
    private  Region region;

    @Builder
    public TourDto(String title, String description, String blurb, Integer price, String duration, String bullets, String keywords, String tourPackageName, Difficulty difficulty, Region region) {
        this.title = title;
        this.description = description;
        this.blurb = blurb;
        this.price = price;
        this.duration = duration;
        this.bullets = bullets;
        this.keywords = keywords;
        this.tourPackageName = tourPackageName;
        this.difficulty = difficulty;
        this.region = region;
    }
}
