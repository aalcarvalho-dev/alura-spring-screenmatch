package com.alura.cursospring.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.alura.cursospring.model.DadosSerie;
import com.alura.cursospring.model.DadosTemporada;
import com.alura.cursospring.service.ConsumoAPI;
import com.alura.cursospring.service.ConverteDados;

public class Principal {

    Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?apikey=";
    private final String APIKEY = "7638fe5c&t=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu(){
        System.out.print("Digite o nome da Série: ");
        var nomeSerie = leitura.nextLine();
		var json = consumoAPI.obterDados(ENDERECO  +APIKEY + nomeSerie.replace(" ", "+"));
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);
    
        List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i =1; i <= dadosSerie.totalTemporadas(); i++) {
			json = consumoAPI.obterDados(ENDERECO  +APIKEY + nomeSerie.replace(" ", "+")+"&season="+i);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
    }
}
