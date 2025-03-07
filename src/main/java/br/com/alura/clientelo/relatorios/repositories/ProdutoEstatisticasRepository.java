package br.com.alura.clientelo.relatorios.repositories;

import br.com.alura.clientelo.relatorios.estatisticas.PedidoDTO;
import br.com.alura.clientelo.relatorios.estatisticas.ProdutoEstatisticas;

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
    public void insert(PedidoDTO pedido) {
        String produto = pedido.getProduto();
        if (!produtoToEstatisticas.containsKey(produto)) insertNovoProduto(produto, pedido);
        else updateProduto(produto, pedido);
    }

    private void updateProduto(String produto, PedidoDTO pedido) {
        ProdutoEstatisticas produtoEstatisticas = produtoToEstatisticas.get(produto);
        produtoEstatisticas.adicionaPedido(pedido);
    }

    private void insertNovoProduto(String produto, PedidoDTO pedido) {
        ProdutoEstatisticas produtoEstatisticas = new ProdutoEstatisticas(pedido);
        produtos.add(produtoEstatisticas);
        produtoToEstatisticas.put(produto, produtoEstatisticas);
    }
}
