package br.com.alura.clientelo.controller;

import br.com.alura.clientelo.controller.produto.AllProdutosDto;
import br.com.alura.clientelo.controller.produto.ProdutoDto;
import br.com.alura.clientelo.controller.produto.ProdutoResumeDto;
import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.core.entity.produto.Categoria;
import br.com.alura.clientelo.core.entity.produto.Produto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProdutoControllerTest {

    private static final String nomeProduto = "nomeProduto";
    private static final BigDecimal precoProduto = BigDecimal.TEN;
    private static final String descProduto = "";
    private static final Integer estoqueProduto = 10;
    private static final int UNEXISTING_ID = 9999;
    private Long produto1Id;
    private Long produto2Id;
    private Long categoriaId;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProdutoDAO produtoDAO;
    @Autowired
    private CategoriaDAO categoriaDAO;
    private ObjectMapper mapper = new ObjectMapper();


    @BeforeAll
    @Transactional
    public void setup() {
        Categoria categoria = new Categoria("categoria");
        categoriaDAO.save(categoria);
        categoriaId = categoria.getId();
        Produto produto1 = new Produto(nomeProduto, categoria, precoProduto, descProduto, estoqueProduto);
        Produto produto2 = new Produto(nomeProduto + 2, categoria, precoProduto, descProduto, estoqueProduto);
        produtoDAO.save(produto1);
        produtoDAO.save(produto2);
        produto1Id = produto1.getId();
        produto2Id = produto2.getId();
    }

    @Test
    public void shouldFindProdutoById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/produtos/" + produto1Id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        ProdutoDto produtoResponse = mapper.readValue(response, new TypeReference<ProdutoDto>() {});
        Long id = produtoResponse.getId();

        assertEquals(produto1Id, id);
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/produtos/" + UNEXISTING_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldGetAllProdutos() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/produtos/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        AllProdutosDto produtoResponse = mapper.readValue(response, new TypeReference<AllProdutosDto>() {});
        Collection<Long> produtosIds = produtoResponse
                .getProdutos()
                .stream()
                .map(ProdutoResumeDto::getId)
                        .collect(Collectors.toList());

        assertTrue(produtosIds.contains(produto1Id));
        assertTrue(produtosIds.contains(produto2Id));
    }

    @Test
    public void shouldCadastrarProduto() throws Exception {
        String json = """
                {
                	"nome": "iPhone",
                	"preco": 9.99,
                	"descricao": "celular de rico",
                	"quantidadeEmEstoque": 50,
                	"categoriaId": 
                """ + categoriaId + "}";

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/produtos/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isCreated())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        ProdutoDto produtoDto = mapper.readValue(responseContent, ProdutoDto.class);

        Optional<Produto> produto = produtoDAO.findById(produtoDto.getId());
        assertTrue(produto.isPresent());
    }

    @Test
    public void shouldReturnForbiddenWhenCreateWithUnexistingCategory() throws Exception {
        String json = """
                {
                	"nome": "iPhone",
                	"preco": 9.99,
                	"descricao": "celular de rico",
                	"quantidadeEmEstoque": 50,
                	"categoriaId": 
                """ + UNEXISTING_ID + "}";

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/produtos/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isForbidden())
                .andReturn();
    }
}