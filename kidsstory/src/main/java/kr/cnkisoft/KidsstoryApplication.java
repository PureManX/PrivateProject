package kr.cnkisoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class KidsstoryApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(KidsstoryApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KidsstoryApplication.class);
	}
}
