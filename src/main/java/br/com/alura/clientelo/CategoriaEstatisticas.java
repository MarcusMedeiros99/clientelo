package br.com.alura.clientelo;

import java.math.BigDecimal;

public class CategoriaEstatisticas implements Comparable<CategoriaEstatisticas> {
    private BigDecimal montante;
    private String categoria;
    private Integer qtdVendas;

    private CategoriaEstatisticas(String categoria, Integer qtdVendas, BigDecimal montante) {
        this.categoria = categoria;
        this.qtdVendas = qtdVendas;
        this.montante = montante;
    }

    public CategoriaEstatisticas(Pedido pedido) {
        this(pedido.getCategoria(), pedido.getQuantidade(), pedido.getPreco());
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

    public void adicionaPedido(Pedido pedido) {
        this.qtdVendas += pedido.getQuantidade();
        this.montante.add(pedido.getPreco());
    }


}
