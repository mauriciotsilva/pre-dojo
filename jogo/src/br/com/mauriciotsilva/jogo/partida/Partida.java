package br.com.mauriciotsilva.jogo.partida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import br.com.mauriciotsilva.jogo.Jogador;
import br.com.mauriciotsilva.jogo.partida.rank.Ranking;

public class Partida {

	private final Map<String, Object> sessao;
	public static final int VELOCIDADE_MAXIMA = 10;
	public static final String RANKING_STREAKER_PARTIDA = "partida.streaker.ranking";

	private long id;
	private int duracao = 60;
	private int velocidade = 9;
	private List<Jogador> jogadores;
	private List<Evento> listaEvento;

	public Partida() {
		this(Arrays.asList(new Jogador("<WORLD>", false), new Jogador("Nick"),
				new Jogador("Roman"), new Jogador("Jack"), new Jogador(
						"Lincoln"), new Jogador("Zaphod")));

	}

	public Partida(List<Jogador> jogadores) {
		id = System.currentTimeMillis();
		sessao = new HashMap<String, Object>();
		this.jogadores = jogadores;
		this.listaEvento = new ArrayList<Evento>();
	}

	public void iniciar() {

		Timer timer = new Timer();
		timer.schedule(new PartidaTimerTask(this), 0, 1000);
	}

	public void definirDuracao(int segundos) {
		if (segundos <= 0)
			throw new IllegalArgumentException("Select a number greater than 0");
		this.duracao = segundos;
	}

	public void definirVelocidade(int velocidade) {
		if (velocidade <= 0 || velocidade > 10)
			throw new IllegalArgumentException(
					"Select a number between 1 and 10");

		this.velocidade = Partida.VELOCIDADE_MAXIMA - velocidade;
	}

	public void criarAtributoSessao(String chave, Object valor) {
		sessao.put(chave, valor);
	}

	@SuppressWarnings("unchecked")
	public <T> T obterAtributoSessao(String str) {
		return (T) sessao.get(str);
	}

	public List<Jogador> listarJogadores() {
		return jogadores;
	}

	public List<Jogador> listarRankingAssassinatos() {
		return Ranking.rankingAssassitos(jogadores);
	}

	int getDuracao() {
		return duracao;
	}

	int getVelocidade() {
		return velocidade;
	}

	public List<Evento> getListaEvento() {
		return listaEvento;
	}

	void setListaEvento(List<Evento> listaEvento) {
		this.listaEvento = listaEvento;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}

}
