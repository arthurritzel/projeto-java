package model.dao;

import model.entities.Perguntas;

import java.util.List;

public interface PerguntasDao {
    void insert(Perguntas obj);
    void update(Perguntas obj);
    void deleteById(Integer id);
    Perguntas findById(Integer id);
    List<Perguntas> findAll();

}
