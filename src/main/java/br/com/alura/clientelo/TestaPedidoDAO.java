package br.com.alura.clientelo;

import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.dao.PedidoDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestaPedidoDAO {

    public static void main(String[] args) {
        try (EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("clientelo");) {
            EntityManager em = managerFactory.createEntityManager();

            PedidoDAO pedidoDAO = new PedidoDAO(em);
            ClienteDAO clienteDAO = new ClienteDAO(em);
            CategoriaDAO categoriaDAO = new CategoriaDAO(em);
            ProdutoDAO produtoDAO = new ProdutoDAO(em);

            Pedido pedido = new Pedido();

            Cliente cliente = new Cliente();
            cliente.setNome("cliente legal");
            cliente.setCpf("11111111111");
            cliente.setTelefone("9999999999");

            Address address = new Address();
            address.setCidade("Manaus");
            address.setBairro("bom");
            address.setRua("legal");
            address.setNumero(9);
            address.setEstadoUF("AM");

            cliente.setAddress(address);

            clienteDAO.cadastra(cliente);

            pedido.setCliente(cliente);
            pedido.setData(LocalDate.now());
            pedido.setDesconto(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            pedido.setTipoDesconto(TipoDescontoPedido.NENHUM);


            List<ItemPedido> itemPedidos = new ArrayList<>();
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            Produto produto = new Produto();
            produto.setPreco(BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP));
            produto.setDescricao("produto bom");

            Categoria categoria = new Categoria();
            categoria.setStatus(CategoriaStatus.ATIVA);
            categoria.setNome("CELULARES");
            categoriaDAO.cadastra(categoria);

            produto.setCategoria(categoria);
            produto.setQuantidadeEmEstoque(20);
            produto.setNome("Xiaomi");
            produtoDAO.cadastra(produto);


            itemPedido.setProduto(produto);
            itemPedido.setDesconto(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            itemPedido.setQuantidade(2L);
            itemPedido.setPrecoUnitario(BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP));
            itemPedido.setTipoDesconto(TipoDescontoItemPedido.NENHUM);
            itemPedidos.add(itemPedido);
            pedido.setItemPedidos(itemPedidos);

            pedidoDAO.cadastra(pedido);
            List<Pedido> pedidos = pedidoDAO.listaTodos();
            pedidos.forEach(System.out::println);

        }
    }
}
