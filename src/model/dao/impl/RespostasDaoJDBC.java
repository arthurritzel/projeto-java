package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.RespostasDao;
import model.entities.Empresa;
import model.entities.Perguntas;
import model.entities.Respostas;
import model.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespostasDaoJDBC implements RespostasDao {
    private Connection conn;

    public RespostasDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Respostas obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO resposta "
                            + "(texto, correta, id_pergunta) "
                            + "VALUES "
                            + "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getTexto());
            st.setBoolean(2, obj.getCorreta());
            st.setInt(3, obj.getPergunta().getId());



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
    public void update(Respostas obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE resposta "
                            + "SET texto = ?, correta = ?, id_pergunta = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getTexto());
            st.setBoolean(2, obj.getCorreta());
            st.setInt(3, obj.getPergunta().getId());

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
            st = conn.prepareStatement("DELETE FROM resposta WHERE Id = ?");

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
    public Respostas findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT resposta.*,perguntas.cabecalho as Cabecalho, perguntas.tipo_prova as TipoProva "
                            + "FROM resposta INNER JOIN perguntas "
                            + "ON resposta.id_pergunta = perguntas.Id "
                            + "WHERE resposta.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Perguntas per = instantiatePergunta(rs);
                Respostas obj = instantiateResposta(rs, per);
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

    private Respostas instantiateResposta(ResultSet rs, Perguntas per) throws SQLException {
        Respostas obj = new Respostas();
        obj.setId(rs.getInt("id"));
        obj.setTexto(rs.getString("texto"));
        obj.setCorreta(rs.getBoolean("correta"));
        obj.setPergunta(per);
        return obj;
    }

    private Perguntas instantiatePergunta(ResultSet rs) throws SQLException {
        Perguntas per = new Perguntas();
        per.setId(rs.getInt("id_pergunta"));
        per.setCabecalho(rs.getString("Cabecalho"));
        per.setTipo_prova(rs.getString("TipoProva"));
        return per;
    }

    @Override
    public List<Respostas> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT resposta.*,perguntas.cabecalho as Cabecalho, perguntas.tipo_prova as TipoProva "
                            + "FROM resposta INNER JOIN perguntas "
                            + "ON resposta.id_pergunta = perguntas.Id ");

            rs = st.executeQuery();

            List<Respostas> list = new ArrayList<>();
            Map<Integer, Perguntas> map = new HashMap<>();

            while (rs.next()) {
                Perguntas per = map.get(rs.getInt("id_pergunta"));

                if (per == null) {
                    per = instantiatePergunta(rs);
                    map.put(rs.getInt("id_pergunta"), per);
                }

                Respostas obj = instantiateResposta(rs, per);
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

    @Override
    public List<Respostas> findByPer(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT resposta.*,perguntas.cabecalho as Cabecalho, perguntas.tipo_prova as TipoProva "
                            + "FROM resposta INNER JOIN perguntas "
                            + "ON resposta.id_pergunta = perguntas.Id "
                            + "WHERE resposta.id_pergunta = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            List<Respostas> list = new ArrayList<>();
            Map<Integer, Perguntas> map = new HashMap<>();

            while (rs.next()) {
                Perguntas per = map.get(rs.getInt("id_pergunta"));

                if (per == null) {
                    per = instantiatePergunta(rs);
                    map.put(rs.getInt("id_pergunta"), per);
                }

                Respostas obj = instantiateResposta(rs, per);
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
