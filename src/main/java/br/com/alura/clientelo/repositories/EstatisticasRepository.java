package br.com.alura.clientelo.repositories;

import br.com.alura.clientelo.estatisticas.PedidoDTO;

import java.util.Collection;

public interface EstatisticasRepository<T> {


    Collection<T> getAll();

    void insert(PedidoDTO pedido);

    default int size() {
        return getAll().size();
    }
}
