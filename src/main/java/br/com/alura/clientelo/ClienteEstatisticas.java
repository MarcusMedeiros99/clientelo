package br.com.alura.clientelo;

public class ClienteEstatisticas {
    private String nome;
    private Integer qtdPedidos;

    private ClienteEstatisticas(String nome, Integer qtdPedidos) {
        this.nome = nome;
        this.qtdPedidos = qtdPedidos;
    }

    public ClienteEstatisticas(Pedido pedido) {
        this(pedido.getCliente(), pedido.getQuantidade());
    }

    public Integer getQtdPedidos() {
        return this.qtdPedidos;
    }

    public String getNome() {
        return this.nome;
    }

    public void adicionaPedido(Pedido pedido) {
        this.qtdPedidos += pedido.getQuantidade();
    }
}
