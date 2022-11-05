package br.com.alura.clientelo.dao;

import java.util.List;

public interface DAO <K,T>{

    void cadastra(T t);
    T buscaPorId(K k);
    void atualiza(T t);
    List<T> listaTodos();

}
