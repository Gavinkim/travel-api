package com.gavin.travel.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by gavinkim at 2019-01-15
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
@Table(name = "TOUR_PACKAGE")
@Entity
public class TourPackage implements Serializable {

    @Id
    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Builder
    public TourPackage(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
