package br.com.alura.clientelo.web.core.usecase.pedido;

import br.com.alura.clientelo.web.dao.produto.ProdutoDAO;
import br.com.alura.clientelo.web.core.exceptions.ProdutoNotFoundException;
import br.com.alura.clientelo.web.core.entity.pedido.ItemPedido;
import br.com.alura.clientelo.web.core.entity.pedido.Pedido;
import br.com.alura.clientelo.web.core.entity.produto.Produto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class ItemPedidoDto {
    @NotNull
    private Long produtoId;
    @NotNull
    @Min(0)
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
            throw new ProdutoNotFoundException("Produto %d não existe".formatted(produtoId));

        Produto produto = optionalProduto.get();
        
        ItemPedido itemPedido = new ItemPedido(pedido, produto, quantidade);

        return itemPedido;
    }
}
