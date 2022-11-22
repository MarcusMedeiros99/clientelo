package br.com.alura.clientelo;

import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.dao.PedidoDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.dao.vo.ClienteFielVO;
import br.com.alura.clientelo.dao.vo.VendasPorCategoriaVO;
import br.com.alura.clientelo.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TestaPedidoDAO implements CommandLineRunner {
    private final PedidoDAO pedidoDAO;
    private final ClienteDAO clienteDAO;
    private final CategoriaDAO categoriaDAO;
    private final ProdutoDAO produtoDAO;

    public TestaPedidoDAO(PedidoDAO pedidoDAO, ClienteDAO clienteDAO, CategoriaDAO categoriaDAO, ProdutoDAO produtoDAO) {
        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
        this.categoriaDAO = categoriaDAO;
        this.produtoDAO = produtoDAO;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestaPedidoDAO.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        populaBanco();
        relatorio();
        limpaBanco();
    }

    private void relatorio() {
        List<Pedido> pedidos = pedidoDAO.listaTodos();
        System.out.println("TODOS OS PEDIDOS");
        pedidos.forEach(System.out::println);


        List<VendasPorCategoriaVO> vendasPorCategoria = categoriaDAO.agrupaPorCategoria();
        System.out.println("VENDAS POR CATEGORIA");
        vendasPorCategoria.forEach(System.out::println);

        List<ClienteFielVO> clientesFieis = clienteDAO.clientesFieis(ClienteDAO.clientesFieisPage);
        System.out.println("CLIENTES FIEIS");
        clientesFieis.forEach(System.out::println);

        List<Produto> maisVendidos = produtoDAO.maisVendidos();
        System.out.println("PRODUTOS MAIS VENDIDOS");
        maisVendidos.forEach(System.out::println);

        List<ClienteFielVO> maisLucrativos = clienteDAO.maisLucrativos(ClienteDAO.maisLucrativosPage);
        System.out.println("CLIENTES MAIS LUCRATIVOS");
        maisLucrativos.forEach(System.out::println);
    }

    private Cliente createCliente(int i) {
        Cliente cliente = new Cliente();
        cliente.setNome("cliente legal" + i);
        cliente.setCpf("1111111111" + i);
        cliente.setTelefone("9999999999");

        Address address = new Address();
        address.setCidade("Manaus");
        address.setBairro("bom");
        address.setRua("legal");
        address.setNumero(9);
        address.setEstadoUF("AM");

        cliente.setAddress(address);

        return cliente;
    }

    private static Categoria criaCategoria(String nome) {
        Categoria categoria = new Categoria();
        categoria.setStatus(CategoriaStatus.ATIVA);
        categoria.setNome(nome);

        return categoria;
    }

    private static Produto criaProduto(String nome, Categoria categoria) {
        Produto produto = new Produto();
        produto.setPreco(BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP));
        produto.setDescricao("produto bom");
        produto.setCategoria(categoria);
        produto.setQuantidadeEmEstoque(20);
        produto.setNome(nome);

        return produto;
    }

    private static Pedido criaPedido(Cliente cliente, Produto produto, Long quantidade) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setData(LocalDate.now());
        pedido.setDesconto(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        pedido.setTipoDesconto(TipoDescontoPedido.NENHUM);


        List<ItemPedido> itemPedidos = new ArrayList<>();
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedido(pedido);



        itemPedido.setProduto(produto);
        itemPedido.setDesconto(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        itemPedido.setQuantidade(quantidade);
        itemPedido.setPrecoUnitario(BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP));
        itemPedido.setTipoDesconto(TipoDescontoItemPedido.NENHUM);
        itemPedidos.add(itemPedido);
        pedido.setItemPedidos(itemPedidos);

        return pedido;
    }

    private void populaBanco() {
        Cliente[] cliente = new Cliente[4];
        for (int i = 0; i < 4; i++) {
            cliente[i] = createCliente(i);
            clienteDAO.cadastra(cliente[i]);
        }
        Categoria celulares = criaCategoria("CELULARES");
        Categoria informatica = criaCategoria("INFORMATICA");
        categoriaDAO.cadastra(celulares);
        categoriaDAO.cadastra(informatica);

        Produto[] produtos = new Produto[3];
        produtos[0] = criaProduto("xiaomi", celulares);
        produtos[1] = criaProduto("iPhone", celulares);
        produtos[2] = criaProduto("notebook dell", informatica);

        produtoDAO.cadastra(produtos[0]);
        produtoDAO.cadastra(produtos[1]);
        produtoDAO.cadastra(produtos[2]);

        Pedido[] pedidos = new Pedido[4];
        for (int i = 0; i < 4; i++) {
            pedidos[i] = criaPedido(cliente[i], produtos[i % 3], (long) i + 1L);
            pedidoDAO.cadastra(pedidos[i]);
        }

    }

    private void limpaBanco() {
        pedidoDAO.findAll().forEach(pedidoDAO::remove);
        produtoDAO.findAll().forEach(produtoDAO::remove);
        categoriaDAO.findAll().forEach(categoriaDAO::remove);
        clienteDAO.findAll().forEach(clienteDAO::remove);
    }

}
