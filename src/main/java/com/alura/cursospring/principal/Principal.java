package com.alura.cursospring.principal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.alura.cursospring.model.DadosSerie;
import com.alura.cursospring.model.DadosTemporada;
import com.alura.cursospring.model.Episodio;
import com.alura.cursospring.service.ConsumoAPI;
import com.alura.cursospring.service.ConverteDados;

public class Principal {

    Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?apikey=";
    private final String APIKEY = "7638fe5c&t=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private String nome;

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
		//temporadas.forEach(System.out::println);

        System.out.println("====================================");

/*         for(int i=0; i<temporadas.size(); i++){
            List<DadosEpisodio> episodios = temporadas.get(i).episodios();
            System.out.println("\nTemporada: "+temporadas.get(i).numero());
            for(int j=0; j < episodios.size(); j++){
                System.out.println(episodios.get(j).titulo());
            }
        } */

        //substituindo o trecho acima por lambdas
        //temporadas.forEach(t->t.episodios().forEach(e->System.out.println(e.titulo())));

/*         List<String> nomes = Arrays.asList("Bené","Pedro","Anabelle","Zumbido","Monique","Caíque");
        nomes.stream()
            .filter(n -> n.startsWith("P"))
            .sorted()
            .limit(3)
            .map(n -> n.toUpperCase())
            .forEach(System.out::println); */

/*         List<DadosEpisodio> dadosEpisodios = temporadas
            .stream()
            .flatMap(t -> t.episodios().stream())
            .collect(Collectors.toList());
        
        dadosEpisodios.stream()
            .filter(e -> !e.avaliacao().equalsIgnoreCase("n/a"))
            .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
            .limit(5)
            .forEach(System.out::println); */
        
        List<Episodio> episodios = temporadas.stream()
            .flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.numero(),d))
            ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver o resultado?");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancameto()!=null && e.getDataLancameto().isAfter(dataBusca))
                .forEach(e -> {
                    System.out.println(
                        "Temporada: "+e.getTemporada()+
                        " Episódio: "+e.getTitulo()+
                        " Data de Lançamento: "+e.getDataLancameto().format(formatter)
                    );
                });
        
        System.out.println("=========== pegando por trecho de titulo ==========");  
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodio = episodios.stream()
                .filter(n -> n.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
                //.forEach(System.out::println);
        if (episodio.isPresent()){
            System.out.println(episodio.get().getTemporada()+" aqui");
        }
        
        System.out.println("=========== testando datas ==========");  
        LocalTime hora = LocalTime.now();
        System.out.println(hora);

        hora = LocalTime.of(12, 0, 0);
        System.out.println(hora);

        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        LocalDateTime hora2 = LocalDateTime.now();
        hora2.format(formatoHora);
        System.out.println("opa "+hora2);

        LocalDateTime hoje = LocalDateTime.now();
        System.out.println(hoje);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        LocalDateTime agora = LocalDateTime.now();
        System.out.println(hoje.format(formatador));

        System.out.println("=========== testando optional ==========");  
        Optional<String> optionalDeNome = getNome();
        optionalDeNome.ifPresent(System.out::println);
        String orElse = optionalDeNome.orElse("Não achou!");
        System.out.println("orelse"+orElse);
        nome = "amor";
        optionalDeNome = getNome();
        optionalDeNome.ifPresent(System.out::println);
    }

    public Optional<String> getNome(){
        return Optional.ofNullable(nome);
    }
}
