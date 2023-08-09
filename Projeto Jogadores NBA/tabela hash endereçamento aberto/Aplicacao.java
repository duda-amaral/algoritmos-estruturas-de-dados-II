import java.io.*;
import java.nio.charset.Charset;

public class Aplicacao {
	public static void main(String[] args) {
		MyIO.setCharset("UTF-8");
		Jogador[] listaJogadores = new Jogador[4000];

		listaJogadores = lerArquivo(listaJogadores);
		
		int comparacao = 0;
		TabelaHash tabela = new TabelaHash(79);
		String texto = MyIO.readLine();

		while (!texto.equals("FIM")) {
			int chave = Integer.parseInt(texto);
			Jogador jogadorEncontrado = new Jogador();
			jogadorEncontrado = pesquisar(listaJogadores, chave);
			if (jogadorEncontrado != null) {
				try {
					tabela.inserir(jogadorEncontrado);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			texto = MyIO.readLine();
		}
		
		String nomeJogador = MyIO.readLine();
		
		while(!nomeJogador.equals("FIM")) {
			Jogador pesquisado;
			pesquisado = tabela.pesquisar(nomeJogador, listaJogadores);
			comparacao++;
			
			if(pesquisado != null) {
				System.out.println("SIM");
			} else {
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
	          FileWriter arqEscrita = new FileWriter("763167_hashRehashing.txt");
	          arqEscrita.write("Matricula: 763167 \t" + "Tempo de execução em milisegundos: " + tempoExecucao
	                + "\t Numero de comparações na pesquisa: " + comparacao);
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

		System.out.print(this.getAltura() + " ");

	}

	public Jogador clone() {
		Jogador copia;
		copia = new Jogador(this.id, this.nome, this.altura, this.peso, this.universidade, this.anoNascimento,
				this.cidadeNascimento, this.estadoNascimento);
		return copia;
	}

}

class TabelaHash {

	private int M;
	private Jogador tabelaHash[];
	
	public TabelaHash(int tamanho) {
		
		this.M = tamanho;
		
		tabelaHash = new Jogador[this.M];
		for (int i = 0; i < this.M; i++)
			tabelaHash[i] = null;
	}
	
	private int funcaoHash(Jogador chave, int i) {
		
		return (((chave.getAltura() % this.M) + (i * (chave.getAltura() % 23))) % this.M);
	}
	
	public void inserir(Jogador novo) throws Exception {
		
		int posicao, tentativa;
		boolean inseriu = false;
		
		tentativa = 0;
		
		while ((!inseriu) && (tentativa < this.M)) {
			posicao = funcaoHash(novo, tentativa);
		
			if (tabelaHash[posicao] == null) {
				tabelaHash[posicao] = novo;
				inseriu = true;
			} else if (tabelaHash[posicao] == novo)
				throw new Exception("Erro ao tentar inserir o novo elemento na tabela hash: o elemento já foi inserido anteriormente!");
			else
				// colisão
				tentativa++;
		}
	}
	
	public Jogador pesquisar(String chave, Jogador[] listaJogadores) {
		
		int posicao, tentativa;
		Jogador jogador;
		jogador = pesquisarNome(chave, listaJogadores);
		tentativa = 0;
		
		while (tentativa < this.M) {
			posicao = funcaoHash(jogador, tentativa);
		
			 if ((tabelaHash[posicao] == jogador)) {
				 System.out.print(posicao + " ");
				return tabelaHash[posicao];
			 } else {
				// colisão
				tentativa++;
			 }
		}
		
		return null;
	}
	
	private Jogador pesquisarNome(String nomeJogador, Jogador[] listaJogadores) {
		for(Jogador jogadores : listaJogadores) {
			if(jogadores != null)
			if(jogadores.getNome().equals(nomeJogador)) {
				return jogadores;
			}
		}
		return null;
	}
	
	public void imprimir() {
		
		for (int i = 0; i < this.M; i++) {
			System.out.print(i + " ");
			if (tabelaHash[i] != null) {
				//tabelaHash[i].imprimir();
			} else
				System.out.println("NAO");
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