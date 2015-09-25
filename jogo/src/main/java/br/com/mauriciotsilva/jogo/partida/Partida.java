package br.com.mauriciotsilva.jogo.partida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.mauriciotsilva.jogo.Jogador;
import br.com.mauriciotsilva.jogo.estrutura.ItemModelavel;
import br.com.mauriciotsilva.jogo.estrutura.Modelavel;
import br.com.mauriciotsilva.jogo.partida.rank.Ranking;
import br.com.mauriciotsilva.jogo.util.Util;

public class Partida {

	public static final String RANKING_STREAKER_PARTIDA = "partida.streaker.ranking";

	private boolean finalizada;
	private Jogador vencedor;
	private Map<String, Object> sessao;
	private List<Jogador> ranking;
	private List<Jogador> jogadores;
	private List<List<Fato>> grupoDeFatos;

	private Partida() {
		jogadores = new ArrayList<Jogador>();
		sessao = new HashMap<String, Object>();
	}

	public Partida(Modelavel modelavel) {

		this();
		grupoDeFatos = listarGrupoDeFatos(modelavel);

		iniciarExecucao();
		finalizarExecucao();
	}

	private List<List<Fato>> listarGrupoDeFatos(Modelavel modelavel) {

		List<Fato> fatos = new ArrayList<>();

		for (ItemModelavel item : modelavel.getItens()) {
			if (!item.isItemSistema()) {
				fatos.add(Fato.criar(this, item));
			}
		}

		return Util.groupList(fatos);
	}

	private void iniciarExecucao() {

		for (List<Fato> fatos : grupoDeFatos) {

			int countGrupo = 0;
			for (Fato fato : fatos) {
				countGrupo++;
				executar(countGrupo, fato);
			}
		}
	}

	private void finalizarExecucao() {
		definirRanking();
		adicionarPremiacaoChuckNorris();
		finalizada = true;
	}

	void criarAtributoSessao(String chave, Object valor) {
		sessao.put(chave, valor);
	}

	Jogador adicionarJogador(String nome) {

		Jogador jogador = buscarJogador(nome);
		if (jogador == null) {
			return adicionar(criarJogador(nome));
		}

		return jogador;
	}

	private Jogador adicionar(Jogador jogador) throws IllegalStateException {

		if (finalizada) {
			throw new IllegalStateException("Match has already finished");
		}

		jogadores.add(jogador);
		return jogador;
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

	public Jogador obterStreaker() {
		return obterAtributoSessao(RANKING_STREAKER_PARTIDA);
	}

	@SuppressWarnings("unchecked")
	public <T> T obterAtributoSessao(String str) {
		return (T) sessao.get(str);
	}

	private void executar(int count, Fato fato) {
		fato.executar();
		verificarStreaker(fato.getJogadorUm());
		fato.adicionarPremiacaoPorMinuto(count);
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
			vencedor.adicionarAward(Premio.NORRIS);
		}
	}

	private void verificarStreaker(Jogador jogador) {

		if (!jogador.isValido()) {
			return;
		}

		Jogador streaker = obterAtributoSessao(RANKING_STREAKER_PARTIDA);
		Comparator<Jogador> comparatorStreaker = new Ranking.ComparatorStreakers();

		if (streaker == null) {
			criarAtributoSessao(RANKING_STREAKER_PARTIDA, jogador.clone());

		} else {
			int resultado = comparatorStreaker.compare(streaker, jogador);
			if (resultado > 0) {
				criarAtributoSessao(RANKING_STREAKER_PARTIDA, jogador.clone());
			}
		}

	}

	public Jogador getVencedor() {
		return vencedor;
	}

	public List<Jogador> getJogadores() {
		return Collections.unmodifiableList(jogadores);
	}
}
