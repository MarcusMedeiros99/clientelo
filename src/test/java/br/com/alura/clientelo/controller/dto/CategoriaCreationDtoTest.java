package br.com.alura.clientelo.controller.dto;

import br.com.alura.clientelo.WebApplication;
import br.com.alura.clientelo.core.usecase.categoria.CategoriaCreationDto;
import br.com.alura.clientelo.core.entity.produto.Categoria;
import br.com.alura.clientelo.core.entity.produto.CategoriaStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(CategoriaCreationDtoTest.class)
@ContextConfiguration(classes = WebApplication.class)
class CategoriaCreationDtoTest {

    private static final String NOME_CATEGORIA = "CELULARES";
    private static final String json = "{\"nome\":\"%s\"}".formatted(NOME_CATEGORIA);
    private CategoriaCreationDto form;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() throws Exception {
        form = mapper.readValue(json, CategoriaCreationDto.class);
    }

    @Test
    public void shouldCreateCategoriaComStatusAtiva() {
        Categoria categoria = form.convert();
        assertEquals(CategoriaStatus.ATIVA, categoria.getStatus());
    }

    @Test
    public void shouldCreateCategoriaComNomeCorreto() {
        Categoria categoria = form.convert();
        assertEquals(NOME_CATEGORIA, categoria.getNome());
    }

}