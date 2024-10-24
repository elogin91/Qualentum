package com.calvarez.carregistry.services;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> getAll();

    T get(ID id);

    T update(T item);

    T delete(ID id);

    T add(T item) throws Exception;
}
