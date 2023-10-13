package model.dao;

import model.entities.Empresa;

import java.util.List;

public interface EmpresaDao {
        void insert(Empresa obj);
        void update(Empresa obj);
        void deleteById(Integer id);
        Empresa findById(Integer id);
        List<Empresa> findAll();

}
