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
    private Integer roomID;
    private int hotelID;
    private String roomType;
    private int price;
    private int roomNumber;

    @Override
    public String toString() {
        return "RoomVO{" +
                "roomID=" + roomID +
                ", hotelID=" + hotelID +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", roomNumber=" + roomNumber +
                '}';
    }

}
