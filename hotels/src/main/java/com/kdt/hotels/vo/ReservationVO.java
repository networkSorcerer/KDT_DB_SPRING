package com.kdt.hotels.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationVO {
    private int reserveID;
    private String userID;
    private int hotelID;
    private String hotelName;
    private Date startDate;
    private Date endDate;
    private int roomID;
    private String roomType;
    private int price;
    private int roomNumber;
}
