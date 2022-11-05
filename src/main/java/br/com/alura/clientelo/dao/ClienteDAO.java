package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.Cliente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteDAO implements DAO<Long, Cliente> {
    private final EntityManager em;

    public ClienteDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastra(Cliente cliente) {
        runWithinTransaction(() -> {em.persist(cliente);});
    }

    public Cliente buscaPorId(Long id) {
        return em.find(Cliente.class, id);
    }

    public void atualiza(Cliente cliente) {
        runWithinTransaction(() -> {em.merge(cliente);});
    }

    public void remove(Cliente cliente) {
        runWithinTransaction(() -> {em.remove(cliente);});
    }

    public List<Cliente> listaTodos() {
        String query = "SELECT c from Cliente c";
        return em.createQuery(query, Cliente.class).getResultList();
    }

    public List<Cliente> listaPorNome(String nome) {
        String query = "SELECT c from Cliente c WHERE c.nome like :nome";
        return em.createQuery(query, Cliente.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    private void runWithinTransaction(Runnable callback) {
        em.getTransaction().begin();
        callback.run();
        em.getTransaction().commit();
    }

}
