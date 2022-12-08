package br.com.alura.clientelo.models;

import br.com.alura.clientelo.exceptions.EstoqueInsuficienteException;

import javax.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUTO")
public class Produto {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "preco", nullable = false)
    private BigDecimal preco;
    @Column(name = "nome")
    private String nome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "quantidade_estoque", nullable = false)
    private Integer quantidadeEmEstoque;
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Produto() {}

    public Produto(String nome, Categoria categoria, BigDecimal preco, String descricao, Integer quantidadeEmEstoque) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", preco=" + preco +
                ", nome='" + nome + '\'' +
                ", quantidadeEmEstoque=" + quantidadeEmEstoque +
                ", categoria=" + categoria +
                '}';
    }

    public void removeDoEstoque(Integer quantidade) {
        if (quantidade > quantidadeEmEstoque) {
            throw new EstoqueInsuficienteException("Produto não possui estoque suficiente");
        }
        this.quantidadeEmEstoque = this.quantidadeEmEstoque - quantidade;
    }

    public static class Builder {
        BigDecimal preco;
        String nome;
        String descricao;
        Integer quantidadeEmEstoque;
        Categoria categoria;
        Long id;

        public Produto build() {
            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
            produto.setPreco(preco);
            produto.setCategoria(categoria);
            produto.setId(id);

            return produto;
        }

        public Builder withNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder withDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder withPreco(BigDecimal preco) {
            this.preco = preco;
            return this;
        }

        public Builder withQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
            this.quantidadeEmEstoque = quantidadeEmEstoque;
            return this;
        }

        public Builder withCategoria(Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }
    }
}
