package br.com.alura.clientelo.web.controller.pedido;

import br.com.alura.clientelo.web.core.entity.cliente.Address;
import br.com.alura.clientelo.web.core.entity.cliente.Cliente;
import br.com.alura.clientelo.web.core.entity.pedido.Pedido;
import br.com.alura.clientelo.web.core.entity.produto.Categoria;
import br.com.alura.clientelo.web.core.entity.produto.Produto;
import br.com.alura.clientelo.web.core.entity.auth.RoleEnum;
import br.com.alura.clientelo.web.core.entity.auth.UsuarioRole;
import br.com.alura.clientelo.web.dao.DAO;
import br.com.alura.clientelo.web.dao.categoria.CategoriaDAO;
import br.com.alura.clientelo.web.dao.cliente.ClienteDAO;
import br.com.alura.clientelo.web.dao.auth.UsuarioRoleDAO;
import br.com.alura.clientelo.web.dao.pedido.PedidoDAO;
import br.com.alura.clientelo.web.dao.produto.ProdutoDAO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PedidoControllerTest {

    private static final String nomeProduto = "nomeProduto";
    private static final BigDecimal precoProduto = BigDecimal.TEN;
    private static final String descProduto = "";
    private static final Integer estoqueProduto = 100;
    private static final Long quantidadePedido = 5l;
    private static final int UNEXISTING_ID = 9999;
    private static final String nomeCliente = "cliente";
    private Produto produto1;
    private Produto produto2;
    private Long categoriaId;
    private Long pedidoId;
    private Pedido pedido;
    private Long clienteId;

    @Autowired
    private CategoriaDAO categoriaDAO;
    @Autowired
    private ProdutoDAO produtoDAO;
    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private UsuarioRoleDAO roleDAO;
    @Autowired
    private PedidoDAO pedidoDAO;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;


    @BeforeAll
    @Transactional
    public void setup() {
        Categoria categoria = createCategoria();
        createProdutos(categoria);
        Cliente cliente = createCliente();
        createPedido(cliente);

        mapper.registerModule(new JavaTimeModule());
    }

    private void createProdutos(Categoria categoria) {
        produto1 = createProduto(nomeProduto, categoria);
        produto2 = createProduto(nomeProduto + 2, categoria);
    }

    private Produto createProduto(String nomeProduto, Categoria categoria) {
        Produto produto = new Produto(nomeProduto, categoria, precoProduto, descProduto, estoqueProduto);
        produtoDAO.save(produto);

        return produto;
    }

    private Categoria createCategoria() {
        Categoria categoria = new Categoria("categoria");
        categoriaDAO.save(categoria);
        categoriaId = categoria.getId();
        return categoria;
    }

    private Cliente createCliente() {
        Address address = new Address();
        address.setCidade("boa");
        address.setNumero(1);
        address.setBairro("legal");
        address.setRua("bonita");
        address.setEstadoUF("SP");

        UsuarioRole role = new UsuarioRole(1, RoleEnum.USUARIO_ROLE);
        roleDAO.save(role);
        Cliente cliente = new Cliente
                .Builder()
                .withNome(nomeCliente)
                .withAddress(address)
                .withCPF("11111111111")
                .withPassword("123")
                .withEmail("a@b.c")
                .withTelefone("11111111111")
                .withRole(role)
                .build();

        clienteDAO.save(cliente);
        clienteId = cliente.getId();

        return cliente;
    }

    private void createPedido(Cliente cliente) {
        pedido = new Pedido(cliente);
        pedido.addItemPedido(produto1, quantidadePedido);

        pedidoDAO.save(pedido);

        pedidoId = pedido.getId();
    }

    @AfterAll
    void cleanUp() {
        pedidoDAO.deleteAll();
        produtoDAO.deleteAll();
        clienteDAO.deleteAll();
        categoriaDAO.deleteAll();
        roleDAO.deleteAll();
    }

    @Test
    public void shouldFindPedidoById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/pedidos/" + pedidoId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        PedidoDetailsDto pedidoResponse = mapper.readValue(response, new TypeReference<PedidoDetailsDto>() {});

        assertEquals(pedido.getCliente().getId(), pedidoResponse.getClienteId());
        assertEquals(pedido.getData(), pedidoResponse.getDataDoPedido());
        assertEquals(pedido.getTotalComDesconto().setScale(2, RoundingMode.HALF_UP),
                pedidoResponse.getValor());
        assertEquals(pedido.getItemPedidos().size(), pedidoResponse.getItems().size());
    }

    @Test
    public void shouldCreatePedido() throws Exception {
        String json = """
                {
                	"clienteId":
                """ + clienteId +
                """
                ,
                	"items": [
                		{
                			"produtoId":""" +  produto1.getId() +
                """
                   ,
                			"quantidade": 1
                		}
                	]
                }
                """;

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/pedidos/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isCreated())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        PedidoDTO pedidoDTO = mapper.readValue(responseContent, PedidoDTO.class);

        Optional<Pedido> pedidoOptional = pedidoDAO.findById(pedidoDTO.getId());
        assertTrue(pedidoOptional.isPresent());
    }

}