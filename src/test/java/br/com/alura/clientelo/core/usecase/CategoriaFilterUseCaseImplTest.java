package br.com.alura.clientelo.core.usecase;

import br.com.alura.clientelo.WebApplication;
import br.com.alura.clientelo.core.entity.produto.Categoria;
import br.com.alura.clientelo.core.entity.produto.CategoriaStatus;
import br.com.alura.clientelo.core.usecase.categoria.CategoriaFilterUseCaseImpl;
import br.com.alura.clientelo.dao.CategoriaDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = WebApplication.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoriaFilterUseCaseImplTest {

    @Autowired
    private CategoriaDAO categoriaDAO;
    @Autowired
    private CategoriaFilterUseCaseImpl categoriaFilter;
    private List<Categoria> categorias = new ArrayList<>();
    private Categoria info;
    private Categoria eletro;
    private Categoria celular;


    @BeforeAll
    void setup() {
        info = new Categoria("INFO");
        categorias.add(info);
        eletro = new Categoria("ELETRO");
        categorias.add(eletro);
        celular = new Categoria("CELULAR", CategoriaStatus.INATIVA);
        categorias.add(celular);

        categoriaDAO.saveAll(categorias);
    }

    @Test
    void shouldFindAllCategoriasWhenNullParameters() {
        List<Categoria> foundCategorias = categoriaFilter.findBy(null, null);

        assertArrayEquals(categorias.toArray(), foundCategorias.toArray());
    }

    @Test
    void shouldFindAllCategoriasAtivas() {

        List<Categoria> foundCategorias = categoriaFilter.findBy(null, CategoriaStatus.ATIVA);

        assertArrayEquals(categorias.stream()
                .filter(categoria -> categoria.getStatus().equals(CategoriaStatus.ATIVA))
                .toArray(), foundCategorias.toArray());
    }

    @Test
    void shouldFindAllCategoriasInativas() {

        List<Categoria> foundCategorias = categoriaFilter.findBy(null, CategoriaStatus.INATIVA);

        assertArrayEquals(categorias.stream()
                .filter(categoria -> categoria.getStatus().equals(CategoriaStatus.INATIVA))
                .toArray(), foundCategorias.toArray());
    }

    @Test
    void shouldFindEletro() {
        List<Categoria> foundCategorias = categoriaFilter.findBy("ELETRO", CategoriaStatus.ATIVA);

        assertTrue(foundCategorias.contains(eletro));
        assertEquals(1, foundCategorias.size());
    }

    @Test
    void shouldFindNothing() {
        List<Categoria> foundCategorias = categoriaFilter.findBy("ELETRO", CategoriaStatus.INATIVA);

        assertEquals(0, foundCategorias.size());
    }
}