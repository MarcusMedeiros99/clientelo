package br.com.alura.clientelo.relatorios.repositories;

import br.com.alura.clientelo.relatorios.estatisticas.CategoriaEstatisticas;
import br.com.alura.clientelo.relatorios.estatisticas.PedidoDTO;

import java.util.*;

public class CategoriaEstatisticasRepository implements EstatisticasRepository<CategoriaEstatisticas> {
    private final Set<CategoriaEstatisticas> categorias;
    private final Map<String, CategoriaEstatisticas> categoriaToEstatisticas;

    public CategoriaEstatisticasRepository() {
        categorias = new TreeSet<>();
        categoriaToEstatisticas = new HashMap<>();
    }

    @Override
    public Collection<CategoriaEstatisticas> getAll() {
        return Collections.unmodifiableCollection(categorias);
    }

    @Override
    public void insert(PedidoDTO pedido) {
        String categoria = pedido.getCategoria();
        if (!categoriaToEstatisticas.containsKey(categoria)) insertNovoProduto(categoria, pedido);
        else updateCategoria(categoria, pedido);
    }

    private void updateCategoria(String categoria, PedidoDTO pedido) {
        CategoriaEstatisticas categoriaEstatisticas = categoriaToEstatisticas.get(categoria);
        categoriaEstatisticas.adicionaPedido(pedido);
    }

    private void insertNovoProduto(String categoria, PedidoDTO pedido) {
        CategoriaEstatisticas categoriaEstatisticas = new CategoriaEstatisticas(pedido);
        categorias.add(categoriaEstatisticas);
        categoriaToEstatisticas.put(categoria, categoriaEstatisticas);
    }
}
