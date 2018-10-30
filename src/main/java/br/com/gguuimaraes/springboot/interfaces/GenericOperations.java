package br.com.gguuimaraes.springboot.interfaces;

import org.springframework.http.HttpEntity;

import java.util.List;

public interface GenericOperations<E> {
    HttpEntity<E> create(E entity);

    HttpEntity<E> update(E entity);

    HttpEntity<E> read(long code);

    void delete(long code);

    HttpEntity<List<E>> create(List<E> entities);

    HttpEntity<List<E>> update(List<E> entities);

    HttpEntity<List<E>> read();

    void delete(List<E> entities);
}
