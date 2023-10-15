package model.dao;

import db.DB;
import model.dao.impl.EmpresaDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {


	public static UsuarioDao createUsuarioDao() {return new UsuarioDaoJDBC(DB.getConnection());}
	public static EmpresaDao createEmpresaDao(){
		return new EmpresaDaoJDBC(DB.getConnection());
	}
}
