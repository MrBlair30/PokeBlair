package com.bsdc.PokeAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bsdc.PokeAPI.services.BDPokeAPIService;

@SpringBootApplication
public class PokeApiApplication implements CommandLineRunner{

	@Autowired
	private BDPokeAPIService bdPokeAPIService;
	public static void main(String[] args) {
		SpringApplication.run(PokeApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://192.168.1.69:4200").allowedHeaders("*").allowedMethods("*");
			}
		};
	}

	@Override
	public void run(String... args) throws Exception {
		/*for(int i = 101; i<=1025; i++){
			bdPokeAPIService.buscarYGuardarPokemon(i);
		}*/
	}

}
