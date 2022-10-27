package br.com.alura.clientelo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EstatisticasServiceTest {

    private EstatisticasService estatisticasService;
    private static Pedido[] umPedido;
    private static Pedido[] pedidos;
    private static Pedido[] tresPedidosUmCliente;

    @BeforeAll
    static void setup() {
        pedidos = ProcessadorDeCsv.processaArquivo("pedidos.csv");
        umPedido = new Pedido[]{
                new Pedido("categoria",
                        "produto",
                        "cliente",
                        BigDecimal.ZERO,
                        2,
                        LocalDate.now())
        };
        tresPedidosUmCliente = new Pedido[3];
        for (int i = 0; i < 3; i++) tresPedidosUmCliente[i] = new Pedido(
                "categoria",
                "produto" + i,
                "cliente",
                new BigDecimal("1.00"),
                2,
                LocalDate.now()
        );
    }

    @BeforeEach
    void beforeEach() {
        estatisticasService = new EstatisticasService();
    }

    @Test
    void shouldGenerateEmptyVendasPorCategoriaWhenEmptyOrderList() {
        List<CategoriaEstatisticas> vendasPorCategoria = estatisticasService.vendasPorCategoria();

        assertEquals(vendasPorCategoria.size(), 0);
    }

    @Test
    void shouldGenerateVendasPorCategoria() {
        estatisticasService.addPedidos(pedidos);
        List<CategoriaEstatisticas> vendasPorCategoria = estatisticasService.vendasPorCategoria();

        assertEquals(vendasPorCategoria.size(),5);

        assertEquals(vendasPorCategoria.get(0).getQtdVendas(), 2);
        assertEquals(vendasPorCategoria.get(0).getCategoria(), "AUTOMOTIVA");
        assertEquals(vendasPorCategoria.get(0).getMontante(), new BigDecimal("1987.97"));

        assertEquals(vendasPorCategoria.get(1).getQtdVendas(), 11);
        assertEquals(vendasPorCategoria.get(1).getCategoria(), "CELULARES");
        assertEquals(vendasPorCategoria.get(1).getMontante(), new BigDecimal("97801.50"));

        assertEquals(vendasPorCategoria.get(2).getQtdVendas(), 9);
        assertEquals(vendasPorCategoria.get(2).getCategoria(), "INFORMÁTICA");
        assertEquals(vendasPorCategoria.get(2).getMontante(), new BigDecimal("64698.40"));

        assertEquals(vendasPorCategoria.get(3).getQtdVendas(), 9);
        assertEquals(vendasPorCategoria.get(3).getCategoria(), "LIVROS");
        assertEquals(vendasPorCategoria.get(3).getMontante(), new BigDecimal("1507.64"));

        assertEquals(vendasPorCategoria.get(4).getQtdVendas(), 4);
        assertEquals(vendasPorCategoria.get(4).getCategoria(), "MÓVEIS");
        assertEquals(vendasPorCategoria.get(4).getMontante(), new BigDecimal("12378.98"));
    }

    @Test
    void shouldGenerateMaisVendidos() {
        estatisticasService.addPedidos(pedidos);
        List<ProdutoEstatisticas> maisVendidos = estatisticasService.produtosMaisVendidos(3);

        assertEquals(maisVendidos.size(), 3);

        assertEquals(maisVendidos.get(0).getProduto(),"iPhone 13 Pro");
        assertEquals(maisVendidos.get(0).getQtdDeVendas(),6);

        assertEquals(maisVendidos.get(1).getProduto(),"Galaxy S22 Ultra");
        assertEquals(maisVendidos.get(1).getQtdDeVendas(),5);

        assertEquals(maisVendidos.get(2).getProduto(),"Galaxy Tab S8");
        assertEquals(maisVendidos.get(2).getQtdDeVendas(),4);
    }

    @Test
    void shouldGenerateMaisVendidosFromOneOrder() {
        estatisticasService.addPedidos(umPedido);
        List<ProdutoEstatisticas> maisVendidos = estatisticasService.produtosMaisVendidos(3);

        assertEquals(maisVendidos.size(), 1);

        assertEquals(maisVendidos.get(0).getProduto(),umPedido[0].getProduto());
        assertEquals(maisVendidos.get(0).getQtdDeVendas(),umPedido[0].getQuantidade());
    }

    @Test
    void shouldGenerateMaisCarosPorCategoria() {
        estatisticasService.addPedidos(pedidos);
        List<CategoriaEstatisticas> vendasPorCategoria = estatisticasService.vendasPorCategoria();

        assertEquals(vendasPorCategoria.size(), 5);

        assertEquals(vendasPorCategoria.get(0).getProdutoMaisCaro(), "Jogo de pneus");
        assertEquals(vendasPorCategoria.get(0).getCategoria(), "AUTOMOTIVA");
        assertEquals(vendasPorCategoria.get(0).getPrecoMaisCaro(), new BigDecimal("1276.79"));

        assertEquals(vendasPorCategoria.get(1).getProdutoMaisCaro(), "iPhone 13 Pro");
        assertEquals(vendasPorCategoria.get(1).getCategoria(), "CELULARES");
        assertEquals(vendasPorCategoria.get(1).getPrecoMaisCaro(), new BigDecimal("9176.00"));

        assertEquals(vendasPorCategoria.get(2).getProdutoMaisCaro(), "Macbook Pro 16");
        assertEquals(vendasPorCategoria.get(2).getCategoria(), "INFORMÁTICA");
        assertEquals(vendasPorCategoria.get(2).getPrecoMaisCaro(), new BigDecimal("31752.00"));

        assertEquals(vendasPorCategoria.get(3).getProdutoMaisCaro(), "Building Microservices");
        assertEquals(vendasPorCategoria.get(3).getCategoria(), "LIVROS");
        assertEquals(vendasPorCategoria.get(3).getPrecoMaisCaro(), new BigDecimal("300.28"));

        assertEquals(vendasPorCategoria.get(4).getProdutoMaisCaro(), "Mesa de jantar 6 lugares");
        assertEquals(vendasPorCategoria.get(4).getCategoria(), "MÓVEIS");
        assertEquals(vendasPorCategoria.get(4).getPrecoMaisCaro(), new BigDecimal("3678.98"));
    }

    @Test
    void shouldGenerateMaisCarosPorPedidoFromOneOrder() {
        estatisticasService.addPedidos(umPedido);
        List<CategoriaEstatisticas> vendasPorCategoria = estatisticasService.vendasPorCategoria();

        assertEquals(vendasPorCategoria.size(), 1);

        assertEquals(vendasPorCategoria.get(0).getProdutoMaisCaro(),umPedido[0].getProduto());
        assertEquals(vendasPorCategoria.get(0).getPrecoMaisCaro(),umPedido[0].getPreco());
    }

    @Test
    void shouldGenerateVendasPorCliente() {
        estatisticasService.addPedidos(pedidos);
        List<ClienteEstatisticas> vendasPorCliente = estatisticasService.vendasPorCliente();

        assertEquals(vendasPorCliente.size(), 6);

        assertEquals(vendasPorCliente.get(0).getNome(), "ANA");
        assertEquals(vendasPorCliente.get(0).getQtdPedidos(), 4);

        assertEquals(vendasPorCliente.get(1).getNome(), "DANI");
        assertEquals(vendasPorCliente.get(1).getQtdPedidos(), 4);

        assertEquals(vendasPorCliente.get(2).getNome(), "BIA");
        assertEquals(vendasPorCliente.get(2).getQtdPedidos(), 3);

        assertEquals(vendasPorCliente.get(3).getNome(), "CAIO");
        assertEquals(vendasPorCliente.get(3).getQtdPedidos(), 3);

        assertEquals(vendasPorCliente.get(4).getNome(), "ELI");
        assertEquals(vendasPorCliente.get(4).getQtdPedidos(), 1);

        assertEquals(vendasPorCliente.get(5).getNome(), "GABI");
        assertEquals(vendasPorCliente.get(5).getQtdPedidos(), 1);
    }

    @Test
    void shouldGenerateVendasPorClienteWhen3PedidosAnd1Cliente() {
        estatisticasService.addPedidos(tresPedidosUmCliente);
        List<ClienteEstatisticas> vendasPorCliente = estatisticasService.vendasPorCliente();

        assertEquals(vendasPorCliente.size(), 1);

        assertEquals(vendasPorCliente.get(0).getQtdPedidos(), 3);
        assertEquals(vendasPorCliente.get(0).getNome(), "cliente");
    }

    @Test
    void shouldGenerateClientesMaisLucrativos() {
        estatisticasService.addPedidos(pedidos);
        List<ClienteEstatisticas> vendasPorCliente = estatisticasService.clientesMaisLucrativos(2);

        assertEquals(vendasPorCliente.size(), 2);

        assertEquals(vendasPorCliente.get(0).getNome(), "ANA");
        assertEquals(vendasPorCliente.get(0).getQtdPedidos(), 4);
        assertEquals(vendasPorCliente.get(0).getMontanteGasto(), new BigDecimal("61284.80"));


        assertEquals(vendasPorCliente.get(1).getNome(), "DANI");
        assertEquals(vendasPorCliente.get(1).getQtdPedidos(), 4);
        assertEquals(vendasPorCliente.get(1).getMontanteGasto(), new BigDecimal("54786.40"));
    }

    @Test
    void shouldGenerateClientesMaisLucrativosWhen3PedidosAnd1Cliente() {
        estatisticasService.addPedidos(tresPedidosUmCliente);
        List<ClienteEstatisticas> clientesMaisLucrativos = estatisticasService.clientesMaisLucrativos(2);

        assertEquals(clientesMaisLucrativos.size(), 1);

        assertEquals(clientesMaisLucrativos.get(0).getQtdPedidos(), 3);
        assertEquals(clientesMaisLucrativos.get(0).getNome(), "cliente");
        assertEquals(clientesMaisLucrativos.get(0).getMontanteGasto(), new BigDecimal("6.00"));

    }

    @Test
    void shouldGetTotalDePedidos() {
        estatisticasService.addPedidos(pedidos);
        Integer totalDePedidos = estatisticasService.totalDePedidos();

        assertEquals(16, totalDePedidos);
    }

    @Test
    void shouldGetTotalDeProdutos() {
        estatisticasService.addPedidos(pedidos);
        Integer totalDeProdutos = estatisticasService.totalDeProdutosVendidos();

        assertEquals(35, totalDeProdutos);
    }

    @Test
    void shouldGetTotalDeCategorias() {
        estatisticasService.addPedidos(pedidos);
        Integer totalDeCategorias = estatisticasService.totalDeCategorias();

        assertEquals(5, totalDeCategorias);
    }

    @Test
    void shouldGetMontanteTotal() {
        estatisticasService.addPedidos(pedidos);
        BigDecimal montanteTotal = estatisticasService.montanteTotal();

        assertEquals(new BigDecimal("178374.49"), montanteTotal);
    }

    @Test
    void shouldGetPedidoMaisCaro() {
        estatisticasService.addPedidos(pedidos);
        Optional<Pedido> pedidoMaisCaro = estatisticasService.pedidoMaisCaro();

        assertEquals(new BigDecimal("55056.00"), pedidoMaisCaro.get().getValorTotal());
    }

    @Test
    void shouldGetPedidoMaisBarato() {
        estatisticasService.addPedidos(pedidos);
        Optional<Pedido> pedidoMaisBarato = estatisticasService.pedidoMaisBarato();

        assertEquals(new BigDecimal("95.17"), pedidoMaisBarato.get().getValorTotal() );
    }
}
