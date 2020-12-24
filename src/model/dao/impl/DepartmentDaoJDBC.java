package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	Connection conn = null;
	
	public DepartmentDaoJDBC(Connection connection) {
		this.conn = connection;
	}

	// Creates a department
	@Override
	public int insert(Department department) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department (Name)"
					+ "VALUES (?) ", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, department.getName());
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Insert completed! Rows affected: " + rowsAffected);
			} else {
				System.out.println("Something went wrong! Rows affected: " + rowsAffected);
			}
			
			ResultSet generatedKey = st.getGeneratedKeys();
			generatedKey.next();
			
			return generatedKey.getInt(1);
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	// Finds a department by id
	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department "
					+ "WHERE Id = ? "
					);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			rs.first();
			return instantiateDepartment(rs);
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
	
	// Finds all departments
	@Override
	public List<Department> findAll() {
		Statement st = null;
		ResultSet rs = null;
		List<Department> departments = new ArrayList<>();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM department");
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);
				departments.add(dep);
			}
			return departments;	
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	// Updates a department
	@Override
	public void update(Department department) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ? "
					);
			st.setString(1, department.getName());
			st.setInt(2, department.getId());
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Update completed! Rows affected: " + rowsAffected);
			} else {
				System.out.println("Something went wrong! Rows affected: " + rowsAffected);
			}
				
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	// Deletes a department by id
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE Id = ? "
					);
			st.setInt(1, id);
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Deletion completed! Rows affected: " + rowsAffected);
			} else {
				System.out.println("Something went wrong! Rows affected: " + rowsAffected);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	// Instantiates a department based on the data of the passed result set
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department(
				rs.getInt("Id"),
				rs.getString("Name")
				);
		return dep;
	}
}
