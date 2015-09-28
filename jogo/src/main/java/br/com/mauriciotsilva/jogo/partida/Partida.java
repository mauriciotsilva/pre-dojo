package br.com.mauriciotsilva.jogo.partida;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import br.com.mauriciotsilva.jogo.estrutura.Modelavel;
import br.com.mauriciotsilva.jogo.partida.rank.Ranking;

public class Partida {

	public static final String RANKING_STREAKER_PARTIDA = "partida.streaker.ranking";

	private boolean finalizada;
	private Jogador vencedor;
	private Map<String, Object> sessao;
	private List<Jogador> ranking;
	private List<Jogador> jogadores;
	private Map<String, List<Acontecimento>> grupoAcontecimento;

	private Partida() {
		jogadores = new ArrayList<Jogador>();
		sessao = new HashMap<String, Object>();
	}

	public Partida(Modelavel modelavel) {
		this();
		grupoAcontecimento = agruparAcontecimentos(modelavel);

		iniciarExecucao();
		finalizarExecucao();
	}

	private Map<String, List<Acontecimento>> agruparAcontecimentos(Modelavel modelavel) {

		Stream<Acontecimento> acontecimentos = modelavel.getItens().stream().filter(item -> !item.isItemSistema())
				.map(item -> Acontecimento.criar(this, item));

		return acontecimentos.collect(groupingBy(Acontecimento::getIdentificadorGrupo, toList()));
	}

	private void iniciarExecucao() {

		for (List<Acontecimento> acontecimetos : grupoAcontecimento.values()) {

			int contador = 0;
			for (Acontecimento acontecimento : acontecimetos) {

				acontecimento.executar();
				verificarStreaker(acontecimento.getJogadorUm());
				acontecimento.adicionarPremiacaoPorMinuto(++contador);
			}
		}
	}

	private void verificarStreaker(Jogador jogador) {

		if (!jogador.isValido()) {
			return;
		}

		Jogador streaker = getAtributoSessao(RANKING_STREAKER_PARTIDA);
		if (streaker == null || streaker.getNumeroStreak() < jogador.getNumeroStreak()) {
			criarAtributoSessao(RANKING_STREAKER_PARTIDA, jogador.clone());
		}
	}

	void criarAtributoSessao(String chave, Object valor) {
		sessao.put(chave, valor);
	}

	private void finalizarExecucao() {
		definirRanking();
		adicionarPremiacaoChuckNorris();
		finalizada = true;
	}

	Jogador adicionarJogador(String nome) {

		Jogador jogador = buscarJogador(nome);
		if (jogador == null) {
			return adicionar(criarJogador(nome));
		}

		return jogador;
	}

	private Jogador adicionar(Jogador jogador) throws IllegalStateException {

		if (!finalizada) {
			jogadores.add(jogador);
			return jogador;
		}

		throw new IllegalStateException("Match has already finished");
	}

	public Jogador buscarJogador(String nome) {

		Jogador novoJogador = criarJogador(nome);
		int index = jogadores.indexOf(novoJogador);

		if (index < 0) {
			return null;
		}

		return jogadores.get(index);
	}

	private Jogador criarJogador(String nome) {
		return new Jogador(nome);
	}

	public Jogador getStreaker() {
		return getAtributoSessao(RANKING_STREAKER_PARTIDA);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAtributoSessao(String str) {
		return (T) sessao.get(str);
	}

	public List<Jogador> listarRankingAssassinatos() {
		return Ranking.rankingAssassitos(jogadores);
	}

	private void definirRanking() {
		ranking = listarRankingAssassinatos();
		vencedor = ranking.get(0);
	}

	private void adicionarPremiacaoChuckNorris() {
		if (vencedor.getQuantidadeMorte() == 0) {
			vencedor.adicionarPremio(Premio.NORRIS);
		}
	}

	public Jogador getVencedor() {
		return vencedor;
	}

	public List<Jogador> getJogadores() {
		return Collections.unmodifiableList(jogadores);
	}
}
