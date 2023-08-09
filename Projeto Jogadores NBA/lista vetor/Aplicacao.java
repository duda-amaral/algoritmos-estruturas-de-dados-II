import java.io.*;
import java.nio.charset.Charset;

public class Aplicacao {
	public static void main(String[] args) {
		MyIO.setCharset("UTF-8");
		Jogador[] listaJogadores = new Jogador[4000];

		listaJogadores = lerArquivo(listaJogadores);

		Lista lista = new Lista(150);
		String texto = MyIO.readLine();

		while (!texto.equals("FIM")) {
			int chave = Integer.parseInt(texto);
			Jogador jogadorEncontrado = pesquisar(listaJogadores, chave);
			if (jogadorEncontrado != null) {
				try {
					lista.inserirFim(jogadorEncontrado);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			texto = MyIO.readLine();
		}

		int qtd = MyIO.readInt();

		String entrada;
		Jogador novoJogador;
		for (int i = 0; i < qtd; i++) {
			entrada = MyIO.readLine();
			String[] entradaLista = new String[3];

			entradaLista = entrada.split(" ");

			if (entradaLista[0].equals("II") || entradaLista[0].equals("I*") || entradaLista[0].equals("IF")) {
				
				if(entradaLista[2] == null){
					int id = Integer.parseInt(entradaLista[1]);
					novoJogador = pesquisar(listaJogadores, id);
				} else {
					int id = Integer.parseInt(entradaLista[2]);
					novoJogador = pesquisar(listaJogadores, id);
				}
				
				if (entradaLista[0].equals("II")) {
					try {
						lista.inserirInicio(novoJogador);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (entradaLista[0].equals("I*")){
					try {
						lista.inserir(novoJogador, Integer.parseInt(entradaLista[1]));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} 
				else {
					try {
						lista.inserirFim(novoJogador);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (entradaLista[0].equals("RI") || entradaLista[0].equals("R*") || entradaLista[0].equals("RF")){
				
				Jogador desenfileirado = new Jogador();

				if(entradaLista[2] == null){
					if (entradaLista[0].equals("RI")) {
						try {
							desenfileirado = lista.removerInicio();
							
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
					else {
						try {
							desenfileirado = lista.removerFim();
							
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
	
				} else {
					if (entradaLista[0].equals("R*")) {
						try {
							desenfileirado = lista.remover(Integer.parseInt(entradaLista[1]));
							
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}

				try {
					System.out.print("(R) " + desenfileirado.getNome() + "\n");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

		try {
			lista.mostrar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static Jogador pesquisar(Jogador[] lista, int chave) {

		for (int j = 0; j < lista.length; j++) {
			if (lista[j] == null)
				continue;

			if (lista[j].getId() == chave)
				return lista[j];
		}

		return null;
	}

	public static Jogador[] lerArquivo(Jogador[] listaJogadores) {
		ArquivoTextoLeitura arqLeitura;
		arqLeitura = new ArquivoTextoLeitura("jogadores.txt"); //"/tmp/jogadores.txt"

		Jogador jogador;
		jogador = new Jogador();

		String texto;
		texto = arqLeitura.ler();

		int i = -1;
		while (texto != null) {
			texto = arqLeitura.ler();
			if (i != -1) {
				if (texto == null)
					continue;

				listaJogadores[i] = jogador.ler(texto);
			}

			i++;
		}

		arqLeitura.fecharArquivo();

		return listaJogadores;
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

	public Jogador ler(String texto) {

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

	public Jogador popularDados(Jogador jogador, String[] dados) {

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

		System.out.print(" ## " + this.getId() + " ## " + this.getNome() + " ## ");

		if (this.getAltura() < 0) {
			System.out.print("nao informado ## ");
		} else {
			System.out.print(this.getAltura() + " ## ");
		}

		if (this.getPeso() < 0) {
			System.out.print("nao informado ## ");
		} else {
			System.out.print(this.getPeso() + " ## ");
		}

		if (this.getAnoNascimento() < 0) {
			System.out.print("nao informado ## ");
		} else {
			System.out.print(this.getAnoNascimento() + " ## ");
		}

		System.out.println(this.getUniversidade() + " ## " + this.getCidadeNascimento() + " ## "
				+ this.getEstadoNascimento() + " ## ");

	}

	public Jogador clone() {
		Jogador copia;
		copia = new Jogador(this.id, this.nome, this.altura, this.peso, this.universidade, this.anoNascimento,
				this.cidadeNascimento, this.estadoNascimento);
		return copia;
	}

}

class Lista {

	private Jogador lista[];
	private int primeiro;
	private int ultimo;
	private int tamanho;

	public Lista(int tamanho) {

		lista = new Jogador[tamanho];
		tamanho = 0;
		primeiro = 0;
		ultimo = 0;
	}

	public boolean listaVazia() {

		boolean resp;

		if (primeiro == ultimo)
			resp = true;
		else
			resp = false;

		return resp;
	}

	public boolean listaCheia() {

		boolean resp;

		if (ultimo == lista.length)
			// if (tamanho == lista.length)
			resp = true;
		else
			resp = false;

		return resp;
	}

	public void inserirInicio(Jogador novo) throws Exception {

		if (!listaCheia()) {

			for (int i = 0; i < ultimo; i++)
				lista[i + 1] = lista[i];

			lista[0] = novo;

			ultimo++;
			tamanho++;

		} else
			throw new Exception("Não foi possível inserir o item na lista: a lista está cheia!");
	}

	public void inserir(Jogador novo, int posicao) throws Exception {

		if (!listaCheia()) {
			if ((posicao >= 0) && (posicao <= tamanho)) {
				for (int i = ultimo; i > posicao; i--)
					lista[i] = lista[i - 1];

				lista[posicao] = novo;

				ultimo++;
				tamanho++;
			} else
				throw new Exception("Não foi possível inserir o item na lista: posição informada é inválida!");
		} else
			throw new Exception("Não foi possível inserir o item na lista: a lista está cheia!");
	}

	public void inserirFim(Jogador novo) throws Exception {

		if (!listaCheia()) {
			lista[ultimo + 1] = novo;

			ultimo++;
			tamanho++;

		} else
			throw new Exception("Não foi possível inserir o item na lista: a lista está cheia!");
	}

	public Jogador removerInicio() throws Exception {

		Jogador removido;

		if (!listaVazia()) {

			removido = lista[0];
			tamanho--;

			for (int i = 0; i < ultimo; i++) {
				lista[i] = lista[i + 1];
			}

			ultimo--;

			return removido;

		} else
			throw new Exception("Não foi possível remover o item da lista: a lista está vazia!");
	}

	public Jogador remover(int posicao) throws Exception {

		Jogador removido;

		if (!listaVazia()) {
			if ((posicao >= 0) && (posicao < tamanho)) {

				removido = lista[posicao];
				tamanho--;

				for (int i = posicao; i < tamanho; i++) {
					lista[i] = lista[i + 1];
				}

				ultimo--;

				return removido;
			} else
				throw new Exception("Não foi possível remover o item da lista: posição informada é inválida!");
		} else
			throw new Exception("Não foi possível remover o item da lista: a lista está vazia!");
	}

	public Jogador removerFim() throws Exception {

		Jogador removido;

		if (!listaVazia()) {
			
			removido = lista[ultimo];
			lista[ultimo] = null;

			tamanho--;
			ultimo--;

			return removido;
		} else
			throw new Exception("Não foi possível remover o item da lista: a lista está vazia!");
	}

	public void mostrar() throws Exception {

		if (!listaVazia()) {

			for (int i = primeiro; i < ultimo; i++) {
				System.out.print("[" + i + "] ");
				lista[i].imprimir();
			}
		} else
			throw new Exception("Não foi possível imprimir o conteúdo da lista: a lista está vazia!");
	}
}

class ArquivoTextoLeitura {

	private BufferedReader entrada;

	ArquivoTextoLeitura(String nomeArquivo) {

		try {
			entrada = new BufferedReader(new InputStreamReader(new FileInputStream(nomeArquivo), "UTF-8"));
		} catch (UnsupportedEncodingException excecao) {
			System.out.println(excecao.getMessage());
		} catch (FileNotFoundException excecao) {
			System.out.println("Arquivo nao encontrado");
		}
	}

	public void fecharArquivo() {

		try {
			entrada.close();
		} catch (IOException excecao) {
			System.out.println("Erro no fechamento do arquivo de leitura: " + excecao);
		}
	}

	@SuppressWarnings("finally")
	public String ler() {

		String textoEntrada = null;

		try {
			textoEntrada = entrada.readLine();
		} catch (EOFException excecao) { // Excecao de final de arquivo.
			textoEntrada = null;
		} catch (IOException excecao) {
			System.out.println("Erro de leitura: " + excecao);
			textoEntrada = null;
		} finally {
			return textoEntrada;
		}
	}
}

class MyIO {

	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in, Charset.forName("ISO-8859-1")));
	private static String charset = "ISO-8859-1";

	public static void setCharset(String charset_) {
		charset = charset_;
		in = new BufferedReader(new InputStreamReader(System.in, Charset.forName(charset)));
	}

	public static void print() {
	}

	public static void print(int x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void print(double x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void print(String x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void print(boolean x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void print(char x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void println() {
	}

	public static void println(int x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.println(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void println(double x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.println(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void println(String x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.println(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void println(boolean x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.println(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void println(char x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.println(x);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static void printf(String formato, double x) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.printf(formato, x);// "%.2f"
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
	}

	public static double readDouble() {
		double d = -1;
		try {
			d = Double.parseDouble(readString().trim().replace(",", "."));
		} catch (Exception e) {
		}
		return d;
	}

	public static double readDouble(String str) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(str);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
		return readDouble();
	}

	public static float readFloat() {
		return (float) readDouble();
	}

	public static float readFloat(String str) {
		return (float) readDouble(str);
	}

	public static int readInt() {
		int i = -1;
		try {
			i = Integer.parseInt(readString().trim());
		} catch (Exception e) {
		}
		return i;
	}

	public static int readInt(String str) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(str);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
		return readInt();
	}

	public static String readString() {
		String s = "";
		char tmp;
		try {
			do {
				tmp = (char) in.read();
				if (tmp != '\n' && tmp != ' ' && tmp != 13) {
					s += tmp;
				}
			} while (tmp != '\n' && tmp != ' ');
		} catch (IOException ioe) {
			System.out.println("lerPalavra: " + ioe.getMessage());
		}
		return s;
	}

	public static String readString(String str) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(str);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
		return readString();
	}

	public static String readLine() {
		String s = "";
		char tmp;
		try {
			do {
				tmp = (char) in.read();
				if (tmp != '\n' && tmp != 13) {
					s += tmp;
				}
			} while (tmp != '\n');
		} catch (IOException ioe) {
			System.out.println("lerPalavra: " + ioe.getMessage());
		}
		return s;
	}

	public static String readLine(String str) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(str);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
		return readLine();
	}

	public static char readChar() {
		char resp = ' ';
		try {
			resp = (char) in.read();
		} catch (Exception e) {
		}
		return resp;
	}

	public static char readChar(String str) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(str);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
		return readChar();
	}

	public static boolean readBoolean() {
		boolean resp = false;
		String str = "";

		try {
			str = readString();
		} catch (Exception e) {
		}

		if (str.equals("true") || str.equals("TRUE") || str.equals("t") || str.equals("1") ||
				str.equals("verdadeiro") || str.equals("VERDADEIRO") || str.equals("V")) {
			resp = true;
		}

		return resp;
	}

	public static boolean readBoolean(String str) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(str);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
		return readBoolean();
	}

	public static void pause() {
		try {
			in.read();
		} catch (Exception e) {
		}
	}

	public static void pause(String str) {
		try {
			PrintStream out = new PrintStream(System.out, true, charset);
			out.print(str);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Erro: charset invalido");
		}
		pause();
	}
}