package db;

//Exce��o de integridade.
//Motivo: quando fazemos uma dele��o em um banco de dados,
//pode ocorrer de deletarmos a linha de uma tabela que � chave
//estrangeira de outra, isso dar� um problema de integridade.
public class DbIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DbIntegrityException(String msg) {
		super(msg);
	}
}
