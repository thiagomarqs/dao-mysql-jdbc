package db;

//Exceção de integridade.
//Motivo: quando fazemos uma deleção em um banco de dados,
//pode ocorrer de deletarmos a linha de uma tabela que é chave
//estrangeira de outra, isso dará um problema de integridade.
public class DbIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DbIntegrityException(String msg) {
		super(msg);
	}
}
