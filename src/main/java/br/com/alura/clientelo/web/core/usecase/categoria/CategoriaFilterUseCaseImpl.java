package br.com.alura.clientelo.web.core.usecase.categoria;

import br.com.alura.clientelo.web.controller.categoria.CategoriaFilterUseCase;
import br.com.alura.clientelo.web.core.entity.produto.Categoria;
import br.com.alura.clientelo.web.core.entity.produto.CategoriaStatus;
import br.com.alura.clientelo.web.dao.categoria.CategoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaFilterUseCaseImpl implements CategoriaFilterUseCase {

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Override
    public List<Categoria> findBy(String nome, CategoriaStatus status) {
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoriaExample = new Categoria(nome, status);
        ExampleMatcher matcher = buildCategoriaMatcher();

        categoriaDAO.findAll(Example.of(categoriaExample, matcher))
                .forEach(categorias::add);
        return categorias;
    }

    private static ExampleMatcher buildCategoriaMatcher() {
        return ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withMatcher("nome", match -> match.contains());
    }
}
