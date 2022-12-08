package br.com.alura.clientelo;

import br.com.alura.clientelo.dao.CategoriaDAO;
import br.com.alura.clientelo.dao.ProdutoDAO;
import br.com.alura.clientelo.models.Categoria;
import br.com.alura.clientelo.models.CategoriaStatus;
import br.com.alura.clientelo.models.Produto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class TestaProdutoDAO implements CommandLineRunner {
    private CategoriaDAO categoriaDAO;
    private ProdutoDAO produtoDAO;

    public TestaProdutoDAO(CategoriaDAO categoriaDAO, ProdutoDAO produtoDAO) {
        this.categoriaDAO = categoriaDAO;
        this.produtoDAO = produtoDAO;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestaProdutoDAO.class);
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
        Categoria categoria = new Categoria(nome);
        return categoria;
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria celulares = novaCategoria("CELULARES");
        Categoria info = novaCategoria("INFORMATICA");

        categoriaDAO.cadastra(celulares);
        categoriaDAO.cadastra(info);

        celulares = categoriaDAO.buscaPorNome("CELULARES");
        info = categoriaDAO.buscaPorNome("INFORMATICA");

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

        limpaBanco();
    }

    private void limpaBanco() {
        produtoDAO.findAll().forEach(produtoDAO::remove);
        categoriaDAO.findAll().forEach(categoriaDAO::remove);
    }
}
