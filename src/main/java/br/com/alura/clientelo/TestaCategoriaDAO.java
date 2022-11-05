package br.com.alura.clientelo;

import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.models.Address;
import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.CategoriaStatus;
import br.com.alura.clientelo.models.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TestaCategoriaDAO {


    public static void main(String[] args) throws Exception {
        try (EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("clientelo");){
            EntityManager em = managerFactory.createEntityManager();

            CategoriaDAO categoriaDAO = new CategoriaDAO(em);
            Categoria info = novaCategoria("INFORMATICA");
            Categoria eletro = novaCategoria("ELETRODOMESTICOS");
            Categoria celulares = novaCategoria("CELULARES");

            categoriaDAO.cadastra(info);
            categoriaDAO.cadastra(eletro);
            categoriaDAO.cadastra(celulares);

            celulares.setStatus(CategoriaStatus.INATIVA);

            categoriaDAO.atualiza(celulares);

            categoriaDAO.listaTodos().forEach(System.out::println);

            em.close();
        }
    }

    private static Categoria novaCategoria(String nome) {
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setStatus(CategoriaStatus.ATIVA);
        return categoria;
    }
}
