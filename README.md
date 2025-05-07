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