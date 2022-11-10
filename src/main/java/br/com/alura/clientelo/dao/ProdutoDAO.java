package br.com.alura.clientelo.dao;

import br.com.alura.clientelo.models.Produto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProdutoDAO implements DAO<Long, Produto> {
    private final EntityManager em;

    public ProdutoDAO(EntityManager em) {
        this.em = em;
    }

    public void cadastra(Produto produto) {
        runWithinTransaction(() -> {em.persist(produto);});
    }

    public Produto buscaPorId(Long id) {
        return em.find(Produto.class, id);
    }

    public void atualiza(Produto produto) {
        runWithinTransaction(() -> {em.merge(produto);});
    }

    public List<Produto> listaTodos() {
        String query = "SELECT p from Produto p";
        return em.createQuery(query, Produto.class).getResultList();
    }

    public List<Produto> listaIndisponiveis() {
        String query = "SELECT p from Produto p WHERE p.quantidadeEmEstoque = 0";
        return em.createQuery(query, Produto.class).getResultList();
    }

    public List<Produto> maisVendidos() {
        String query = "SELECT p from Produto p " +
                "JOIN ItemPedido ip on ip.produto = p " +
                "GROUP BY p.id HAVING SUM(ip.quantidade) > 3";
        return em.createQuery(query, Produto.class).getResultList();
    }

    private void runWithinTransaction(Runnable callback) {
        em.getTransaction().begin();
        callback.run();
        em.getTransaction().commit();
    }
}
