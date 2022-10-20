package br.com.alura.clientelo.repositories;

import br.com.alura.clientelo.Pedido;
import br.com.alura.clientelo.ProdutoEstatisticas;

import java.util.*;

public class ProdutoEstatisticasRepository implements EstatisticasRepository<ProdutoEstatisticas> {
    private final Set<ProdutoEstatisticas> produtos;
    private final Map<String, ProdutoEstatisticas> produtoToEstatisticas;

    public ProdutoEstatisticasRepository() {
        produtos = new TreeSet<>();
        produtoToEstatisticas = new HashMap<>();
    }

    @Override
    public Collection<ProdutoEstatisticas> getAll() {
        return Collections.unmodifiableCollection(produtos);
    }

    @Override
    public void insert(Pedido pedido) {
        String produto = pedido.getProduto();
        if (!produtoToEstatisticas.containsKey(produto)) insertNovoProduto(produto, pedido);
        else updateProduto(produto, pedido);
    }

    private void updateProduto(String produto, Pedido pedido) {
        ProdutoEstatisticas produtoEstatisticas = produtoToEstatisticas.get(produto);
        produtoEstatisticas.adicionaNVendas(pedido.getQuantidade());
    }

    private void insertNovoProduto(String produto, Pedido pedido) {
        ProdutoEstatisticas produtoEstatisticas = new ProdutoEstatisticas(pedido);
        produtos.add(produtoEstatisticas);
        produtoToEstatisticas.put(produto, produtoEstatisticas);
    }
}
