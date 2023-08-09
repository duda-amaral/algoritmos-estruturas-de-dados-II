import java.io.*;
import java.nio.charset.Charset;

public class Aplicacao {
	public static void main(String[] args) {
		MyIO.setCharset("UTF-8");
		Jogador[] listaJogadores = new Jogador[4000];

		listaJogadores = lerArquivo(listaJogadores);
		
		int comparacao = 0;
		AVL arvore = new AVL();
		String texto = MyIO.readLine();

		while (!texto.equals("FIM")) {
			int chave = Integer.parseInt(texto);
			Jogador jogadorEncontrado = new Jogador();
			jogadorEncontrado = pesquisar(listaJogadores, chave);
			if (jogadorEncontrado != null) {
				try {
					arvore.inserir(jogadorEncontrado);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			texto = MyIO.readLine();
		}
		
		String nomeJogador = MyIO.readLine();
		
		while(!nomeJogador.equals("FIM")) {
			Jogador pesquisado = new Jogador();
			pesquisado = arvore.pesquisar(nomeJogador);
			comparacao++;
			
			if(pesquisado != null) {
				arvore.caminhamento(nomeJogador);
				System.out.println("SIM");
			} else {
        		arvore.caminhamento(nomeJogador);
				System.out.println("NAO");
      }
      
      nomeJogador = MyIO.readLine();
		}

		criarArquivoLog(comparacao);

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
	

	  private static void criarArquivoLog (int comparacao) {
	       long tempoExecucao = System.currentTimeMillis();
	 
	       try {
	          FileWriter arqEscrita = new FileWriter("763167_arvoreAVL.txt");
	          arqEscrita.write("Matricula: 763167 \t" + "Tempo de execução em milisegundos: " + tempoExecucao
	                + "\t Numero de comparações na arvore: " + comparacao);
	          arqEscrita.close();
	       } catch (IOException e) {
	          e.printStackTrace();
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

		System.out.print(this.getNome() + " ");

	}

	public Jogador clone() {
		Jogador copia;
		copia = new Jogador(this.id, this.nome, this.altura, this.peso, this.universidade, this.anoNascimento,
				this.cidadeNascimento, this.estadoNascimento);
		return copia;
	}

}

class No {

	private Jogador item;
	private int altura;
	private No esquerda;
	private No direita;
	
	public No() {
		
		item = new Jogador();
		altura = 0;
		esquerda = null;
		direita = null;
	}
	
	public No(Jogador registro) {
		
		item = registro;
		altura = 0;
		esquerda = null;
		direita = null;
	}
	
	public Jogador getItem() {
		return item;
	}
	public void setItem(Jogador item) {
		this.item = item;
	}
	
	public No getEsquerda() {
		return esquerda;
	}
	public void setEsquerda(No esquerda) {
		this.esquerda = esquerda;
	}
	
	public No getDireita() {
		return direita;
	}
	public void setDireita(No direita) {
		this.direita = direita;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura() {
		
		int alturaEsq, alturaDir;
		
		if (this.direita == null)
			alturaDir = -1;
		else
			alturaDir = this.direita.getAltura();
		
		if (this.esquerda == null)
			alturaEsq = -1;
		else
			alturaEsq = this.esquerda.getAltura();
		
		if (alturaEsq > alturaDir)
			this.altura = alturaEsq + 1;
		else
			this.altura = alturaDir + 1;
	}
	
	public int getFatorBalanceamento() {
		
		int alturaEsq, alturaDir;
		
		if (this.direita == null)
			alturaDir = -1;
		else
			alturaDir = this.direita.getAltura();
		
		if (this.esquerda == null)
			alturaEsq = -1;
		else
			alturaEsq = this.esquerda.getAltura();
		
		return (alturaEsq - alturaDir);
	}
}

class AVL {

	private No raiz;
	
	public AVL() {
		
		raiz = null;
	}

	public Jogador pesquisar(String chave) {
		return pesquisar(this.raiz, chave);
	}
	
	private Jogador pesquisar(No raizSubarvore, String chave) {
		
		if (raizSubarvore == null)
			return null;
		else if (chave.equals(raizSubarvore.getItem().getNome()))
			return raizSubarvore.getItem();
		else if (chave.compareTo(raizSubarvore.getItem().getNome()) > 0)
			return pesquisar(raizSubarvore.getDireita(), chave);
		else
			return pesquisar(raizSubarvore.getEsquerda(), chave);
	}
	
	public void inserir(Jogador novo) throws Exception {
		this.raiz = inserir(this.raiz, novo);
	}
	
	private No inserir(No raizSubarvore, Jogador novo) throws Exception{
		
		if (raizSubarvore == null)
			raizSubarvore = new No(novo);
		else if (novo.getNome().equals(raizSubarvore.getItem().getNome()))
			throw new Exception("Não foi possível inserir o item na árvore: chave já inseriada anteriormente!");
		else if (novo.getNome().compareTo(raizSubarvore.getItem().getNome()) < 0)
			raizSubarvore.setEsquerda(inserir(raizSubarvore.getEsquerda(), novo));
		else
			raizSubarvore.setDireita(inserir(raizSubarvore.getDireita(), novo));
			
		return balancear(raizSubarvore);
	}
	
	private No balancear(No raizSubarvore) {
		
		int fatorBalanceamento;
		int fatorBalanceamentoFilho;
		
		fatorBalanceamento = raizSubarvore.getFatorBalanceamento();
		
		if (fatorBalanceamento == 2) {
			fatorBalanceamentoFilho = raizSubarvore.getEsquerda().getFatorBalanceamento();
			if (fatorBalanceamentoFilho == -1) {
				raizSubarvore.setEsquerda(rotacionarEsquerda(raizSubarvore.getEsquerda()));
			}
			raizSubarvore = rotacionarDireita(raizSubarvore);
		} else if (fatorBalanceamento == -2) {
			fatorBalanceamentoFilho = raizSubarvore.getDireita().getFatorBalanceamento();
			if (fatorBalanceamentoFilho == 1) {
				raizSubarvore.setDireita(rotacionarDireita(raizSubarvore.getDireita()));
			}
			raizSubarvore = rotacionarEsquerda(raizSubarvore);
		} else
			raizSubarvore.setAltura();
		
		return raizSubarvore;
	}
	
	private No rotacionarDireita(No p) {
		
		No u = p.getEsquerda();
		No filhoEsquerdaDireita = u.getDireita();
		
		u.setDireita(p);
		p.setEsquerda(filhoEsquerdaDireita);
		
		p.setAltura();
		u.setAltura();
	
		return u;
	}
	
	private No rotacionarEsquerda(No p) {
		
		No z = p.getDireita();
		No filhoDireitaEsquerda = z.getEsquerda();
		
		z.setEsquerda(p);
		p.setDireita(filhoDireitaEsquerda);
		
		p.setAltura();
		z.setAltura();
		
		return z;
	}

	public void remover(String chaveRemover) throws Exception {
		this.raiz = remover(this.raiz, chaveRemover);
	}

	private No remover(No raizSubarvore, String chaveRemover) throws Exception {
		
		if (raizSubarvore == null)
			throw new Exception("Não foi possível remover o item da árvore: chave não encontrada!");
		else if (chaveRemover.equals(raizSubarvore.getItem().getNome())) {
			if (raizSubarvore.getEsquerda() == null)
				raizSubarvore = raizSubarvore.getDireita();
			else if (raizSubarvore.getDireita() == null)
				raizSubarvore = raizSubarvore.getEsquerda();
			else
				raizSubarvore.setEsquerda(antecessor(raizSubarvore, raizSubarvore.getEsquerda()));
		} else if (chaveRemover.compareTo(raizSubarvore.getItem().getNome()) > 0)
			raizSubarvore.setDireita(remover(raizSubarvore.getDireita(), chaveRemover));
		else
			raizSubarvore.setEsquerda(remover(raizSubarvore.getEsquerda(), chaveRemover));
			
		return balancear(raizSubarvore);
	}
	
	private No antecessor(No noRetirar, No raizSubarvore) {
		
		if (raizSubarvore.getDireita() != null)
			raizSubarvore.setDireita(antecessor(noRetirar, raizSubarvore.getDireita()));
		else {
			noRetirar.setItem(raizSubarvore.getItem());
			raizSubarvore = raizSubarvore.getEsquerda();
		}	
		
		return balancear(raizSubarvore);
	}
	
	public void caminhamento(String chave) {
		caminhamento(this.raiz, chave);
	}
	
	private void caminhamento(No raizSubarvore, String chave) {
		
		if (raizSubarvore != null) {
			if(chave.equals(raizSubarvore.getItem().getNome())) {
			raizSubarvore.getItem().imprimir();
			}
			else if(chave.compareTo(raizSubarvore.getItem().getNome()) > 0) {
				raizSubarvore.getItem().imprimir();
				caminhamento(raizSubarvore.getDireita(), chave); 
				} else {
				raizSubarvore.getItem().imprimir();
				caminhamento(raizSubarvore.getEsquerda(), chave);
			}
		}
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