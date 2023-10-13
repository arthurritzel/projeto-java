package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.EmpresaDaoJDBC;
import model.dao.impl.SellerDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;
import model.entities.Empresa;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}

	public static EmpresaDao createEmpresaDao(){
		return new EmpresaDaoJDBC(DB.getConnection());
	}
}
