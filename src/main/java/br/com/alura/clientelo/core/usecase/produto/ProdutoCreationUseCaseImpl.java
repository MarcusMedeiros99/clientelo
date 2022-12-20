package br.com.alura.clientelo.core.usecase.produto;

import br.com.alura.clientelo.controller.produto.ProdutoCreationUseCase;
import br.com.alura.clientelo.core.entity.produto.Categoria;
import br.com.alura.clientelo.core.entity.produto.Produto;
import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoCreationUseCaseImpl implements ProdutoCreationUseCase {

    @Autowired
    private CategoriaDAO categoriaDao;
    @Autowired
    private ProdutoDAO produtoDao;

    @Override
    public Produto createFrom(ProdutoCreationDto dto) {
        Categoria categoria = categoriaDao.buscaPorId(dto.getCategoriaId());

        Produto produto = new Produto
                .Builder()
                .withNome(dto.getNome())
                .withPreco(dto.getPreco())
                .withDescricao(dto.getDescricao())
                .withQuantidadeEmEstoque(dto.getQuantidadeEmEstoque())
                .withCategoria(categoria)
                .build();

        produtoDao.save(produto);

        return produto;
    }
}
