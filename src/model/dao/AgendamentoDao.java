package model.dao;

import model.entities.Agendamento;

import java.sql.SQLException;
import java.util.List;

public interface AgendamentoDao {
    void insert(Agendamento obj) throws SQLException;
    void update(Agendamento obj);
    void deleteById(Integer id);
    Agendamento findById(Integer id);
    List<Agendamento> findAll();

}
