package br.com.alura.clientelo;


public class ProdutoEstatisticas implements Comparable<ProdutoEstatisticas> {
    private String produto;
    private Integer qtdDeVendas;
    private String categoria;

    private ProdutoEstatisticas(String produto, Integer qtdDeVendas, String categoria) {
        this.produto = produto;
        this.qtdDeVendas = qtdDeVendas;
        this.categoria = categoria;
    }

    public ProdutoEstatisticas(Pedido pedido) {
        this(pedido.getProduto(), pedido.getQuantidade(), pedido.getCategoria());
    }

    public String getProduto() {
        return produto;
    }

    public Integer getQtdDeVendas() {
        return qtdDeVendas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void adicionaPedido(Pedido pedido) {
        this.qtdDeVendas += pedido.getQuantidade();
    }

    @Override
    public int compareTo(ProdutoEstatisticas produtoEstatisticas) {
        if (produtoEstatisticas != null)
            return -this.qtdDeVendas.compareTo(produtoEstatisticas.qtdDeVendas);
        throw new NullPointerException();
    }
}
