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
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemPedidos = new ArrayList<>();
    @Transient
    private BigDecimal total = BigDecimal.ZERO;
    @Transient
    private BigDecimal descontosDeItens = BigDecimal.ZERO;

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
    }

    public void addItemPedido(ItemPedido itemPedido) {
        this.itemPedidos.add(itemPedido);
        this.descontosDeItens = this.descontosDeItens.add(itemPedido.getDesconto());
        this.total = this.total.add(itemPedido.getTotal());
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public BigDecimal getTotalComDesconto() {
        return this.total.subtract(desconto).subtract(descontosDeItens);
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
