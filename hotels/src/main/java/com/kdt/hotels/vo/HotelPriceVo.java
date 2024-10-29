package com.kdt.hotels.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceVo {
    private int hotelID;
    private String hotelName;
    private String hotelPhone;
    private int hotelPriceMax;
    private int hotelPriceMin;
}
