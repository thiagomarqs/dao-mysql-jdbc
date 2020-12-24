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
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

// Implementation of the methods specified in SellerDao
// Interacts with the DB.
public class SellerDaoJDBC implements SellerDao {
	Connection conn = null;
	
	// Dependency injection. 
	// When this class is instanciated, a db connection 
	// must be passed to be used internally.
	public SellerDaoJDBC(Connection newConn) {
		this.conn = newConn;
	}
	
	// Inserts a new seller
	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(
					"INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES (?, ?, ?, ?, ?) ", 
					Statement.RETURN_GENERATED_KEYS
					);
			
			// Filling placeholders with the attributes of the object
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				System.out.println("Insert completed! Rows affected: " + rowsAffected);
			} else {
				throw new DbException("Unexpected error! Rows affected: " + rowsAffected);
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	// Finds seller by id;
	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepartmentName " + 
					"FROM seller INNER JOIN department " + 
					"ON seller.DepartmentId = department.Id " + 
					"WHERE seller.Id = ? "
					);
			st.setInt(1, id); // Sets the first and only placeholder to the informed id
			rs = st.executeQuery();
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);				
				return seller;
			}
			return null;
				
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	// Finds all sellers
	@Override
	public List<Seller> findAll() {
		Statement st = null;
		ResultSet rs = null;
		List<Seller> sellers = new ArrayList<>();
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(
					"SELECT seller.*, department.Name as DepartmentName "
					+ "FROM seller "
					+ "INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY seller.Id ASC ");
			
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
				sellers.add(seller);
			}
			return sellers;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	// Finds all sellers of a department
	@Override
	public List<Seller> findByDepartment(String department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> sellers = new ArrayList<>();
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepartmentName FROM seller " 
					+ "INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE department.Name = ? "
					+ "ORDER BY seller.Id ASC");
			st.setString(1, department);
			rs = st.executeQuery();
			
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
				sellers.add(seller);
			}
			return sellers;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	// Updates a seller
	@Override
	public void update(Seller seller) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ? ", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			st.setInt(6, seller.getId());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				System.out.println("Update completed! Rows affected: " + rowsAffected);
			} else {
				throw new DbException("Unexpected error! Rows affected: " + rowsAffected);
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}
	
	// Deletes seller by id
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(String.format("DELETE FROM seller WHERE Id = %s", id));
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	// Instantiates a seller based on the data of the passed result set
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		return seller;
	}
	
	// Instantiates a department based on the data of the passed result set
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department(
				rs.getInt("DepartmentId"),
				rs.getString("DepartmentName")
				);
		return dep;
	}
}
