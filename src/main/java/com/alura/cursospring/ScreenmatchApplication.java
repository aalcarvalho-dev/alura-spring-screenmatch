package com.alura.cursospring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.cursospring.model.DadosSerie;
import com.alura.cursospring.service.ConsumoAPI;
import com.alura.cursospring.service.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoAPI();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?apikey=7638fe5c&t=gilmore+girls");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);
	}

}
