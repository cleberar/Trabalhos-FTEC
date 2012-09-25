package abc.gei.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Classe Gerencia a persistencia dos dados do Projeto
 * 
 * XXX TODO :: Nesta primeira versao o armazenamento sera por arquivo,
 * 			   no proximo Grau, implementaremos conexao com algum 
 * 			   banco de dados 
 * 
 * @author cleber Rodrigues <cleber@cleberrodrigues.com.br>
 *
 */
public class Persistence {

	/**
	 * Local de armazenamento dos arquivos
	 */
	private String path = "persistence/";
	
	/**
	 * Objeto do Arquivo de armazenamento
	 */
	private File file;
	
	/**
	 * HashMap contendo um hash com indice e valor onde o indice sera o identificador
	 * e o valor sera um HashMap de Objetos contendo os dados
	 */
	HashMap<String, Object> dataList;

	/**
	 * Construtor, instancia os arquivos e 
	 * carrega os dados caso exista algum
	 * 
	 * @param fileName Nome do Arquivo de armazenamento
	 * 
	 * @return void
	 */
	public Persistence(String fileName) {
		
		// cria o objeto de arquivo informando o local e o nome do arquivo
		this.file = new File(this.path + fileName);

		// se o arquivo existir, carregamos seu conteudo para o HashMap dataList
		// assim podemos manipular todos os dados existentes
		if (this.file.exists()) {
			
			try {
				
				// para carregar o conteudo vamos deserializar os dados gravados em arquivo
				// incluindo os Objetos no nosso HashMap
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.file));

				this.dataList = (HashMap<String, Object>) in.readObject();
				
				// fecha o resource
				in.close();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.dataList = new HashMap<String, Object>();
		}
	}
	
	/**
	 * Obtem lista completa de Objetos armazenados
	 * 
	 * @return HashMap <String, Object> Chave/Objetos de dados armazenados
	 */
	public HashMap<String, Object> fetchAll() {
		return (HashMap<String, Object>) this.dataList;
	}
	
	/**
	 * Consulta um indice espeficico de um elemento,
	 * retorna um MashMap c
	 * 
	 * @param index chave usado para armazenar o objeto
	 *  
	 * @return HashMap <String, String> Objetos de dados armazenados
	 */
	public HashMap<String, String> fetch(String index) {
		return (HashMap<String, String>) this.dataList.get(index);
	}
	
	/**
	 * Adiciona ao HashMap de elementos um nova chave:Objeto
	 * 
	 * @param index Chave de identificacao do objeto
	 * @param data HashMap com os dados do Objeto
	 * 
	 * @return void
	 */
	public void add(String index, HashMap<?, ?> data) {
	     this.dataList.put(index , data);
	}

	/**
	 * Persiste os dados contidos no HashMap em arquivo
	 * 
	 * @return void
	 */
	public void save() {
		
		try {
			
			// serializa os dados da lista e grava no arquivo correspondente
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.file));
			out.writeObject(this.dataList);
			out.close();
		} catch (Exception e) {

			e.printStackTrace();
		}	
	}
}
