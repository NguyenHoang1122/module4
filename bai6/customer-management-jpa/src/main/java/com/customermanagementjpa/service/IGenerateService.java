package com.customermanagementjpa.service;

import java.util.List;

public interface IGenerateService<T> {
    List<T> findAll();

    T findById(Long id);
    void save(T t);
    void remove(Long id);
}
