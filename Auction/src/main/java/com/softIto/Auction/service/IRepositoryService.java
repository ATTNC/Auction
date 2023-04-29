package com.softIto.Auction.service;

import java.util.List;

public interface IRepositoryService<T> {
    T getById(Long id);

    List<T> getAll();

    void save(T entity);

    void update(T entity);

    void deleteById(Long id);

}
