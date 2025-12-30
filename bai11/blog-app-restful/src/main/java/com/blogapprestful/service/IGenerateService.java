package com.blogapprestful.service;

import java.util.Optional;

public interface IGenerateService<T> {
    Iterable<T> findAll();
    void save(T t);
    Optional<T> findById(Long id);
    void update(T t);
    void remove(Long id);
}
