package com.kdt.hotels.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVO {
    private int reviewID;
    private int hotelID;
    private String hotelName;
    private String userID;
    private String content;
    private int star;

}
