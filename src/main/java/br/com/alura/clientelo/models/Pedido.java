package br.com.alura.clientelo.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "data", nullable = false)
    private LocalDate data;
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @Column(name = "desconto", nullable = false, scale = 2)
    private BigDecimal desconto;
    @Column(name = "tipo_desconto", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDescontoPedido tipoDesconto;
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<ItemPedido> itemPedidos;

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
}
