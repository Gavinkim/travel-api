package com.gavin.travel.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by gavinkim at 2019-01-15
 */
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Embeddable
public class TourRatingPk implements Serializable {

    @ManyToOne
    private Tour tourIdx;

    @Column(insertable = false, updatable = false,nullable = false)
    private Integer customerId;

    @Builder
    public TourRatingPk(Tour tourIdx, Integer customerId) {
        this.tourIdx = tourIdx;
        this.customerId = customerId;
    }
}
