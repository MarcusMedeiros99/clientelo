package br.com.alura.clientelo.relatorios;

import br.com.alura.clientelo.relatorios.estatisticas.PedidoDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoDTOTest {

    private static PedidoDTO pedido;
    private static PedidoDTO pedidoMaisBarato;
    private static PedidoDTO pedidoMaisCaro;


    @BeforeAll
    static void setup() {
        pedido = new PedidoDTO("categoria","produto", "cliente",
                new BigDecimal("5.00"), 2, LocalDate.now());
        pedidoMaisBarato = new PedidoDTO("categoria","produto", "cliente",
                new BigDecimal("2.50"), 2, LocalDate.now());
        pedidoMaisCaro = new PedidoDTO("categoria","produto", "cliente",
                new BigDecimal("10.00"), 2, LocalDate.now());
    }

    @Test
    void isMaisBaratoQue() {
        assertFalse(pedido.isMaisBaratoQue(pedidoMaisBarato));
        assertFalse(pedido.isMaisBaratoQue(pedido));
        assertTrue(pedido.isMaisBaratoQue(pedidoMaisCaro));
    }

    @Test
    void isMaisCaroQue() {
        assertTrue(pedido.isMaisCaroQue(pedidoMaisBarato));
        assertFalse(pedido.isMaisCaroQue(pedido));
        assertFalse(pedido.isMaisCaroQue(pedidoMaisCaro));
    }

    @Test
    void getValorTotal() {
        assertEquals(new BigDecimal("10.00"), pedido.getValorTotal());
    }
}