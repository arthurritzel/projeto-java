package model.dao.impl;

import model.entities.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.EmpresaDao;
import db.DB;
import db.DbException;



public class EmpresaDaoJDBC implements EmpresaDao {

    private Connection conn;

    public EmpresaDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(Empresa obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO empresa"
                            + "(nome,cnpj)"
                            + "VALUES "
                            + "(?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getCnpj());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Empresa obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE empresa "
                            + "SET nome = ?, cnpj = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getCnpj());

            st.setInt(3, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM empresa WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Empresa findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT *"
                            + "FROM empresa "
                            + "WHERE Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Empresa obj = instantiateEmpresa(rs);
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Empresa instantiateEmpresa(ResultSet rs) throws SQLException {
        Empresa obj = new Empresa();
        obj.setId(rs.getInt("id"));
        obj.setNome(rs.getString("nome"));
        obj.setCnpj(rs.getString("cnpj"));
        return obj;
    }

    @Override
    public List<Empresa> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT *"
                            + "FROM empresa");

            rs = st.executeQuery();

            List<Empresa> list = new ArrayList<>();


            while (rs.next()) {

                Empresa obj = instantiateEmpresa(rs);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    }

