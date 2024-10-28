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

	public static void main(String[] args) {
		SpringApplication.run(HotelsApplication.class, args);

	}

}
