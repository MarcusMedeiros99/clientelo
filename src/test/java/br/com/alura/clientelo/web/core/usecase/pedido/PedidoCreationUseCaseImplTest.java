package br.com.alura.clientelo.web.core.usecase.pedido;

import br.com.alura.clientelo.web.WebApplication;
import br.com.alura.clientelo.web.core.entity.cliente.Cliente;
import br.com.alura.clientelo.web.core.entity.pedido.Pedido;
import br.com.alura.clientelo.web.core.entity.produto.Produto;
import br.com.alura.clientelo.web.core.usecase.pedido.PedidoCreationDto;
import br.com.alura.clientelo.web.core.usecase.pedido.PedidoCreationUseCaseImpl;
import br.com.alura.clientelo.web.dao.cliente.ClienteDAO;
import br.com.alura.clientelo.web.dao.pedido.PedidoDAO;
import br.com.alura.clientelo.web.dao.produto.ProdutoDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(PedidoCreationUseCaseImpl.class)
@ContextConfiguration(classes = WebApplication.class)
class PedidoCreationUseCaseImplTest {
    private static final Long CLIENTE_ID = 32l;
    private static final Long PRODUTO_ID = 31l;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ClienteDAO clienteDAO;
    @MockBean
    private ProdutoDAO produtoDAO;
    @MockBean
    private PedidoDAO pedidoDAO;
    @Mock
    private Cliente cliente;
    @Mock
    private Produto produto;
    @Autowired
    private PedidoCreationUseCaseImpl pedidoCreationUseCaseImpl;

    @BeforeEach
    public void setup() {
        when(cliente.getId()).thenReturn(CLIENTE_ID);
        when(clienteDAO.findById(CLIENTE_ID)).thenReturn(Optional.of(cliente));
        when(produtoDAO.findById(PRODUTO_ID)).thenReturn(Optional.of(produto));
        when(produto.getId()).thenReturn(PRODUTO_ID);
        when(produto.getPreco()).thenReturn(BigDecimal.TEN);
    }

    @Test
    public void shouldCreatePedidoWithItemsAndClient() throws JsonProcessingException {
        String jsonForm = """
                {
                	"clienteId":
                	 """ + CLIENTE_ID +
                """
                 ,
                	"items": [
                		{
                			"produtoId":
                			""" + PRODUTO_ID +
                """
                   ,
                			"quantidade": 11
                		}
                	]
                }
                """;
        PedidoCreationDto form = mapper.readValue(jsonForm, new TypeReference<PedidoCreationDto>() {});
        Pedido pedido = pedidoCreationUseCaseImpl.createPedido(form);

        assertEquals(CLIENTE_ID, pedido.getCliente().getId());
    }
}