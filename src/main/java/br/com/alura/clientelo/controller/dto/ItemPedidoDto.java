package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.exceptions.EstoqueInsuficienteException;
import br.com.alura.clientelo.exceptions.ProdutoNotFoundException;
import br.com.alura.clientelo.models.ItemPedido;
import br.com.alura.clientelo.models.Pedido;
import br.com.alura.clientelo.models.Produto;
import br.com.alura.clientelo.models.TipoDescontoItemPedido;

import java.math.BigDecimal;
import java.util.Optional;

public class ItemPedidoDto {
    private Long produtoId;
    private Long quantidade;

    public Long getProdutoId() {
        return produtoId;
    }

    public Long getQuantidade() {
        return quantidade;
    }


    public ItemPedido convert(ProdutoDAO produtoDAO, Pedido pedido) {
        Optional<Produto> optionalProduto = produtoDAO.findById(produtoId);

        if(optionalProduto.isEmpty()) 
            throw new ProdutoNotFoundException("Produto %d não existe".formatted(produtoId), Produto.class);

        Produto produto = optionalProduto.get();
        
        ItemPedido itemPedido = new ItemPedido(pedido, produto, quantidade);

        return itemPedido;
    }
}
