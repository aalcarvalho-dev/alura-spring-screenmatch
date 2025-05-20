# alura-spring-screenmatch
Curso Spring - Inicial

## Aula 1
Criar um Record DadosSerie.java:
```
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                        @JsonAlias("totalSeasons") Integer totalTemporadas,
                        @JsonAlias("imdbRating") String avaliacao) {

}
```

Criar uma interface genérica para conversão de String json em objeto de uma classe:
```
public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);

}
```

Criar classe que implementa a interface de conversão:
```
public class ConverteDados implements IConverteDados{
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {

            try {
                return mapper.readValue(json, classe);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

    }
}
```

Cria classe para consumo da api:
```
public class ConsumoAPI {
    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }
```

Criada tag v1 no GIT

## Aula 2
Refatorando o projeto
-model (Records)
--DadosEpisodio
--DadosSerie
--DadosTemporada
-principal
--Principal
-service
--ConsumoAPI
--ConverteDados
--IConverteDados

Na Classe Principal:
```
/*         for(int i=0; i<temporadas.size(); i++){
            List<DadosEpisodio> episodios = temporadas.get(i).episodios();
            System.out.println("\nTemporada: "+temporadas.get(i).numero());
            for(int j=0; j < episodios.size(); j++){
                System.out.println(episodios.get(j).titulo());
            }
        } */

        //substituindo o trecho acima por lambdas
        temporadas.forEach(t->t.episodios().forEach(e->System.out.println(e.titulo())));
```

Criada tag v2.0 no GIT

## Aula 3
Trabalhando com lambdas

- Alguns métodos do Stream
 - ```filter()```   - filtra a coleção
 - ```sorted()```   - ordena
 - ```limit()```    - limita a quantidade de resultados
 - ```map()```      - transforma o tipo da entrada para o tipo da saída
 - ```forEach()```  - itera sobre a lista
 - ```flatMap()```  - aglutina o resultado em um só (ex.: de temporadas para dados episodios)
 - ```toList()```   - retorna uma lista imutável
 - ```collect()```  - coleta os dados e coloca numa nova lista

__Obs.: Tem diferença um .forEach dentro do fluxo (stream) e fora, diretamente na lista!__
__Obs.: A ordem das operações intermediárias faz diferença!__

```
List<String> nomes = Arrays.asList("Bené","Pedro","Anabelle","Zumbido","Monique","Caíque");
nomes.stream()
    .filter(n -> n.startsWith("P"))
    .sorted()
    .limit(3)
    .map(n -> n.toUpperCase())
    .forEach(System.out::println);
```

- Ao criar a classe .model.Episodio estamos indicando que a partir daqui começa 
a separação entre o retorno da api e as regras de negócio da nossa aplicação.

No try...catch() tratar o erro direto no construtor da model. Exemplo de Episodio.java:

```
public Episodio(Integer temporada, DadosEpisodio dadosEpisodio) {
    this.temporada = temporada;
    this.titulo = dadosEpisodio.titulo();
    this.numero = dadosEpisodio.numeroEpisodio();
    
    try {
        this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
    } catch (NumberFormatException nfe) {
        this.avaliacao = 0.0;
    }

    try{
        this.dataLancameto = LocalDate.parse(dadosEpisodio.dataLancamento());
    } catch(DateTimeParseException dtpe){
        this.dataLancameto = null;
    }
}
```

Obs.: Lembre-se que, toda vez que usamos nextInt(), em seguida devemos usar nextLine(). Caso contrário, quando teclarmos "Enter", ele confundirá os valores lidos.

- Buscando após uma determinada data:
```
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
```

## Aula 4
Algumas Functional Interfaces:
```
Consumer<String> print = (message) -> System.out.println(message);
print.accept("Hello, functional!");

Predicate<Integer> testaSituacao = a -> a>5;
System.out.println(testaSituacao.test(6));

Predicate<String> testaPalavra = palavra -> palavra.contains("amor");
System.out.println(testaPalavra.test("ramon"));

//Function<Integer, Boolean> funcao = argumento -> argumento * 5 > 30;
Function<Integer, Boolean> funcao = SpringDesafio4Application::testaArgumento;
System.out.println("Calcula maiores que 30: "+funcao.apply(7)); 

Supplier<String> supplier = () -> "meu supplier";
System.out.println(supplier.get());
```

Streams utilizam Functional Interfaces
As operações em Stream API frequentemente recebem lambda expressions, que são implementações de functional interfaces. Por exemplo:
```
- Predicate<T> em filter()
- Function<T,R> em map()
- Consumer<T> em forEach()
- Supplier<T> em generate()
- BinaryOperator<T> em reduce()
```
Uso da função peek: Foi introduzida a função peek no Java, que permite visualizar o que está acontecendo em cada etapa da stream, facilitando o processo de debug.

Operações Intermediárias e Finais: Aprendemos sobre a utilização de operações (como map, filter e find) que nos permitem manipular e encontrar dados dentro de um Stream.

Uso de Containers para Dados: Examinamos como usar o Container Optional para armazenar um episódio dentro de um Stream e evitar referências nulas.

Filtragem de dados: Aprendemos a importância de filtrar dados adequados para análises e como isso pode afetar os resultados.

Uso do DoubleSummaryStatistics: Aprendemos como a classe Double Summary Statistics do Java pode ajudar a analisar informações, como a maior avaliação, a menor e a quantidade de avaliações em nossas séries.