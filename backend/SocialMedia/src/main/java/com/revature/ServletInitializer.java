package com.revature;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SocialMediaApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ServletInitializer.class, args);
	}
	
	/***
	 * PostgreSQL database config settings
	 * @return 
	 */
	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
				
		dataSourceBuilder.driverClassName("org.postgresql.Driver");
		dataSourceBuilder.username(System.getenv("AWS_USERNAME"));
		dataSourceBuilder.password(System.getenv("AWS_PASSWORD"));
		dataSourceBuilder.url("jdbc:postgresql://" + System.getenv("AWS_DB_ENDPOINT") + "/socialmedia");
		
		return dataSourceBuilder.build();
	}

}
