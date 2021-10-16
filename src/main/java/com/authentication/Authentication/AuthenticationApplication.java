package com.authentication.Authentication;

import com.authentication.Authentication.util.PropertiesLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AuthenticationApplication {


	public static void main(String[] args) {


			SpringApplication springApplication = new SpringApplication(AuthenticationApplication.class);
			springApplication.addListeners(new PropertiesLogger());
			springApplication.run(args);

	}


}
