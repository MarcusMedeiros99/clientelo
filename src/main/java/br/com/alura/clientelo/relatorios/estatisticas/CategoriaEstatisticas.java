package br.com.alura.clientelo.relatorios.estatisticas;

import java.math.BigDecimal;

public class CategoriaEstatisticas implements Comparable<CategoriaEstatisticas> {
    private BigDecimal montante;
    private String categoria;
    private Integer qtdVendas;
    private PedidoDTO pedidoComProdutoMaisCaro;

    private CategoriaEstatisticas(String categoria, Integer qtdVendas) {
        this.categoria = categoria;
        this.qtdVendas = qtdVendas;
    }

    public CategoriaEstatisticas(PedidoDTO pedido) {
        this(pedido.getCategoria(), pedido.getQuantidade());
        this.pedidoComProdutoMaisCaro = pedido;
        this.montante = pedido.getValorTotal();
    }

    public String getCategoria() {
        return categoria;
    }

    public Integer getQtdVendas() {
        return qtdVendas;
    }

    public BigDecimal getMontante() {
        return this.montante;
    }

    @Override
    public int compareTo(CategoriaEstatisticas categoriaEstatisticas) {
        if (categoriaEstatisticas != null)
            return this.categoria.compareTo(categoriaEstatisticas.categoria);
        throw new NullPointerException();
    }

    public void adicionaPedido(PedidoDTO pedido) {
        this.qtdVendas += pedido.getQuantidade();
        this.montante = this.montante.add(pedido.getValorTotal());

        if (this.pedidoComProdutoMaisCaro == null ||
                pedido.getPreco().compareTo(this.pedidoComProdutoMaisCaro.getPreco()) > 0) {
            this.pedidoComProdutoMaisCaro = pedido;
        }
    }

    public String getProdutoMaisCaro() {
        if (this.pedidoComProdutoMaisCaro != null)
            return this.pedidoComProdutoMaisCaro.getProduto();

        return null;
    }


    public BigDecimal getPrecoMaisCaro() {
        if (this.pedidoComProdutoMaisCaro != null)
            return this.pedidoComProdutoMaisCaro.getPreco();

        return null;
    }
}
