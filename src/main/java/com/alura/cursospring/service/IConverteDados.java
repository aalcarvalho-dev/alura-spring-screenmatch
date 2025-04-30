package com.alura.cursospring.service;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);

}
