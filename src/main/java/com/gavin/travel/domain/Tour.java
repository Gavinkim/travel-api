package com.gavin.travel.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gavinkim at 2019-01-15
 * The Tour contains all attributes of an Explore California Tour.
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "TOUR")
public class Tour implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "TOUR_IDX")
    private Long tour_idx;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION",length = 2000)
    private String description;

    @Column(name = "BLURB",length = 2000)
    private String blurb;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "BULLETS",length = 2000)
    private String bullets;

    @Column(name = "KEYWORDS")
    private String keywords;

    @Column(name = "DIFFICULTY")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "REGION")
    @Enumerated(EnumType.STRING)
    private Region region;

    @ManyToOne
    private TourPackage tourPackage;

    @Builder

    public Tour(String title, String description, String blurb, Integer price, String bullets, String keywords, Difficulty difficulty, Region region, TourPackage tourPackage) {
        this.title = title;
        this.description = description;
        this.blurb = blurb;
        this.price = price;
        this.bullets = bullets;
        this.keywords = keywords;
        this.difficulty = difficulty;
        this.region = region;
        this.tourPackage = tourPackage;
    }
}
