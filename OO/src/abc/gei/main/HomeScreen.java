package abc.gei.main;

/**
 * Classe para criacao da tela de Home
 * 
 *  @author cleber Rodrigues <cleber@cleberrodrigues.com.br>
 *
 */
class HomeScreen extends SystemScreen {

	/**
	 * Exibe a tela de Home
	 * 
	 * @return void
	 */
	public void show() {

		// cria o cabecalho
		this.header();

		// opcap
		String option = null;
		do {
			
			// cria menu de opcoes
			option = this.menu();
			
			// conforme a opcao escolhida, exibimos a tela
			switch (option) {
			
				case "1":
					UserScreen uNew = new UserScreen();
					uNew.insert();
					break;

				case "2":
					UserScreen uList = new UserScreen();
					uList.list();
					break;
					
				case "3":
					UserScreen uStatus = new UserScreen();
					uStatus.status();
					break;

					
				case "4":
					SectorScreen sNew = new SectorScreen();
					sNew.insert();
					break;
				
				case "5":
					SectorScreen sList = new SectorScreen();
					sList.list();
					break;

				case "6":
					SectorScreen sStatus= new SectorScreen();
					sStatus.status();
					
				case "7":
					SectorScreen sUser= new SectorScreen();
					sUser.associate();
					break;

				case "0":
					System.out.close();
					break;

				default:
					System.out.println();
					System.out.println("==> Opcao invalida");
					break;
			}
			
			
		} while (option != null && option != "0");
		
		// cria o roda
		this.footer();
	}
	
	private String menu() {
		
		System.out.println("==> Menu");
		for (int i = 0; i < 85; ++i) {  
		    System.out.print("-");  
		}
		System.out.println();
		System.out.println("-> 1. Criar Usuario");
		System.out.println("-> 2. Listar Usuarios");
		System.out.println("-> 3. Desativar / Ativar Usuarios");
		
		System.out.println();
		for (int i = 0; i < 85; ++i) {  
		    System.out.print("-");  
		}
		System.out.println();
		System.out.println("-> 4. Criar Setor");
		System.out.println("-> 5. Listar Setor");
		System.out.println("-> 6. Ativar / Desativar Setor");
		System.out.println("-> 7. Associar Usuario ao Setor");
		System.out.println("-> 0. Sair");

		System.out.println();
		System.out.print("Informe a opcao escolhida: ");
		return SystemScreen.readKeyboard();
	}
}
