package br.com.alura.clientelo;

import java.util.*;

public class PedidosEstatisticas {
    private final Set<ProdutoEstatisticas> produtoEstatisticas;
    private final Map<String, ProdutoEstatisticas> produtos;

    public PedidosEstatisticas() {
        this.produtoEstatisticas = new TreeSet<>();
        produtos = new HashMap<>();
    }

    public void addPedidos(Pedido[] pedidos) {
        for (Pedido pedido:
             pedidos) {
            if (pedido != null)  {
                String produto = pedido.getProduto();
                if (!produtos.containsKey(produto)) insertProduto(pedido, produto);
                else updateProduto(pedido, produto);
            }
        }
    }

    private void updateProduto(Pedido pedido, String produto) {
        ProdutoEstatisticas estatisticas = produtos.get(produto);
        estatisticas.adicionaNVendas(pedido.getQuantidade());
    }

    private void insertProduto(Pedido pedido, String produto) {
        ProdutoEstatisticas estatisticas = new ProdutoEstatisticas(pedido);
        produtoEstatisticas.add(estatisticas);
        produtos.put(produto, estatisticas);
    }

    public List<ProdutoEstatisticas> produtosMaisVendidos(int n) {
        List<ProdutoEstatisticas> maisVendidos = new ArrayList<>(n);

        Iterator<ProdutoEstatisticas> iterator = produtoEstatisticas.iterator();
        int i = 0;
        while (iterator.hasNext() && i < 3) {
            maisVendidos.add(iterator.next());
            i++;
        }

        return maisVendidos;
    }


}
