package model.dao;

import db.DB;
import model.dao.impl.*;
import model.entities.Empresa;
import model.entities.Perguntas;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	public static UsuarioDao createUsuarioDao() {return new UsuarioDaoJDBC(DB.getConnection());}
	public static EmpresaDao createEmpresaDao(){
		return new EmpresaDaoJDBC(DB.getConnection());
	}
	public static PerguntasDao createPerguntasDao(){
		return new PerguntasDaoJDBC(DB.getConnection());
	}
}
