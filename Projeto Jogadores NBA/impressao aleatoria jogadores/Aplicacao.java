public class Aplicacao {
	public static void main(String[] args) {
		MyIO.setCharset("UTF-8");
		Jogador[] listaJogadores = new Jogador[5000];
		
		for (int i = 0; i < listaJogadores.length; i++) {
			String texto = MyIO.readLine();

			if(texto.equals("FIM"))
					break;
			
			listaJogadores[i] = Jogador.ler(texto);
		}

		// Numero de buscas solicitadas
		int qtd = Integer.parseInt(MyIO.readLine());
		
		// Pesquisa
		for(int i = 0; i < qtd; i++) {
			pesquisar(listaJogadores);
		}
	}
	
	public static void pesquisar(Jogador[] lista) { 
		int chave = MyIO.readInt(); 

		for(int j = 0; j < lista.length; j++) {
			if(lista[j].getId() == chave) {
				lista[j].imprimir();
				break;
			}
		}
	}
}


class Jogador {
	private int id, altura, peso, anoNascimento;
	private String nome, universidade, cidadeNascimento, estadoNascimento;

	public Jogador() {
	}

	public Jogador(int id, String nome, int altura, int peso, String universidade, int anoNascimento,
			String cidadeNascimento, String estadoNascimento) {

		this.id = id;
		this.nome = nome;
		this.altura = altura;
		this.peso = peso;
		this.universidade = universidade;
		this.anoNascimento = anoNascimento;
		this.cidadeNascimento = cidadeNascimento;
		this.estadoNascimento = estadoNascimento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getAnoNascimento() {
		return anoNascimento;
	}

	public void setAnoNascimento(int anoNascimento) {
		this.anoNascimento = anoNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUniversidade() {
		return universidade;
	}

	public void setUniversidade(String universidade) {
		this.universidade = universidade;
	}

	public String getCidadeNascimento() {
		return cidadeNascimento;
	}

	public void setCidadeNascimento(String cidadeNascimento) {
		this.cidadeNascimento = cidadeNascimento;
	}

	public String getEstadoNascimento() {
		return estadoNascimento;
	}

	public void setEstadoNascimento(String estadoNascimento) {
		this.estadoNascimento = estadoNascimento;
	}

	public static Jogador ler (String texto) {

		Jogador jogador = new Jogador();
		String[] dados = new String[8];
		String[] textos = texto.split(",");
		

		for (int i = 0; i < dados.length; i++) {
			if (i >= textos.length || textos[i].equals("")) 
				dados[i] = "";
			else
				dados[i] = textos[i];
		}

		jogador = popularDados(jogador, dados);

		return jogador; 
	}

	public static Jogador popularDados(Jogador jogador, String[] dados) {

		jogador.setId(Integer.parseInt(dados[0]));
		jogador.setNome(dados[1]);

		if (dados[2].equals("")) {
			jogador.setAltura(-1);
		} else {
			jogador.setAltura(Integer.parseInt(dados[2]));
		}

		if (dados[3].equals("")) {
			jogador.setPeso(-1);
		} else {
			jogador.setPeso(Integer.parseInt(dados[3]));
		}
		
		if (dados[4].equals("")) {
			jogador.setUniversidade("nao informado");
		} else {
			jogador.setUniversidade(dados[4]);
		}
		
		if (dados[5].equals("")) {
			jogador.setAnoNascimento(-1);
		} else {
			jogador.setAnoNascimento(Integer.parseInt(dados[5]));
		}

		if (dados[6].equals("")) {
			jogador.setCidadeNascimento("nao informado");
		} else {
			jogador.setCidadeNascimento(dados[6]);
		}

		if (dados[7].equals("")) {
			jogador.setEstadoNascimento("nao informado");
		} else {
			jogador.setEstadoNascimento(dados[7]);
		}

		return jogador;
	}

	public void imprimir() {
		
		System.out.println("[" + this.getId() + " ## " + this.getNome() + " ## " + this.getAltura() + " ## " + this.getPeso() + " ## " + this.getAnoNascimento() + " ## " 
				+ this.getUniversidade() + " ## " + this.getCidadeNascimento() + " ## " + this.getEstadoNascimento() + "]");

	}

	public Jogador clone() {
		Jogador copia;
		copia = new Jogador(this.id, this.nome, this.altura, this.peso, this.universidade, this.anoNascimento,
				this.cidadeNascimento, this.estadoNascimento);
		return copia;
	}

}
