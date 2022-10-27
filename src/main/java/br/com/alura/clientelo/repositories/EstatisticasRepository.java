package br.com.alura.clientelo.repositories;

import br.com.alura.clientelo.Pedido;

import java.util.Collection;

public interface EstatisticasRepository<T> {


    Collection<T> getAll();

    void insert(Pedido pedido);

    default int size() {
        return getAll().size();
    }
}
