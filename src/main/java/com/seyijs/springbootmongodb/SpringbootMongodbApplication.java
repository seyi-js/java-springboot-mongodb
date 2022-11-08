package com.seyijs.springbootmongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableSwagger2
public class SpringbootMongodbApplication {

	public static void main(String[] args) {


		Dotenv dotenv = Dotenv.configure().directory("./java-mongodb-restapi").systemProperties().load();



		SpringApplication.run(SpringbootMongodbApplication.class, args);
	}

}
