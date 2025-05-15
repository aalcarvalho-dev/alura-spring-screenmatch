package com.alura.cursospring.desafio;

import java.util.Arrays;
import java.util.List;

public class Desafio3 {

    public static void main(String[] args) {
        List<Integer> listaDeNumeros =  Arrays.asList(5, 6, 7, 8);
        int soma = listaDeNumeros.stream()
                                .peek(n -> System.out.println("elemento: "+n))
                                .map(n->n*2)
                                .peek(n -> System.out.println("multiplicado:"+n))
                                .reduce(0, ((total, controle) -> total + controle));
        System.out.println("soma: "+soma);
    }
}
