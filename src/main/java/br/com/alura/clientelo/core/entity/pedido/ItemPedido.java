package br.com.alura.clientelo.core.entity.pedido;

import br.com.alura.clientelo.core.entity.produto.Produto;

import javax.persistence.*;

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
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Pedido pedido;
    @ManyToOne(optional = false)
    private Produto produto;
    @Column(name = "desconto", scale = 2, nullable = false)
    private BigDecimal desconto;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_desconto", nullable = false)
    private TipoDescontoItemPedido tipoDesconto;
    @Transient
    private BigDecimal total;

    public ItemPedido() {}

    public ItemPedido(Pedido pedido, Produto produto, Long quantidade) {
        this.pedido = pedido;
        this.desconto = BigDecimal.ZERO;
        this.tipoDesconto = TipoDescontoItemPedido.NENHUM;
        addProduto(produto,quantidade);
    }

    public Produto getProduto() {
        return produto;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }


    public Long getQuantidade() {
        return quantidade;
    }

    private void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
        if (quantidade > 10) this.tipoDesconto = TipoDescontoItemPedido.QUANTIDADE;
    }

    private BigDecimal calculaDescontoQuantidade() {
        return getTotal()
                .multiply(BigDecimal.valueOf(0.10));
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getDesconto() {
        if (tipoDesconto.equals(TipoDescontoItemPedido.QUANTIDADE))
            return calculaDescontoQuantidade();
        return BigDecimal.ZERO;
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

    public BigDecimal getTotal() {
        if (total == null) {
            this.total = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
        }
        return this.total;
    }

    public BigDecimal getTotalComDesconto() {
        return this.getTotal().subtract(desconto);
    }

    public void addProduto(Produto produto, Long quantidade) {
        this.produto = produto;
        this.total = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
        this.precoUnitario = produto.getPreco();
        setQuantidade(quantidade);
        produto.removeDoEstoque(quantidade.intValue());
    }
}
