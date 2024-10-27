package com.kdt.hotels.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomVO {
    private int roomID;
    private int hotelID;
    private String roomType;
    private int price;
    private int roomNumber;
}
