package br.com.alura.clientelo;

import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.dao.UsuarioDAO;
import br.com.alura.clientelo.models.Address;
import br.com.alura.clientelo.models.Cliente;
import br.com.alura.clientelo.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class TestaClienteDAO implements CommandLineRunner {
    private final ClienteDAO clienteDAO;
    private final UsuarioDAO usuarioDAO;
    @Autowired
    private PasswordEncoder encoder;

    public TestaClienteDAO(ClienteDAO clienteDAO, UsuarioDAO usuarioDAO) {
        this.clienteDAO = clienteDAO;
        this.usuarioDAO = usuarioDAO;
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestaPedidoDAO.class, args);
    }

    public Cliente getCliente(char n) {
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

        Usuario usuario = new Usuario("email" + n + "@provider", encoder.encode("123456"));

        cliente.setUsuario(usuario);

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
