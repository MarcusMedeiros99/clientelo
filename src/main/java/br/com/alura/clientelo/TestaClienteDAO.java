package br.com.alura.clientelo;

import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.ClienteDAO;
import br.com.alura.clientelo.models.Address;
import br.com.alura.clientelo.models.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TestaClienteDAO {


    public static void main(String[] args) throws Exception {
        try (EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("clientelo");){
            EntityManager em = managerFactory.createEntityManager();

            ClienteDAO clienteDAO = new ClienteDAO(em);
            Cliente cliente1 = getCliente('1');
            Cliente cliente2 = getCliente('2');
            Cliente cliente3 = getCliente('3');

            clienteDAO.cadastra(cliente1);
            clienteDAO.cadastra(cliente2);
            clienteDAO.cadastra(cliente3);

            List<Cliente> clientes = clienteDAO.listaPorNome("Cliente");
            clientes.forEach(System.out::println);

            em.close();
        }
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
}
