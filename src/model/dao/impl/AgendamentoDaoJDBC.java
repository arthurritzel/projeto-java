package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.AgendamentoDao;
import model.entities.Agendamento;
import model.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendamentoDaoJDBC implements AgendamentoDao {

    private final Connection conn;

    public AgendamentoDaoJDBC(Connection conn){
        this.conn = conn;
    }

    private Agendamento instantiateAgenda(ResultSet rs, Usuario usu) throws SQLException{
        Agendamento obj = new Agendamento();
        obj.setId(rs.getInt("id"));
        obj.setDia(rs.getString("dia"));
        obj.setHora(rs.getString("horario"));
        obj.setFeito(rs.getBoolean("feito"));
        obj.setUsuario(usu);
        return obj;
    }
    private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
        Usuario obj = new Usuario();
        obj.setId(rs.getInt("id_usuario"));
        obj.setNome(rs.getString("UsuName"));
        obj.setCpf(rs.getString("UsuCpf"));
        return obj;
    }
    @Override
    public void insert(Agendamento obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO agendamento "
                            + "(dia, horario, feito, id_usuario) "
                            + "VALUES "
                            + "(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getDia());
            st.setString(2, obj.getHora());
            st.setBoolean(3, obj.isFeito());
            st.setInt(4, obj.getUsuario().getId());



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
    public void update(Agendamento obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE agendamento" +
                            " SET dia = ?, horario = ?, feito = ?, id_usuario = ? " +
                            "WHERE Id = ?");

            st.setString(1, obj.getDia());
            st.setString(2, obj.getHora());
            st.setBoolean(3, obj.isFeito());
            st.setInt(4, obj.getUsuario().getId());
            st.setInt(5, obj.getId());

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
            st = conn.prepareStatement("DELETE FROM agendamento WHERE Id = ?");

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
    public Agendamento findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT agendamento.*, usuario.nome as UsuName, usuario.cpf as UsuCpf " +
                            "FROM agendamento INNER JOIN usuario " +
                            "ON agendamento.id_usuario = usuario.id " +
                            "WHERE agendamento.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()){
                Usuario usu = instantiateUsuario(rs);
                Agendamento obj = instantiateAgenda(rs, usu);
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
    public List<Agendamento> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
    try {
        st = conn.prepareStatement(
                "SELECT agendamento.*, usuario.nome as UsuName, usuario.cpf as UsuCpf " +
                        "FROM agendamento INNER JOIN usuario " +
                        "ON agendamento.id_usuario = usuario.id ");

        rs = st.executeQuery();

        List<Agendamento> list = new ArrayList<>();
        Map<Integer, Usuario> map = new HashMap<>();

        while (rs.next()) {
            Usuario usu = map.get(rs.getInt("id_usuario"));

            if (usu == null) {
                usu = instantiateUsuario(rs);
                map.put(rs.getInt("id_usuario"), usu);
            }

            Agendamento obj = instantiateAgenda(rs, usu);
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
