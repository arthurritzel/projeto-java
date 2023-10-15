package model.dao.impl;
import model.dao.PerguntasDao;
import model.entities.Perguntas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.DB;
import db.DbException;

public class PerguntasDaoJDBC implements PerguntasDao {

    private Connection conn;

    public PerguntasDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Perguntas obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO perguntas"
                            + "(cabecalho,tipo_prova,nivel)"
                            + "VALUES "
                            + "(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getCabecalho());
            st.setString(2, obj.getTipo_prova());
            st.setInt(3, obj.getNivel());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Perguntas obj){
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPTADE perguntas"
                            + "SET cabecalho = ?, tipo_prova = ?, nivel = ? "
                            + "WHERE Id = ?");
            st.setString(1, obj.getCabecalho());
            st.setString(2, obj.getTipo_prova());
            st.setInt(3, obj.getNivel());
            st.setInt(4, obj.getId());
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
        st = conn.prepareStatement("DELETE FROM perguntas WHERE Id = ?");

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
    public Perguntas findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT *"
                            + "FROM perguntas "
                            + "WHERE Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Perguntas obj = instantiatePerguntas(rs);
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
    private Perguntas instantiatePerguntas(ResultSet rs) throws SQLException {
        Perguntas obj = new Perguntas();
        obj.setId(rs.getInt("id"));
        obj.setCabecalho(rs.getString("cabecalho"));
        obj.setTipo_prova(rs.getString("tipo_prova"));
        obj.setNivel(rs.getInt("nivel"));
        return obj;
    }
    @Override
    public List<Perguntas> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT *"
                            + "FROM perguntas");

            rs = st.executeQuery();

            List<Perguntas> list = new ArrayList<>();


            while (rs.next()) {

                Perguntas obj = instantiatePerguntas(rs);
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


