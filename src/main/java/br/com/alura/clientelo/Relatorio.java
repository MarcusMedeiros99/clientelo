package br.com.alura.clientelo;

import java.util.List;

public class Relatorio {
    private PedidosEstatisticas pedidosEstatisticas;

    public Relatorio(PedidosEstatisticas pedidosEstatisticas) {
        this.pedidosEstatisticas = pedidosEstatisticas;
    }

    public String produtosMaisVendidos(int n) {
        StringBuffer buffer = new StringBuffer("\n");
        List<ProdutoEstatisticas> produtos = pedidosEstatisticas.produtosMaisVendidos(n);

        for (int i = 0; i < n; i++) {
            ProdutoEstatisticas produto = produtos.get(i);
            buffer.append((i + 1) + "º PRODUTO MAIS VENDIDO: " + produto.getProduto() + "(" + produto.getQtdDeVendas() + " vendas)\n" );
        }
        return buffer.toString();
    }

    public String vendasPorCategoria() {
        StringBuffer buffer = new StringBuffer("\n");
        List<CategoriaEstatisticas> categorias = pedidosEstatisticas.vendasPorCategoria();

        for (int i = 0; i < categorias.size(); i++) {
            CategoriaEstatisticas categoria = categorias.get(i);
            buffer.append("CATEGORIA: " + categoria.getCategoria() + "\n");
            buffer.append("QUANTIDADE VENDIDA: " + categoria.getQtdVendas() + "\n");
            buffer.append("MONTANTE: " + categoria.getMontante() + "\n\n");
        }
        return buffer.toString();
    }
}
