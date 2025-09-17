package group.com.hotel_reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class HotelReservationApplication {
	public static void main(String[] args) {

		TimeZone.setDefault(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
		System.setProperty("user.timezone", "America/Argentina/Buenos_Aires");

		SpringApplication.run(HotelReservationApplication.class, args);
	}
}
