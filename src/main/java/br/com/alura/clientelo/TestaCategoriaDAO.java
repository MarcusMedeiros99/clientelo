package br.com.alura.clientelo;

import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.DAO;
import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.CategoriaStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class TestaCategoriaDAO implements CommandLineRunner {

    private final CategoriaDAO categoriaDAO;

    public TestaCategoriaDAO(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestaCategoriaDAO.class, args);
    }

    private static Categoria novaCategoria(String nome) {
        Categoria categoria = new Categoria(nome);
        return categoria;
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria info = novaCategoria("INFORMATICA");
        Categoria eletro = novaCategoria("ELETRODOMESTICOS");
        Categoria celulares = novaCategoria("CELULARES");

        categoriaDAO.cadastra(info);
        categoriaDAO.cadastra(eletro);
        categoriaDAO.cadastra(celulares);


        categoriaDAO.atualiza(celulares);
        categoriaDAO.listaTodos().forEach(System.out::println);
        limpaBanco();

    }

    private void limpaBanco() {
        categoriaDAO.findAll().forEach(categoriaDAO::remove);
    }
}
