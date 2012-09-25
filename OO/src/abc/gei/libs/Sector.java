package abc.gei.libs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import abc.gei.core.*;

/**
 * Classe de Setores
 * 
 * @author cleber Rodrigues <cleber@cleberrodrigues.com.br>
 *
 */
public class Sector {

	private String name;
	private Boolean status;
	private User owner;
	private Persistence saver;
	ArrayList<String> users;
	
	/**
	 * Construtor
	 */
	public Sector() {
		// setor novo deve estar ativado
		this.status = true;
	}
	
	/**
	 * Construtor, recebendo nome
	 * Instancia os dados na classe caso os mesmo
	 * estejam persistidos
	 *
	 *  @param name Nome do Setor
	 *  @return void
	 */
	public Sector(String name) {

		// instancia o nome do setor
		this.name = name;

		// se o metodo de persistencia foi instanciado ainda, o faremos
		if (this.saver == null) {
			this.saver = new Persistence("sector");
		}

		// Obtem os dados do setor instanciado
		HashMap<String, String> data = this.saver.fetch(this.name);

		if (data != null) {
							
			// status == 1 instancia true / 0 = false
			this.status = new Integer(data.get("status")) == 1;
	
			// se existir Usuarios. criamos uma lista de usuarios
			if (data.get("users") != null) {
	
				// XXX FIXME :: Bizarro esta parte, deve ter uma forma melhor de criar uma lista
				//				a partir de uma string em JAVA. A unica forma que consegui foi
				//				explodindo a string pela virgula para isto tive que remover os [] com ER
				this.users = new ArrayList<String>(Arrays.asList(data.get("users").replaceAll("[\\[\\]\\s]", "").split(",")));
			}
			
			if (data.get("owner") != null) {
				this.owner = new User(data.get("owner"));
			}
		}
	}
	
	/**
	 * Metodo que retorna Lista de OBjetos instanciando a Classe Setor
	 * 
	 * @return ArrayList Lista de Setores
	 */
	public ArrayList<Sector> fetchAll() {

		// nao foi instanciada ainda a classe de persistencia, o varemos
		if (this.saver == null) {
			this.saver = new Persistence("sector");
		}
		

		// Obtem lista com todos os setores ja persistidos
		HashMap<String, Object> data = this.saver.fetchAll();
	
		// cria um ArrayList de Setores
		ArrayList<Sector> sectors = new ArrayList<Sector>();
		
		// percorre lista de setores persistidos
		for (Object key : data.keySet()) {
			
			// Instancia a classe de setor adiciona a lista de setores
			sectors.add(new Sector(key.toString()));
		}
		
		return sectors;
	}
	

	/**
	 * Seta novo valor para o Status
	 * 
	 * @param status
	 */
	public void setActive(Boolean status) {
		this.status = status;
	}
	
	/**
	 * Obtem nome do setor
	 * @return String nome do setor
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Obtem nome do setor
	 * @return String nome do setor
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Informa se o setor esta ativo ou nao
	 * @return Boolean 
	 */
	public Boolean isActive() {
		return this.status;
	}

	/**
	 * Adiciona um Objeto de usuario ao Setor
	 * @param user Objeto de um Usuario
	 */
	public void setUser(User user) {
		
		if (this.users == null) {
			this.users = new ArrayList<String>();
		}

		this.users.add(user.getId());
	}
	
	/**
	 * Obtem o Usuario responsavel pelo Setor
	 * @param user
	 */
	public User getOwner() {
		return this.owner;
	}

	/**
	 * Define um Usuario como responsavel pelo Setor
	 * @param user
	 */
	public void setOwner(User user) {
		this.owner = user;
	}

	/**
	 * Obtem lista de Objetos de usuarios
	 * 
	 * @return
	 */
	public ArrayList<User> getUser() {
		
		// cria um ArrayList de usuarios
		ArrayList<User> users = new ArrayList<User>();
		
		if (this.users == null) {
			return null;
		}
		
		// percorre lista de usuarios deste Setor
		for (String key : this.users) {
			
			// Instancia a classe de usuario adiciona a lista de usuarios
			users.add(new User(key.toString()));
		}
		
		return users;
	}

	/**
	 * Metodo salva o setor persistindo os seus dados
	 * @throws Exception 
	 */
	public void save() throws Exception {

		// se o metodo de persistencia foi instanciado 
		// ainda instanciamos
		if (this.saver == null) {
			this.saver = new Persistence("sector");
		}
		
		// Criamos o HashMap com todos os dados que queremos persistir
		HashMap<String, String> sector = new HashMap<String, String>();
		sector.put("status" , this.status ? "1" : "0");
		
		// setor esta ativado
		if (this.status) {
			
			if (this.users != null) {
				
				// adiciona a lista de usuarios ao setor
				sector.put("users" , this.users.toString());
				
				// percorre os usuarios deste setor
				for (String key : this.users) {

					// instancia a classe do Setor
					User u = new User(key.toString());
					u.setSector(this.name);
					// ativa o usuario
					u.setActive(true);
					// salva o usuario
					u.save();
				}
			}
					
			// se o setor possuir um responsavel
			if (this.owner != null) {
				
				// adicionamos o identificador do responsavel ao setor
				sector.put("owner" , this.owner.getId());
				
				// XXX FIXME :: Temos que recarregar a classe para atualizar os dados
				this.owner = new User(this.owner.getId());
				
				// adiciona uma flag ao responsavel
				this.owner.setOwner(this.name);
				
				// setor ativo, usuario responsavel tambem deve estar ativo
				this.owner.setActive(true);
				this.owner.setSector(this.name);
				// salva os daodos do Responsavel
				this.owner.save();
			}
		
		// setor foi desativado
		} else {
						
			//  desativamos todos os usuarios do setor, caso esteja algum associado
			if (this.users != null) {
				
				// percorre os usuarios deste setor
				for (String key : this.users) {
					
					// instancia a classe do Setor
					User u = new User(key.toString());
					// desassocia o user ao setor
					u.setOwner(null);
					u.setSector(null);
					// desativa o usuario
					u.setActive(false);
					// salva o usuario
					u.save();
				}
			}
			
			// limpamos a lista de usuarios deste setor
			sector.put("users" , null);
		}
						
		// Adiciona o Setor na lista
		this.saver.add(this.name, sector);
		
		// persiste o setor, gravando-o
		this.saver.save();
	}
}
