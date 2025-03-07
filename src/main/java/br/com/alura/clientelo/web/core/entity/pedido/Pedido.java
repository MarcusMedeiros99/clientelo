package br.com.alura.clientelo.web.core.entity.pedido;

import br.com.alura.clientelo.web.core.entity.cliente.Cliente;
import br.com.alura.clientelo.web.core.entity.produto.Produto;

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
    private BigDecimal descontosDeItens;
    @Transient
    private Long quantidadeDeItems;

    public Pedido() {}

    public Pedido(Cliente cliente) {
        this.data = LocalDate.now();
        this.desconto = BigDecimal.ZERO;
        this.tipoDesconto = TipoDescontoPedido.NENHUM;
        setCliente(cliente);
    }

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
        if (cliente.getPedidos().size() >= 5l) {
            setTipoDesconto(TipoDescontoPedido.FIDELIDADE);
        }
    }

    public BigDecimal getDesconto() {
        if (TipoDescontoPedido.FIDELIDADE.equals(tipoDesconto))
            return calculaDescontoFidelidade();

        return BigDecimal.ZERO;
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

    public void addItemPedido(Produto produto, Long quantidade) {
        ItemPedido itemPedido = new ItemPedido(this,produto,quantidade);
        this.itemPedidos.add(itemPedido);
        this.quantidadeDeItems = this.getQuantidadeDeItens() + itemPedido.getQuantidade();
        itemPedido.setPedido(this);

        if (tipoDesconto.equals(TipoDescontoPedido.FIDELIDADE)) {
            setDesconto(calculaDescontoFidelidade());
        }
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
        return getTotal().subtract(getDesconto()).subtract(getDescontosDeItens());
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
        return getDesconto().add(getDescontosDeItens());
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


    public void addItemPedido(ItemPedido itemPedido) {
        this.itemPedidos.add(itemPedido);
        this.quantidadeDeItems = this.getQuantidadeDeItens() + itemPedido.getQuantidade();

        if (tipoDesconto.equals(TipoDescontoPedido.FIDELIDADE))
            setDesconto(calculaDescontoFidelidade());
        itemPedido.setPedido(this);
    }

    private BigDecimal calculaDescontoFidelidade() {
        return getTotal().subtract(getDescontosDeItens()).multiply(BigDecimal.valueOf(0.05));
    }
}
