package br.com.alura.clientelo;

import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.models.Address;
import br.com.alura.clientelo.models.Cliente;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TestaClienteDAO implements CommandLineRunner {
    private final ClienteDAO clienteDAO;

    public TestaClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestaPedidoDAO.class, args);
    }

    private static Cliente getCliente(char n) {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente Elo" + n);
        cliente.setCpf("1111111111" + n);
        cliente.setTelefone("9999999999" + n);
        Address address = new Address();
        address.setBairro("bom");
        address.setCidade("legal");
        address.setComplemento("top");
        address.setEstadoUF("AM");
        address.setNumero(99);
        address.setRua("daora");
        cliente.setAddress(address);
        return cliente;
    }

    @Override
    public void run(String... args) throws Exception {
        Cliente cliente1 = getCliente('1');
        Cliente cliente2 = getCliente('2');
        Cliente cliente3 = getCliente('3');

        clienteDAO.cadastra(cliente1);
        clienteDAO.cadastra(cliente2);
        clienteDAO.cadastra(cliente3);

        List<Cliente> clientes = clienteDAO.listaPorNome("Cliente");
        clientes.forEach(System.out::println);

        limpaBanco();
    }

    private void limpaBanco() {
        clienteDAO.findAll().forEach(clienteDAO::remove);
    }
}
