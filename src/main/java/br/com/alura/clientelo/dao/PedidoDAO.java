package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.dao.vo.VendasPorCategoriaVO;
import br.com.alura.clientelo.models.Pedido;
import br.com.alura.clientelo.models.Produto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PedidoDAO implements DAO<Long, Pedido> {
    EntityManager em;

    public PedidoDAO (EntityManager em) {
        this.em = em;
    }

    @Override
    public void cadastra(Pedido pedido) {
        runWithinTransaction(() -> {
            em.persist(pedido);
            pedido.getItemPedidos()
                    .forEach((itemPedido) -> em.persist(itemPedido));
        });
    }

    @Override
    public Pedido buscaPorId(Long id) {
        return em.find(Pedido.class, id);
    }

    @Override
    public void atualiza(Pedido pedido) {
        runWithinTransaction(() -> {em.merge(pedido);});
    }

    @Override
    public List<Pedido> listaTodos() {
        String query = "SELECT p from Pedido p";
        return em.createQuery(query, Pedido.class).getResultList();
    }

    private void runWithinTransaction(Runnable callback) {
        em.getTransaction().begin();
        callback.run();
        em.getTransaction().commit();
    }
}
