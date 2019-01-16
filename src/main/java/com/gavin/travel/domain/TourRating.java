package com.gavin.travel.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by gavinkim at 2019-01-16
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "TOUR_RATING")
@Entity
public class TourRating {

    @EmbeddedId
    private TourRatingPk pk;

    @Column(name = "SCORE",nullable = false)
    private Integer score;

    @Column(name = "COMMENT")
    private String comment;

    @Builder
    public TourRating(TourRatingPk pk, Integer score, String comment) {
        this.pk = pk;
        this.score = score;
        this.comment = comment;
    }
}
