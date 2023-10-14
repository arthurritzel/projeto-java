package model.dao;

import model.entities.Agenda;

import java.sql.SQLException;
import java.util.List;

public interface AgendaDao {
    void insert(Agenda obj) throws SQLException;
    void update(Agenda obj);
    void deleteById(Integer id);
    Agenda findById(Integer id);
    List<Agenda> findAll();

}
