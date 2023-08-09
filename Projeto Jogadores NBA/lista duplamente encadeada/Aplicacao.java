import java.io.*;
import java.nio.charset.Charset;

public class Aplicacao {
	public static void main(String[] args) {
		MyIO.setCharset("UTF-8");
		Jogador[] listaJogadores = new Jogador[4000];

		listaJogadores = lerArquivo(listaJogadores);

		Lista lista = new Lista();
		String texto = MyIO.readLine();

		while (!texto.equals("FIM")) {
			int chave = Integer.parseInt(texto);
			Jogador jogadorEncontrado = pesquisar(listaJogadores, chave);
			if (jogadorEncontrado != null) {
				try {
					lista.inserirFinal(jogadorEncontrado);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			texto = MyIO.readLine();
		}

		int qtd = MyIO.readInt();

		String entrada;
		Jogador novoJogador;
		Jogador desenfileirado = new Jogador();
		for (int i = 0; i < qtd; i++) {
			entrada = MyIO.readLine();
			String[] entradaLista = new String[2];

			entradaLista = entrada.split(" ");
						
			if(entradaLista[0].equals("II") || entradaLista[0].equals("I*") || entradaLista[0].equals("IF")) {
				if(entradaLista[0].equals("II")) {
					int id = Integer.parseInt(entradaLista[1]);
					novoJogador = pesquisar(listaJogadores, id);
					if (novoJogador != null)
						lista.inserirInicio(novoJogador);
				}
				
				else if(entradaLista[0].equals("I*")) {
					int id = Integer.parseInt(entradaLista[2]);
					novoJogador = pesquisar(listaJogadores, id);
					if (novoJogador != null)
						lista.inserir(novoJogador, Integer.parseInt(entradaLista[1]));
				}
				
				else if(entradaLista[0].equals("IF")) {
					int id = Integer.parseInt(entradaLista[1]);
					novoJogador = pesquisar(listaJogadores, id);

					if (novoJogador != null)
						lista.inserirFinal(novoJogador);
				}
			}
			
			if (entradaLista[0].equals("RI") || entradaLista[0].equals("R*") || entradaLista[0].equals("RF")) {
				
				if(entradaLista[0].equals("RI")) {
					try {
						desenfileirado = lista.removerInicio();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				else if(entradaLista[0].equals("R*")) {
					try {
						desenfileirado = lista.remover(Integer.parseInt(entradaLista[1]));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				else if(entradaLista[0].equals("RF")) {
					try {
						desenfileirado = lista.removerFinal();
					} catch (Exception e) {
						e.printStackTrace();
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
		arqLeitura = new ArquivoTextoLeitura("/tmp/jogadores.txt");

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

	private Celula primeiro;
	private Celula ultimo;
	private int tamanho;
	
	public Lista() {
		
		Celula sentinela;
		
		sentinela = new Celula();
		
		primeiro = sentinela;
		ultimo = sentinela;
		
		tamanho = 0;
	}
	
	public boolean listaVazia() {
		
		boolean resp;
		
		if (primeiro == ultimo)
			resp = true;
		else
			resp = false;
		
		return resp;
	}
	
	public void inserir(Jogador novo, int posicao) {
		
		Celula novaCelula, atual;
		
		if(posicao == 0) {
			inserirInicio(novo);
			} else if (posicao == tamanho) {
				inserirFinal(novo);
				tamanho++;
			} else if (posicao >= 0 && posicao <= tamanho) {
				novaCelula = new Celula(novo);
				atual = primeiro;
				for(int i = 0; i < posicao; i++) {
					atual = atual.getProximo();
				}
				
				novaCelula.setProximo(atual.getProximo());
				novaCelula.setAnterior(atual);
				atual.getProximo().setAnterior(novaCelula);
				atual.setProximo(novaCelula);
			}
	}
	
	public void inserirInicio(Jogador novo) {
		
		Celula novaCelula;
		
		novaCelula = new Celula(novo);
		
		primeiro.getProximo().setAnterior(novaCelula);
		novaCelula.setProximo(primeiro.getProximo());
		novaCelula.setAnterior(primeiro);
		primeiro.setProximo(novaCelula);
		
		tamanho++;
		
	}
	
	public void inserirFinal(Jogador novo) {
		
		Celula novaCelula;
		
		novaCelula = new Celula(novo);
		
		ultimo.setProximo(novaCelula);
		novaCelula.setAnterior(ultimo);
		
		ultimo = novaCelula;
		
		tamanho++;
		
	}
	
	public Jogador remover(int posicao) throws Exception {
		
		Celula removida, anterior, proximo;
		
		if (! listaVazia()) {
			
			if (posicao <= tamanho) {
		         removida = primeiro.getProximo();

		        for (int i = 0; i < posicao; i++) {
		          if (removida != null)
		            removida = removida.getProximo();
		        }

		        if (removida != ultimo) {
		           anterior = removida.getAnterior();
		           proximo = removida.getProximo();

		          anterior.setProximo(proximo);
		          proximo.setAnterior(anterior);

		          removida.setAnterior(null);
		          removida.setProximo(null);

		          return removida.getItem();
		        } else
		          return removerFinal();
		      }
		}
		return null;
	}
	
	public Jogador removerInicio() throws Exception {
		
		Celula removida;
		
		if (! listaVazia()) {
			
			removida = primeiro.getProximo();
			
			removida.getProximo().setAnterior(primeiro);
			primeiro.setProximo(removida.getProximo());
			removida.setAnterior(null);
			removida.setProximo(null);
			
			tamanho--;
			
			return (removida.getItem());
		} else
			throw new Exception("Não foi possível remover o item da lista: a lista está vazia!");
	}
	
	public Jogador removerFinal() throws Exception {
		
		Celula removida, penultima;
		
		if (! listaVazia()) {
			
			removida = ultimo;
			
			penultima = ultimo.getAnterior();
			penultima.setProximo(null);
			removida.setAnterior(null);
			
			ultimo = penultima;
			
			tamanho--;
			
			return (removida.getItem());
		} else
			throw new Exception("Não foi possível remover o último item da lista: a lista está vazia!");
	}
	
	public void mostrar() throws Exception {
		
		Celula aux;
		int posicao = 0;
		
		if (! listaVazia()) {
			
			aux = primeiro.getProximo();
			while (aux != null) {
				System.out.print("[" + (posicao++) + "]");
				aux.getItem().imprimir();
				aux = aux.getProximo();
			}	
		} else
			throw new Exception("Não foi possível imprimir o conteúdo da lista: a lista está vazia!");
	}
}

class Celula {

	private Jogador item;
	private Celula anterior;
	private Celula proximo;
	
	public Celula(Jogador novo) {
  
		item = novo;
  anterior = null;
		proximo = null;
	}
	
	public Celula() {
		
		item = new Jogador();
		anterior = null;
		proximo = null;
	}
	
	public Jogador getItem() {
		return item;
	}
	public void setItem(Jogador item) {
		this.item = item;
	}
	
	public Celula getProximo() {
		return proximo;
	}
	public void setProximo(Celula proximo) {
		this.proximo = proximo;
	}

	public Celula getAnterior() {
		return anterior;
	}
	public void setAnterior(Celula anterior) {
		this.anterior = anterior;
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