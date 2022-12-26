package br.com.alura.clientelo.relatorios.repositories;

import br.com.alura.clientelo.relatorios.estatisticas.PedidoDTO;

import java.util.Collection;

public interface EstatisticasRepository<T> {


    Collection<T> getAll();

    void insert(PedidoDTO pedido);

    default int size() {
        return getAll().size();
    }
}
