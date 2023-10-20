package model.dao;


import model.entities.Respostas;

import java.util.List;

public interface RespostasDao {
    void insert(Respostas obj);
    void update(Respostas obj);
    void deleteById(Integer id);
    Respostas findById(Integer id);
    List<Respostas> findByPer(Integer id);
    List<Respostas> findAll();
}
