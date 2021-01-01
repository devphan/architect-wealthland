package com.cybersoft.service;


import java.util.List;


public interface IService<T> {
    List<T> findAll();
    T findById(Long id);
    void save(T object);
    void remove(Long id);
}
