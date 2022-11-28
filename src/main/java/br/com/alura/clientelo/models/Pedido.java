package br.com.alura.clientelo.models;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "data", nullable = false)
    private LocalDate data;
    @ManyToOne(optional = false)
    private Cliente cliente;
    @Column(name = "desconto", nullable = false, scale = 2)
    private BigDecimal desconto;
    @Column(name = "tipo_desconto", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDescontoPedido tipoDesconto;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itemPedidos = new ArrayList<>();
    @Transient
    private BigDecimal total;
    @Transient
    private BigDecimal descontosDeItens ;
    @Transient
    private Long quantidadeDeItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public TipoDescontoPedido getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(TipoDescontoPedido tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    public List<ItemPedido> getItemPedidos() {
        return itemPedidos;
    }

    public void setItemPedidos(List<ItemPedido> itemPedidos) {
        this.itemPedidos = itemPedidos;
        this.descontosDeItens = itemPedidos.stream().map(ItemPedido::getDesconto).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.total = itemPedidos.stream().map(ItemPedido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.quantidadeDeItems = itemPedidos.stream().map(ItemPedido::getQuantidade).reduce(0l, Long::sum);
    }

    public void addItemPedido(ItemPedido itemPedido) {
        this.itemPedidos.add(itemPedido);
        this.descontosDeItens = this.getDescontosDeItens().add(itemPedido.getDesconto());
        this.total = this.getTotal().add(itemPedido.getTotal());
        this.quantidadeDeItems = this.getQuantidadeDeItens() + itemPedido.getQuantidade();
        itemPedido.setPedido(this);
    }

    public BigDecimal getTotal() {
        if (total == null) {
            this.total = itemPedidos.stream()
                    .filter(itemPedido -> itemPedido != null)
                    .map(ItemPedido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return this.total;
    }

    public BigDecimal getTotalComDesconto() {
        return this.getTotal().subtract(desconto).subtract(this.getDescontosDeItens());
    }

    private BigDecimal getDescontosDeItens() {
        if (descontosDeItens == null) {
            this.descontosDeItens =
                    itemPedidos.stream()
                            .filter(itemPedido -> itemPedido != null)
                            .map(ItemPedido::getDesconto).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return descontosDeItens;
    }

    public BigDecimal getDescontoTotal() {
        return this.desconto.add(getDescontosDeItens());
    }

    public Long getQuantidadeDeItens() {
        if (quantidadeDeItems == null) {
            this.quantidadeDeItems =
                    itemPedidos.stream()
                            .filter(itemPedido -> itemPedido != null)
                            .map(ItemPedido::getQuantidade).reduce(0l, Long::sum);
        }
        return this.quantidadeDeItems;
    }

    @Override
    public String
    toString() {
        return "Pedido{" +
                "id=" + id +
                ", data=" + data +
                ", cliente=" + cliente +
                '}';
    }


}
