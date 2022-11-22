package br.com.alura.clientelo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

@NoRepositoryBean
public interface DAO <K,T> extends PagingAndSortingRepository<T,K> {

    default void cadastra(T t) {
        save(t);
    }
    default T buscaPorId(K k) {
        return findById(k).orElseThrow(() -> new RuntimeException("Id não encontrado"));
    }
    default void atualiza(T t) {
        save(t);
    }

    default List<T> listaTodos() {
        List<T> tList = new ArrayList<>();
        findAll().forEach(tList::add);
        return tList;
    }

    default void remove(T t) {
        delete(t);
    }

}
