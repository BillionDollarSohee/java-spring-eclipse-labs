package kr.or.kosa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAi5Application {

	// http://localhost:8090/h2-console
	// jdbc:h2:mem:2c0960b4-19f8-401b-b3c9-9b5d77a1a825
	
	public static void main(String[] args) {
		SpringApplication.run(SpringAi5Application.class, args);
	}

}
