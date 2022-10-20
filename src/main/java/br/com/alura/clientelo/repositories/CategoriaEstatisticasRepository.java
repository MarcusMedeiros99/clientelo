package br.com.alura.clientelo.repositories;

import br.com.alura.clientelo.CategoriaEstatisticas;
import br.com.alura.clientelo.Pedido;

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
    public void insert(Pedido pedido) {
        String categoria = pedido.getCategoria();
        if (!categoriaToEstatisticas.containsKey(categoria)) insertNovoProduto(categoria, pedido);
        else updateCategoria(categoria, pedido);
    }

    private void updateCategoria(String categoria, Pedido pedido) {
        CategoriaEstatisticas categoriaEstatisticas = categoriaToEstatisticas.get(categoria);
        categoriaEstatisticas.adicionaPedido(pedido);
    }

    private void insertNovoProduto(String categoria, Pedido pedido) {
        CategoriaEstatisticas categoriaEstatisticas = new CategoriaEstatisticas(pedido);
        categorias.add(categoriaEstatisticas);
        categoriaToEstatisticas.put(categoria, categoriaEstatisticas);
    }
}
