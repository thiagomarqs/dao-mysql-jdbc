package model.application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

// Testing SellerDaoJDBC
public class Program {

	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("\nTest 1: Inserting new seller");
		Seller newSeller = new Seller("Antônio", "antonio@gmail.com", new Date(), 2000.0, new Department(1));
		sellerDao.insert(newSeller);
		
		System.out.println("\nTest 2: Reading seller by id");
		Seller sellerById = sellerDao.findById(3);
		System.out.println(sellerById);
		
		System.out.println("\nTest 3: Updating seller");
		Seller seller = sellerDao.findById(3);
		seller.setName("Marco");
		sellerDao.update(seller);
		
		System.out.println("\nTest 4: Deleting seller");
		sellerDao.deleteById(13);
		
		System.out.println("\nTest 5: Reading all sellers");
		List<Seller> allSellers = sellerDao.findAll();
		allSellers.stream().forEach(System.out::println);
		
		System.out.println("\nTest 6: Reading sellers by department:");
		List<Seller> sellersByDepartment = sellerDao.findByDepartment("Electronics");
		sellersByDepartment.stream().forEach(System.out::println);
	}

}
