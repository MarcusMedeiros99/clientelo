package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.dao.vo.VendasPorCategoriaVO;
import br.com.alura.clientelo.models.Categoria;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CategoriaDAO implements DAO<Long, Categoria> {
    private final EntityManager em;

    public CategoriaDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastra(Categoria categoria) {
        runWithinTransaction(() -> {em.persist(categoria);});
    }

    public Categoria buscaPorId(Long id) {
        return em.find(Categoria.class, id);
    }

    public void atualiza(Categoria categoria) {
        runWithinTransaction(() -> {em.merge(categoria);});
    }

    public List<Categoria> listaTodos() {
        String query = "SELECT c from Categoria c";
        return em.createQuery(query, Categoria.class).getResultList();
    }

    public Categoria buscaPorNome(String nome) {
        String query = "SELECT c from Categoria c WHERE c.nome = :nome";
        return em.createQuery(query, Categoria.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }

    public List<VendasPorCategoriaVO> agrupaPorCategoria() {
        String jpql = "SELECT new br.com.alura.clientelo.dao.vo.VendasPorCategoriaVO(" +
                "c.nome, SUM(ip.quantidade), SUM(ip.quantidade * ip.precoUnitario)" +
                ") from " +
                "Pedido p " +
                "JOIN ItemPedido ip " +
                "JOIN Produto pr " +
                "JOIN Categoria c group by c.nome";
        return em.createQuery(jpql, VendasPorCategoriaVO.class).getResultList();
    }

    private void runWithinTransaction(Runnable callback) {
        em.getTransaction().begin();
        callback.run();
        em.getTransaction().commit();
    }

}
