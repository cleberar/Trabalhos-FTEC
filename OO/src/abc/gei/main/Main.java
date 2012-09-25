package abc.gei.main;

/**
 * XXX TODO :: Faltou as validacoes dos campos
 * 
 * @author  @author cleber Rodrigues <cleber@cleberrodrigues.com.br>
 *
 */
public class Main {

	/**
	 * Main do Projeto
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			// loga no sistema
			LoginScreen login = new LoginScreen();
			
			//  passou pelo login, vai para a home
			if (login.show(null)) {
				HomeScreen home = new HomeScreen();
				home.show();
			}
			
		} catch (Exception e) {
			System.out.println(" ** ATENCAO ** " + e.getMessage());
		}
	}
}
