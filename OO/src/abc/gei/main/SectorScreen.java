package abc.gei.main;

import java.util.ArrayList;

import abc.gei.libs.*;

/**
 * Cria as Telas de Setores no Sistema
 * 
 * @author cleber Rodrigues <cleber@cleberrodrigues.com.br>
 *
 */
class SectorScreen extends SystemScreen {

	/**
	 * Criacao de Setor
	 */
	public void insert() {

		this.header();
				
		System.out.println("Informe seus Dados do Setor:");

		System.out.print("Nome:");
		String name = SystemScreen.readKeyboard();
		
		System.out.print("Login Usuario Responsavel: ");
		String owner = SystemScreen.readKeyboard();
	
		System.out.print("Deseja Salvar o Setor " + name + " [S/N]:");
		String isSave = SystemScreen.readKeyboard();
		
		if (isSave.toLowerCase().contains("s")) {
			
			try {
				
				Sector sector = new Sector();
				sector.setName(name);
				sector.setOwner(new User(owner));
				sector.save();
				
				this.done(" Setor " + name + " Cadastrado com Sucesso.");

			} catch (Exception e) {
				
				// Ocorreo alguma excessao , alerta e volta a tela de Insert
				this.error(e.getMessage());
				this.insert();
			}
		}
	}
	
	public void associate() {
		
		this.header();
		
		System.out.println("Informe os dados de associacaor:");

		System.out.print("Nome Setor:");
		String name = SystemScreen.readKeyboard();
		
		System.out.print("Login Usuario: ");
		String user = SystemScreen.readKeyboard();
		
		System.out.print("Deseja Associar " + user + " o Setor " + name + " [S/N]:");
		String isSave = SystemScreen.readKeyboard();
		
		if (isSave.toLowerCase().contains("s")) {
			
			try {
				
				Sector sector = new Sector(name);
				sector.setUser(new User(user));
				sector.save();
				
				this.done(" Usuario associado com Sucesso.");

			} catch (Exception e) {
				
				// Ocorreo alguma excessao , alerta e volta a tela de Insert
				this.error(e.getMessage());
				this.insert();
			}
		}
	}

	public void status() {

		this.header();
				
		System.out.println("Ativar / Desativar Setor:");

		System.out.print("Nome:");
		String name = SystemScreen.readKeyboard();
		
		System.out.print("1=ativa | 0=desativa: ");
		String status = SystemScreen.readKeyboard();
				
		System.out.print("Confirma alteracao de status do Setor " + name + " [S/N]:");
		String isSave = SystemScreen.readKeyboard();
		
		if (isSave.toLowerCase().contains("s")) {
			
			try {
				
				Sector sector = new Sector(name);
				sector.setActive((status == "1"));
				sector.save();
				
				this.done(" Setor " + name + " Alterado com Sucesso.");

			} catch (Exception e) {
				
				// Ocorreo alguma excessao , alerta e volta a tela
				this.error(e.getMessage());
				this.status();
			}
		}
	}
	
	/**
	 * Tela para Listar Setor
	 * 
	 */
	public void list() {
		
		this.header();
		
		
		// instancia a classe de setor
		Sector sector = new Sector();
		
		UserScreen uScreen = new UserScreen();
		
		// percorre todos os setores
		for (Sector s : sector.fetchAll()) {
			
			System.out.println();
			// Balaca para criar uma Tabela ;)
			System.out.print(" ");  
			for (int i = 0; i < 70; ++i) {  
			    System.out.print("=");  
			}
			System.out.println();
			
			System.out.println(String.format(" |  %-65s | ", " Nome: "  +  s.getName()));
			System.out.print(String.format(" |  %-20s  ", " Status : " + (s.isActive() ? "Ativado" : "Desativado")));
			
			if (s.getOwner() != null) {
				System.out.print(String.format(" %-41s  |", " Responsavel : " + s.getOwner().getId()));
			} else {
				System.out.print(String.format(" %-41s  |", " "));
			}
			
			ArrayList<User> userList = s.getUser();
			if (userList != null) {

				System.out.print(String.format("\n |  %-65s | ", " "));
				System.out.print(String.format("\n |  %-65s | ", " ** Lista de Usuarios Deste Setor : "));
				uScreen.tableList(userList);

			} else {
				
				System.out.println(" ");
				System.out.print(" "); 
				for (int i = 0; i < 70; ++i) {  
				    System.out.print("-");  
				}
				
				System.out.println(String.format("\n |  %-65s | ", " Nenhum usuario associado "));
			}
			
			System.out.print(" ");  
			for (int i = 0; i < 70; ++i) {  
			    System.out.print("=");  
			}
			System.out.println();  
		}
		
		System.out.print("  ");  
		for (int i = 0; i < 70; ++i) {  
		    System.out.print("-");  
		}
		System.out.println("\n");
		
		// pergunta qual o proximo passo
		System.out.print("Deseja voltar ao menu inicial [S/N]:");
		String isBack = SystemScreen.readKeyboard();
		
		// se o usuario nao quer voltar ao menu, executa a funcao novamente
		if (isBack.toLowerCase().contains("n")) {
			this.list();
		}
	}
}
