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