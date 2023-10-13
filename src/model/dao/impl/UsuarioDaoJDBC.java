package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.dao.UsuarioDao;
import model.entities.Department;
import model.entities.Empresa;
import model.entities.Seller;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao {
    private Connection conn;

    public UsuarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Usuario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO usuario "
                            + "(nome, cpf, id_empresa) "
                            + "VALUES "
                            + "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getCpf());
            st.setInt(3, obj.getEmpresa().getId());



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
    public void update(Usuario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE usuario "
                            + "SET nome = ?, cpf = ?, id_empresa = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getCpf());
            st.setInt(3, obj.getEmpresa().getId());

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
            st = conn.prepareStatement("DELETE FROM usuario WHERE Id = ?");

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
    public Usuario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT usuario.*,empresa.nome as EmpName, empresa.cnpj as EmpCnpj"
                            + "FROM usuario INNER JOIN empresa "
                            + "ON usuario.id_empresa = empresa.Id "
                            + "WHERE usuario.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Empresa emp = instantiateEmpresa(rs);
                Usuario obj = instantiateUsuario(rs, emp);
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

    private Usuario instantiateUsuario(ResultSet rs, Empresa empresa) throws SQLException {
        Usuario obj = new Usuario();
        obj.setId(rs.getInt("id"));
        obj.setNome(rs.getString("nome"));
        obj.setCpf(rs.getString("cpf"));
        obj.setEmpresa(empresa);
        return obj;
    }

    private Empresa instantiateEmpresa(ResultSet rs) throws SQLException {
        Empresa emp = new Empresa();
        emp.setId(rs.getInt("id_empresa"));
        emp.setNome(rs.getString("EmpName"));
        emp.setCnpj(rs.getString("EmpCnpj"));
        return emp;
    }

    @Override
    public List<Usuario> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT usuario.*,empresa.nome as EmpName, empresa.cnpj as EmpCnpj "
                            + "FROM usuario INNER JOIN empresa "
                            + "ON usuario.id_empresa = empresa.Id ");

            rs = st.executeQuery();

            List<Usuario> list = new ArrayList<>();
            Map<Integer, Empresa> map = new HashMap<>();

            while (rs.next()) {
                Empresa emp = map.get(rs.getInt("id_empresa"));

                if (emp == null) {
                    emp = instantiateEmpresa(rs);
                    map.put(rs.getInt("id_empresa"), emp);
                }

                Usuario obj = instantiateUsuario(rs, emp);
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
