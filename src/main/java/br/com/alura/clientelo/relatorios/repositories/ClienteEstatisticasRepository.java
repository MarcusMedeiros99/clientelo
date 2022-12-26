package br.com.alura.clientelo.relatorios.repositories;

import br.com.alura.clientelo.relatorios.estatisticas.ClienteEstatisticas;
import br.com.alura.clientelo.relatorios.estatisticas.PedidoDTO;

import java.util.*;

public class ClienteEstatisticasRepository implements EstatisticasRepository<ClienteEstatisticas> {
    private final Set<ClienteEstatisticas> clientes;
    private final Map<String, ClienteEstatisticas> clienteToEstatisticas;

    public ClienteEstatisticasRepository() {
        clientes = new TreeSet<>();
        clienteToEstatisticas = new HashMap<>();
    }

    public ClienteEstatisticasRepository(Comparator<ClienteEstatisticas> comparator) {
        clientes = new TreeSet<>(comparator);
        clienteToEstatisticas = new HashMap<>();
    }

    @Override
    public Collection<ClienteEstatisticas> getAll() {
        return Collections.unmodifiableCollection(clientes);
    }

    @Override
    public void insert(PedidoDTO pedido) {
        String cliente = pedido.getCliente();
        if (!clienteToEstatisticas.containsKey(cliente)) insertNovoCliente(cliente, pedido);
        else updateCliente(cliente, pedido);
    }

    private void updateCliente(String cliente, PedidoDTO pedido) {
        ClienteEstatisticas clienteEstatisticas = clienteToEstatisticas.get(cliente);
        clientes.remove(clienteEstatisticas);
        clienteEstatisticas.adicionaPedido(pedido);
        clientes.add(clienteEstatisticas);
    }

    private void insertNovoCliente(String cliente, PedidoDTO pedido) {
        ClienteEstatisticas clienteEstatisticas = new ClienteEstatisticas(pedido);
        clientes.add(clienteEstatisticas);
        clienteToEstatisticas.put(cliente, clienteEstatisticas);
    }
}
