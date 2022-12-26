package br.com.alura.clientelo.web.dao;

import br.com.alura.clientelo.web.core.exceptions.ClienteloEntityNotFoundException;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.ArrayList;
import java.util.List;

@NoRepositoryBean
public interface DAO <K,T> extends PagingAndSortingRepository<T,K>, QueryByExampleExecutor<T>, JpaSpecificationExecutor<T> {

    default void cadastra(T t) {
        save(t);
    }
    default T buscaPorId(K k) {
        return findById(k).orElseThrow(() -> new ClienteloEntityNotFoundException("Id " + k + " não encontrado", Object.class));
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
