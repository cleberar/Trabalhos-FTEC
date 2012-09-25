package abc.gei.main;

import java.util.ArrayList;

import abc.gei.libs.*;

/**
 * Cria as Telas de Usuarios no Sistema
 * 
 * @author cleber Rodrigues <cleber@cleberrodrigues.com.br>
 *
 */
class UserScreen extends SystemScreen {

	/**
	 * Tela para Criar Usuario
	 */
	public void insert() {

		this.header();
				
		System.out.println("Informe seus Dados do Usuario:");

		System.out.print("Nome:");
		String name = SystemScreen.readKeyboard();
		
		System.out.print("E-mail / Login: ");
		String login = SystemScreen.readKeyboard();
				
		System.out.print("Senha: ");
		String password = SystemScreen.readKeyboard();
		
		System.out.print("Deseja Salvar o Usuario " + login + " [S/N]:");
		String isSave = SystemScreen.readKeyboard();
		
		// Se confirmou o Save, instancia a classe de Usuario e adiciona
		if (isSave.toLowerCase().contains("s")) {
			
			try {
				
				User user = new User();
				user.setLogin(login);
				user.setPassword(password);
				user.setName(name);
				user.save();
				
				// cria mensagem de sucesso
				this.done(" Usuario " + login + " Cadastrado com Sucesso.");

			} catch (Exception e) {
				
				// Ocorreo alguma excessao , alerta e volta a tela de Insert
				this.error(e.getMessage());
				this.insert();
			}
		}
	}
	
	public void status() {

		this.header();
				
		System.out.println("Ativar / Desativar Usuario:");

		System.out.print("Login:");
		String login = SystemScreen.readKeyboard();
		
		System.out.print("1=ativa | 0=desativa: ");
		String status = SystemScreen.readKeyboard();
				
		System.out.print("Confirma alteracao de status do usuario " + login + " [S/N]:");
		String isSave = SystemScreen.readKeyboard();
		
		if (isSave.toLowerCase().contains("s")) {
			
			try {
				
				User user = new User(login);
				user.setActive((status == "1"));
				user.save();
				
				this.done(" Usuario " + login + " Alterado com Sucesso.");

			} catch (Exception e) {
				
				// Ocorreo alguma excessao , alerta e volta a tela
				this.error(e.getMessage());
				this.status();
			}
		}
	}
	
	/**
	 * Tela para Listar Usuario
	 * 
	 */
	public void list() {
		
		this.header();
		
		// instancia a classe de usuarios
		User user = new User();
		
		// cria tabela de Usuarios
		this.tableList( user.fetchAll());
		
		// pergunta qual o proximo passo
		System.out.print("Deseja voltar ao menu inicial [S/N]:");
		String isBack = SystemScreen.readKeyboard();
		
		// se o usuario nao quer voltar ao menu, executa a funcao novamente
		if (isBack.toLowerCase().contains("n")) {
			this.list();
		}
	}
	
	public void tableList(ArrayList<User> userAll) {
		
		System.out.println();  
		// Balaca para criar uma Tabela ;)
		System.out.print("  ");  
		for (int i = 0; i < 85; ++i) {  
		    System.out.print("-");  
		}
		
		System.out.println();
		System.out.print(String.format(" | %-20s ", "Login / E-mail"));
		System.out.print(String.format(" | %-20s ", "Nome"));
		System.out.print(String.format(" | %-20s ", "setor"));
		System.out.print(String.format(" | %-10s |","Status"));
		System.out.println();
		
		System.out.print("  ");  
		for (int i = 0; i < 85; ++i) {  
		    System.out.print("-");  
		}
		System.out.println();
	
		// percorre todos os usuarios
		for (User u : userAll) {
			
			System.out.print(String.format(" | %-20s ", u.getId()));
			System.out.print(String.format(" | %-20s ", u.getName()));
			System.out.print(String.format(" | %-20s ", u.getSector()));
			System.out.print(String.format(" | %-10s |", u.isActive() ? "Ativado" : "Desativado"));
			System.out.println();
		}
		
		System.out.print("  ");  
		for (int i = 0; i < 85; ++i) {  
		    System.out.print("-");  
		}
		System.out.println("\n");
	}
}
