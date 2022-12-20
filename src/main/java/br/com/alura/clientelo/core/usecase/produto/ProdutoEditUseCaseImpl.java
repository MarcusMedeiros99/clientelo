package br.com.alura.clientelo.core.usecase.produto;

import br.com.alura.clientelo.controller.produto.ProdutoEditUseCase;
import br.com.alura.clientelo.core.entity.produto.Categoria;
import br.com.alura.clientelo.core.entity.produto.Produto;
import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.exceptions.ProdutoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoEditUseCaseImpl implements ProdutoEditUseCase {

    @Autowired
    private CategoriaDAO categoriaDao;
    @Autowired
    private ProdutoDAO produtoDAO;

    @Override
    public Produto edit(Long produtoId, ProdutoEditDto dto) {
        Categoria categoria = categoriaDao.buscaPorId(dto.getCategoriaId());

        Optional<Produto> antigoProduto = produtoDAO.findById(produtoId);
        if (antigoProduto.isEmpty())
            throw new ProdutoNotFoundException("Produto %d não encontrado".formatted(produtoId));

        Produto novoProduto = new Produto
                .Builder()
                .withNome(dto.getNome())
                .withPreco(dto.getPreco())
                .withDescricao(dto.getDescricao())
                .withQuantidadeEmEstoque(dto.getQuantidadeEmEstoque())
                .withCategoria(categoria)
                .withId(produtoId)
                .build();

        produtoDAO.save(novoProduto);

        return novoProduto;
    }
}
