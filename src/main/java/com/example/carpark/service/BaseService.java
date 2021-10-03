package com.example.carpark.service;

import java.util.Collection;

public interface BaseService<V> {
    Collection<V> getAll();

    V create(V seedDto);

    V findById(String id);

    boolean remove(String id);

    V update(String id, V viewDto);
}