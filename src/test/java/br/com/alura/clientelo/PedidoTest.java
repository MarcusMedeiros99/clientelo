package br.com.alura.clientelo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private static Pedido pedido;
    private static Pedido pedidoMaisBarato;
    private static Pedido pedidoMaisCaro;


    @BeforeAll
    static void setup() {
        pedido = new Pedido("categoria","produto", "cliente",
                new BigDecimal("5.00"), 2, LocalDate.now());
        pedidoMaisBarato = new Pedido("categoria","produto", "cliente",
                new BigDecimal("2.50"), 2, LocalDate.now());
        pedidoMaisCaro = new Pedido("categoria","produto", "cliente",
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