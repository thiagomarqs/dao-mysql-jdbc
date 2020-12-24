package model.dao;

import java.util.List;

import model.entities.Department;

// Interface that specifies methods for operations
// involving the Department entity in the DB.
public interface DepartmentDao {
	
	public int insert(Department department);
	public void update(Department department);
	public void deleteById(Integer id);
	public Department findById(Integer id);
	public List<Department> findAll();

}
