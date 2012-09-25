package abc.gei.main;

import abc.gei.libs.*;

/**
 * Classe para criacao da tela de Login
 * 
 * @author cleber
 *
 */
class LoginScreen extends SystemScreen {

	/**
	 * Exibe a tela de Login
	 * 
	 * @return void
	 */
	public boolean show(String message) {

		// cria o cabecalho
		this.header();

		if (message != null) {
			System.out.println(" ** ATENCAO ** " + message);
			System.out.println();
		}
		
		try {

			System.out.println("Informe seus Dados de acesso:");
	
			System.out.print("Setor: ");
			String sector = SystemScreen.readKeyboard();
			
			System.out.print("Login: ");
			String login = SystemScreen.readKeyboard();
					
			System.out.print("Senha: ");
			String password = SystemScreen.readKeyboard();
			
			try {
				
				User user;
				user = new User(login);
				
				if (!user.getPassword().equals(password)) {
					throw new Exception("Senha Invalida");
				}
				
				if (user.getSector() == null || !user.getSector().equals(sector)) {
					throw new Exception("Setor Invalido");
				}
				
				if (!user.isActive()) {
					throw new Exception("Usario Desativado");
				}
				
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

		} catch (Exception e) {
			
			this.show(e.getMessage());
		}
		
		return true;
	}
}
