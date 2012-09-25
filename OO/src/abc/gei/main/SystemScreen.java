package abc.gei.main;

import java.util.Scanner;

/**
 * Classe com as principais funcoes paraa criacao de telas,
 * que farao a interface com o usuario para utilizacao do sistema
 *  
 * @author Cleber
 */
public abstract class SystemScreen {

	/**
	 * Armazena dados de entrada
	 */
	private static Scanner input;


	/**
	 * Cabecalho das telas
	 * 
	 * @return void
	 */
	protected void header() {
		
		for (int i = 0; i < 50; ++i)  
		    System.out.println ();  
		
		String title = "GEI :: Gestao de Estoques Integrada > ABC";
		
		System.out.println(String.format("[%-80s] \n", title));
	}
	
	/**
	 * Rodape das telas
	 * 
	 * @return void
	 */
	protected void footer() {

		String text = "rodape";
		
		System.out.println(String.format("\n\n [%-80s] \n\n", text));
	}
	
	/**
	 * Rodape das telas
	 * 
	 * @return void
	 */
	protected void error(String errorText) {
		System.out.println(String.format("\n\n [DEU MERDA :: %-80s] \n\n", errorText));
	}
	
	protected void done(String errorText) {
		System.out.println(String.format("\n\n FEITO :: %-80s] \n\n", errorText));
	}
	
	/**
	 * Metodo para ler do Teclado
	 * 
	 * @return String Texto digitado pelo usuario
	 */
	static String readKeyboard() {
		input = new Scanner(System.in);
		return input.next();
	}
}
