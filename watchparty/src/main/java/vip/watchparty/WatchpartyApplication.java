package vip.watchparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WatchpartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchpartyApplication.class, args);
	}

}
