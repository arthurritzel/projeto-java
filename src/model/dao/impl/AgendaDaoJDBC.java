package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.AgendaDao;
import model.entities.Agenda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDaoJDBC  implements AgendaDao{

    private final Connection conn;

    public AgendaDaoJDBC (Connection conn){
        this.conn = conn;
    }

    private Agenda instantiateAgenda(ResultSet rs) throws SQLException{
        Agenda obj = new Agenda();
        obj.setId(rs.getInt("id"));
        obj.setDia(rs.getString("dia"));
        obj.setHora(rs.getString("hora"));
        obj.setOk(rs.getBoolean("ok"));
        return obj;
    }

    @Override
    public  void insert(Agenda obj) throws SQLException {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("INSERT INTO Agenda" +
                    "(dia,hora,ok)" +
                    "VALUES " +
                    "(??)",

                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getDia());
            st.setString(2, obj.getHora());
            st.setString(3, String.valueOf(obj.isOk()));

            int rowsAffected;
            rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected!");
            } else {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Agenda obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE Agenda" +
                            "SET data = ?, hora = ?, ok = ?" +
                            "WHERE Id = ?");

            st.setString(1, obj.getDia());
            st.setString(2, obj.getHora());
            st.setString(3, String.valueOf(obj.isOk()));

            st.executeUpdate();
        }
        catch (SQLException e){
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
            st = conn.prepareStatement("DELETE FROM Agenda WHERE Id = ?");

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
    public Agenda findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT *" +
                            "FROM Agenda" +
                            "WHERE Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()){
                Agenda obj = instantiateAgenda(rs);
                return obj;
            }
            return null;
        }
        catch (SQLException e){
                throw new DbException(e.getMessage());
            }

        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Agenda> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
    try {
        st = conn.prepareStatement(
                "SELECT *" +
                        "FROM Agenda");

        rs = st.executeQuery();

        List<Agenda> list = new ArrayList<>();

        while (rs.next()) {
            Agenda obj = instantiateAgenda(rs);
            list.add(obj);
        }
        return list;
    }
    catch (SQLException e){
        throw new DbException(e.getMessage());
    }
    finally {
        DB.closeStatement(st);
        DB.closeResultSet(rs);
    }
}
}
