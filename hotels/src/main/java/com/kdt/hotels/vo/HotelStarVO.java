package com.kdt.hotels.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelStarVO {
        private int hotelID;
        private String hotelName;
        private String phone;
        private String hotelExpl;
        private Double avg;
    }


