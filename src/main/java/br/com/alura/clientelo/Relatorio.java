package br.com.alura.clientelo;

import java.util.List;

public class Relatorio {
    private PedidosEstatisticas pedidosEstatisticas;

    public Relatorio(PedidosEstatisticas pedidosEstatisticas) {
        this.pedidosEstatisticas = pedidosEstatisticas;
    }

    public String produtosMaisVendidos(int n) {
        StringBuffer buffer = new StringBuffer();
        List<ProdutoEstatisticas> produtos = pedidosEstatisticas.produtosMaisVendidos(n);

        for (int i = 0; i < n; i++) {
            ProdutoEstatisticas produto = produtos.get(i);
            buffer.append((i + 1) + "º PRODUTO MAIS VENDIDO: " + produto.getProduto() + "(" + produto.getQtdDeVendas() + " vendas)\n" );
        }
        return buffer.toString();
    }
}
