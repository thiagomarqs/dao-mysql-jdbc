package model.dao;

import db.DB;
import model.dao.impl.*;

// Instanciates the DAOs
public class DaoFactory {
	
	// Returns a SellerDao.
	// The implementation of the object returned can be changed 
	// in the future, thus facilitating maintenance.
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
