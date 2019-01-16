package com.gavin.travel.web.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by gavinkim at 2019-01-17
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RatingDto {

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Integer customerId;

    @Builder
    public RatingDto(@Min(0) @Max(5) Integer score, @Size(max = 255) String comment, @NotNull Integer customerId) {
        this.score = score;
        this.comment = comment;
        this.customerId = customerId;
    }
}
