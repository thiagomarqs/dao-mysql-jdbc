package model.application;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

// Testing DepartmentDaoJDBC
public class Program2 {

	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Scanner sc = new Scanner(System.in);

		System.out.println("\nTEST 1: Inserting department");
		Department department = new Department("Kitchen");
		System.out.println(department);
		int id = departmentDao.insert(department);
		System.out.println("Id: " + id);
		
		System.out.println("\nTEST 2: Finding by id");
		Department department2 = departmentDao.findById(id);
		System.out.println(department2);
		
		System.out.println("\nTEST 3: Updating department");
		System.out.println("Department to update:");
		System.out.println(department2);
		System.out.println("Updated department: ");
		department2.setName("Games");
		System.out.println(department2);
		
		System.out.println("\nTEST 4: Deleting department by id");
		System.out.print("Enter id: ");
		id = sc.nextInt();
		departmentDao.deleteById(id);
		
		System.out.println("\nTEST 5: Finding all departments");
		departmentDao.findAll().stream().forEach(System.out::println);
		
		sc.close();
	}

}
