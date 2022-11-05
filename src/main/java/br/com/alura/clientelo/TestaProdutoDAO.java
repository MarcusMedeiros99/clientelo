package br.com.alura.clientelo;

import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.CategoriaStatus;
import br.com.alura.clientelo.models.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;

public class TestaProdutoDAO {


    public static void main(String[] args) throws Exception {
        try (EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("clientelo");){
            EntityManager em = managerFactory.createEntityManager();

            Categoria celulares = novaCategoria("CELULARES");
            Categoria info = novaCategoria("INFORMATICA");

            CategoriaDAO categoriaDAO = new CategoriaDAO(em);
            categoriaDAO.cadastra(celulares);
            categoriaDAO.cadastra(info);

            celulares = categoriaDAO.buscaPorNome("CELULARES");
            info = categoriaDAO.buscaPorNome("INFORMATICA");

            ProdutoDAO produtoDAO = new ProdutoDAO(em);
            Produto iPhone = novoProduto("iPhone", celulares);
            Produto xiaomi = novoProduto("xiaomi", celulares);
            Produto pcGamer = novoProdutoIndisponivel("pc gamer", info);
            Produto notebook = novoProdutoIndisponivel("notebook", info);

            produtoDAO.cadastra(iPhone);
            produtoDAO.cadastra(xiaomi);
            produtoDAO.cadastra(pcGamer);
            produtoDAO.cadastra(notebook);

            produtoDAO.listaTodos().forEach(System.out::println);
            produtoDAO.listaIndisponiveis().forEach(System.out::println);

            em.close();
        }
    }

    private static Produto novoProduto(String nome, Categoria categoria) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setCategoria(categoria);
        produto.setDescricao("produto bom");
        produto.setQuantidadeEmEstoque(999);
        produto.setPreco(new BigDecimal("10.0"));
        return produto;
    }

    private static Produto novoProdutoIndisponivel(String nome, Categoria categoria) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(new BigDecimal("10.0"));
        produto.setCategoria(categoria);
        produto.setDescricao("produto ruim");
        produto.setQuantidadeEmEstoque(0);
        return produto;
    }

    private static Categoria novaCategoria(String nome) {
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoria.setStatus(CategoriaStatus.ATIVA);
        return categoria;
    }
}
