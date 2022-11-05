package br.com.alura.clientelo.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ITEM_PEDIDO", uniqueConstraints = @UniqueConstraint(columnNames = {"pedido_id", "produto_id"}))
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "preco_unitario", scale = 2, nullable = false)
    private BigDecimal precoUnitario;
    @Column(name = "quantidade", nullable = false)
    private Long quantidade;
    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    @OneToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;
    @Column(name = "desconto", scale = 2, nullable = false)
    private BigDecimal desconto;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_desconto", nullable = false)
    private TipoDescontoItemPedido tipoDesconto;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public TipoDescontoItemPedido getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(TipoDescontoItemPedido tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }
}
