package com.kdt.hotels.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelVO {
    private int hotelID;
    private String hotelName;
    private String region;
    private String phone;
    private String hotelExpl;
}
