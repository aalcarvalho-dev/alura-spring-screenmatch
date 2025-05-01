package com.alura.cursospring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.cursospring.model.DadosEpisodio;
import com.alura.cursospring.model.DadosSerie;
import com.alura.cursospring.model.DadosTemporada;
import com.alura.cursospring.principal.Principal;
import com.alura.cursospring.service.ConsumoAPI;
import com.alura.cursospring.service.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
		
		var consumoApi = new ConsumoAPI();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?apikey=7638fe5c&t=gilmore+girls");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);

		json = consumoApi.obterDados("https://www.omdbapi.com/?apikey=7638fe5c&t=gilmore+girls&season=1&episode=2");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i =1; i <= dadosSerie.totalTemporadas(); i++) {
			json = consumoApi.obterDados("https://www.omdbapi.com/?apikey=7638fe5c&t=gilmore+girls&season="+i);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}

}
