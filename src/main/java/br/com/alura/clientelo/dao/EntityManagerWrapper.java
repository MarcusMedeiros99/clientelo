package br.com.alura.clientelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

class EntityManagerWrapper implements AutoCloseable {
    private static EntityManager em;

    EntityManagerWrapper() {}

    EntityManager getEntityManager() {
        if (em == null) {
            em = Persistence.createEntityManagerFactory("clientelo").createEntityManager();
        }
        return em;
    }

    @Override
    public void close() throws Exception {
        em.close();
    }
}
