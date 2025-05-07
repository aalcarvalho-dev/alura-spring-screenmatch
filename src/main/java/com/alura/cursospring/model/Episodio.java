package com.alura.cursospring.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {

    private Integer temporada;
    private String titulo;
    private Integer numero;
    private Double avaliacao;
    private LocalDate dataLancameto;
    
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

    public Integer getTemporada() {
        return temporada;
    }
    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public Double getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
    public LocalDate getDataLancameto() {
        return dataLancameto;
    }
    public void setDataLancameto(LocalDate dataLancameto) {
        this.dataLancameto = dataLancameto;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada + ", titulo=" + titulo + ", numero=" + numero + ", avaliacao="
                + avaliacao + ", dataLancameto=" + dataLancameto;
    }
    
}