package br.com.alura.clientelo.web.controller.pedido;

import br.com.alura.clientelo.web.WebApplication;
import br.com.alura.clientelo.web.core.usecase.pedido.ItemPedidoDto;
import br.com.alura.clientelo.web.dao.produto.ProdutoDAO;
import br.com.alura.clientelo.web.core.exceptions.ProdutoNotFoundException;
import br.com.alura.clientelo.web.core.entity.pedido.ItemPedido;
import br.com.alura.clientelo.web.core.entity.pedido.Pedido;
import br.com.alura.clientelo.web.core.entity.produto.Produto;
import br.com.alura.clientelo.web.core.entity.pedido.TipoDescontoItemPedido;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ItemPedidoDtoTest.class)
@ContextConfiguration(classes = WebApplication.class)
class ItemPedidoDtoTest {

    private static final Long PRODUTO_ID = 1l;
    private static final String json = "{" +
            "\"produtoId\": %d," +
            "\"quantidade\": %d" +
    "}";
    private final BigDecimal PRECO_UNITARIO = BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP);

    private ItemPedidoDto dto;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ProdutoDAO produtoDAO;
    @MockBean
    private Pedido pedido;
    @MockBean
    private Produto produto;

    @BeforeEach
    public void setup()  {
        when(produtoDAO.findById(PRODUTO_ID)).thenReturn(Optional.of(produto));
        when(produto.getPreco()).thenReturn(PRECO_UNITARIO);
    }

    @Test
    public void deveCriarItemPedidoSemDescontoQuandoQuantidadeMenorQueOnze() throws Exception {
        Integer menorQueOnze = 10;
        dto = mapper.readValue(json.formatted(PRODUTO_ID, menorQueOnze), ItemPedidoDto.class);
        ItemPedido itemPedido = dto.convert(produtoDAO, pedido);

        assertEquals(TipoDescontoItemPedido.NENHUM, itemPedido.getTipoDesconto());
        assertEquals(BigDecimal.ZERO, itemPedido.getDesconto());
    }

    @Test
    public void deveCriarItemPedidoComDescontoQuandoQuantidadeMaiorQueDez() throws Exception {
        int quantidadeMaiorQueDez = 11;
        BigDecimal descontoEsperado = PRECO_UNITARIO
                .multiply(BigDecimal.valueOf(quantidadeMaiorQueDez))
                .multiply(BigDecimal.valueOf(0.10));

        dto = mapper.readValue(json.formatted(PRODUTO_ID, quantidadeMaiorQueDez), ItemPedidoDto.class);
        ItemPedido itemPedido = dto.convert(produtoDAO, pedido);

        assertEquals(TipoDescontoItemPedido.QUANTIDADE, itemPedido.getTipoDesconto());
        assertEquals(descontoEsperado, itemPedido.getDesconto());
    }

    @Test
    public void deveRemoverQuantidadeDoEstoque() throws Exception {
        int quantidade = 2;
        int estoque = 10;
        Produto produtoComEstoque = new Produto
                .Builder()
                .withQuantidadeEmEstoque(10)
                .withId(PRODUTO_ID)
                .withPreco(PRECO_UNITARIO)
                .build();
        when(produtoDAO.findById(PRODUTO_ID)).thenReturn(Optional.of(produtoComEstoque));


        dto = mapper.readValue(json.formatted(PRODUTO_ID, quantidade), ItemPedidoDto.class);
        ItemPedido itemPedido = dto.convert(produtoDAO, pedido);

        assertEquals(estoque - quantidade, produtoComEstoque.getQuantidadeEmEstoque());
    }

    @Test
    public void deveLancarProdutoNotFoundExceptionSeProdutoNaoExiste() throws Exception {
        int quantidade = 10;
        when(produtoDAO.findById(PRODUTO_ID)).thenReturn(Optional.empty());

        dto = mapper.readValue(json.formatted(PRODUTO_ID, quantidade), ItemPedidoDto.class);

        assertThrows(ProdutoNotFoundException.class, () -> dto.convert(produtoDAO, pedido));
    }

}