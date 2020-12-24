package model.dao;

import java.util.List;

import model.entities.Seller;

// Interface that specifies methods for operations
// involving the Seller entity in the DB.
public interface SellerDao {
	
	public void insert(Seller seller);
	public void update(Seller seller);
	public void deleteById(Integer id);
	public Seller findById(Integer id);
	public List<Seller> findByDepartment(String department);
	public List<Seller> findAll();

}
