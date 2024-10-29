package com.kdt.hotels;

import com.kdt.hotels.dao.HotelDAO;
import com.kdt.hotels.dao.UsersDAO;
import com.kdt.hotels.vo.HotelVO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class HotelsApplication implements CommandLineRunner {
	private final HotelDAO hotelDAO;
	private final UsersDAO usersDAO;
	Scanner sc = new Scanner(System.in);

	public HotelsApplication(HotelDAO hotelDAO, UsersDAO usersDAO) {
		this.hotelDAO = hotelDAO;
		this.usersDAO = usersDAO;
	}

	public static void main(String[] args) {
		SpringApplication.run(HotelsApplication.class,args);

	}

	@Override
	public void run (String... args) throws Exception {
		String s = "Seoul";
		hotelDAO.hotelStarList(s);
		hotelDAO.allHotelList();
		hotelDAO.hotelMaxPList(s);
		hotelDAO.hotelMinPList(s);
		System.out.println();
		System.out.println(usersDAO.userGrade("user03"));

	}
	public static void menu() {

	}

}
