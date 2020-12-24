package db;

// Integrity exception
// Reason: when we perform a deletion in a db, 
// we may unintentionally delete a row of a table 
// that is the foreign key of another one, this will
// cause a integrity problem.

public class DbIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DbIntegrityException(String msg) {
		super(msg);
	}
}
