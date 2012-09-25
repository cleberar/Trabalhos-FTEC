package abc.gei.libs;

import java.util.ArrayList;
import java.util.HashMap;

import abc.gei.core.Persistence;

/**
 * Classe de usuarios
 * 
 * @author cleber Rodrigues <cleber@cleberrodrigues.com.br>
 *
 */
public class User {
	
	private String name;
	private Boolean status;
	private String email;
	private String password;
	private String owner;
	private String sector;
	private Persistence saver;
	
	/**
	 * Construtor
	 */
	public User() {
		// usuario novo deve estar ativado
		this.status = true;
	}
	
	/**
	 * Construtor, recebendo e-mail do usuario para identificar os seus dados
	 * Instancia os dados na classe caso os mesmo estejam persistidos
	 *
	 *  @param email Email do usuario
	 *  @return void
	 */
	public User(String email) {

		// instancia o email do usuario
		this.email = email;

		// se o metodo de persistencia foi instanciado 
		// ainda instanciamos
		if (this.saver == null) {
			this.saver = new Persistence("user");
		}
	
		// Obtem os dados do usuario instanciado
		HashMap<String, String> data = this.saver.fetch(this.email);

		if (data != null) {
		
			// status == 1 instancia true / 0 = false
			this.status = new Integer(data.get("status")) == 1;
			this.name = data.get("name");
			this.password = data.get("password");
			this.owner = data.get("owner");
			this.sector = data.get("sector");
		}
	}
	
	/**
	 * Metodo que retorna Lista de OBjetos instanciando a Classe User
	 * 
	 * @return ArrayList Lista de Usuarios
	 */
	public ArrayList<User> fetchAll() {

		// nao foi instanciada ainda a classe de persistencia, o varemos
		if (this.saver == null) {
			this.saver = new Persistence("user");
		}
		

		// Obtem lista com todos os usuarios ja persistidos
		HashMap<String, Object> data = this.saver.fetchAll();
	
		// cria um ArrayList de usuarios
		ArrayList<User> users = new ArrayList<User>();
		
		// percorre lista de usuarios persistidos
		for (Object key : data.keySet()) {
			
			// Instancia a classe de usuario adiciona a lista de usuarios
			users.add(new User(key.toString()));
		}
		
		return users;
	}
	

	/**
	 * Seta novo valor para o Status
	 * 
	 * @param status
	 * @throws Exception 
	 */
	public void setActive(Boolean status) {
		this.status = status;
	}
		
	/**
	 * Obtem o identificador do usuario (email)
	 * @return String email do usuario
	 */
	public String getId() {
		return this.email;
	}
	
	/**
	 * Cria login / identificador
	 * 
	 * @param login
	 * @return
	 */
	public void setLogin(String email) {
		this.email = email;
	}
	
	/**
	 * Obtem nome do usuario
	 * @return String nome do usuario
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * define um nome do usuario
	 * @return void
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Obtem setor do usuario
	 * @return String nome do usuario
	 */
	public String getSector() {
		return this.sector;
	}
	
	/**
	 * define um setor para o usuario
	 * @return void
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	/**
	 * Obtem a senha do usuario
	 * @return String nome do usuario
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * define uma senha do usuario
	 * @return void
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Informa se o usuario esta ativo ou nao
	 * @return Boolean 
	 */
	public Boolean isActive() {
		return this.status;
	}

	/**
	 * @return the owner
	 */
	public String isOwner() {
		return this.owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Metodo salva o usuario persistindo os seus dados
	 * @throws Exception 
	 */
	public void save() throws Exception {

		// se o metodo de persistencia foi instanciado 
		// ainda instanciamos
		if (this.saver == null) {
			this.saver = new Persistence("user");
		}
		
		// Criamos o HashMap com todos os dados que queremos persistir
		HashMap<String, String> user = new HashMap<String, String>();
		user.put("status" , this.status ? "1" : "0");
		user.put("name" , this.name);
		user.put("password" , this.password);
		user.put("sector" , this.sector);

		// se o usuario for responsavel por um setor
		if (this.owner != null) {
			
			// adiciona o setor
			user.put("owner" , this.owner);
			
			// se o usuario esta sendo desativado, gera uma execssao
			// pois donos de setores nao podem ser desativados
			if (this.status == false) {
				throw new Exception("Usuario Responsavel nao pode ser desativado");
			}
		}
		
		// Adiciona o usuario na lista
		this.saver.add(this.email, user);
		
		// persiste o usuario, gravando-o
		this.saver.save();
	}
}
