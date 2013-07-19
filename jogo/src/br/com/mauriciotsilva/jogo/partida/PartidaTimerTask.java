package br.com.mauriciotsilva.jogo.partida;

import java.util.Comparator;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import br.com.mauriciotsilva.jogo.Arma;
import br.com.mauriciotsilva.jogo.Award;
import br.com.mauriciotsilva.jogo.Jogador;
import br.com.mauriciotsilva.jogo.partida.rank.Ranking;
import br.com.mauriciotsilva.jogo.util.Mensagem;
import br.com.mauriciotsilva.jogo.util.Util;

class PartidaTimerTask extends TimerTask {

	private final int segundos = 59;
	private final Logger logger = Logger.getLogger(Partida.class
			.getSimpleName());

	private int count;
	private Partida partida;

	PartidaTimerTask(Partida partida) {
		this.partida = partida;
		logger.info(Mensagem.getMensagem(Mensagem.JOGO_PARTIDA_INICIO, partida));
	}

	@Override
	public void run() {
		if (ehTerminoPartida()) {
			finalizar();
		}

		count++;
		if (count == segundos) {
			count = 0;
			List<Jogador> list = partida.listarJogadores();
			for (Jogador jogador : list) {
				jogador.iniciarAssassinatosPorSegundo();
			}
		}

		simular();
	}

	private void finalizar() {

		logger.info(Mensagem
				.getMensagem(Mensagem.JOGO_PARTIDA_TERMINO, partida));
		logger.info("Ranking");

		List<Jogador> assassinos = partida.listarRankingAssassinatos();
		Jogador winner = assassinos.get(0);

		for (int i = 0; i < 3; i++) {
			Jogador jog = assassinos.get(i);

			StringBuffer builder = new StringBuffer();

			builder.append(1 + i);
			builder.append(" ");
			builder.append(jog);
			builder.append(" Assassinatos:");
			builder.append(jog.getQuantidadeAssassinato());
			builder.append(" Mortes:");
			builder.append(jog.getQuantidadeMorte());

			if (jog.equals(winner)) {
				builder.append(" Arma Favorita:" + winner.obterArmaFavorita());
			}

			logger.info(builder);
		}

		logger.info("Extra");

		if (winner.getQuantidadeMorte() == 0) {
			winner.adicionarAward(Award.NORRIS);
			logger.info(winner + " award(s):" + winner.getAwards());
		}

		Jogador streaker = partida
				.obterAtributoSessao(Partida.RANKING_STREAKER_PARTIDA);
		if (streaker != null) {
			logger.info("Streaker:" + streaker + " "
					+ streaker.getNumeroStreak());
		}

		System.exit(0);
	}

	private void simular() {

		Jogador jogadorUm = obterPrimeiroJogador();
		Jogador jogadorDois = obterSegundoJogador(jogadorUm);

		if (Util.random() >= partida.getVelocidade()) {

			Arma arma = Arma.random();
			if (jogadorUm.assassinar(arma, jogadorDois)) {

				try {
					verificarStreaker(jogadorUm);
				} catch (CloneNotSupportedException e) {
					logger.error("Não foi possível verificar Streaker", e);
				}

				Evento evento = Evento
						.criarEvento(jogadorUm, jogadorDois, arma);

				if (5 == jogadorUm.getQuantidadeAssassinatoPorMinuto()) {
					evento.anexar(Award.PADAWAN);
					jogadorUm.iniciarAssassinatosPorSegundo();
				}

				partida.getListaEvento().add(evento);
				logger.info(evento);

			}
		}

	}

	private void verificarStreaker(Jogador jogador)
			throws CloneNotSupportedException {

		if (!jogador.isValido()) {
			return;
		}

		Jogador streaker = partida
				.obterAtributoSessao(Partida.RANKING_STREAKER_PARTIDA);
		Comparator<Jogador> comparatorStreaker = new Ranking.ComparatorStreakers();

		if (streaker == null) {
			partida.criarAtributoSessao(Partida.RANKING_STREAKER_PARTIDA,
					jogador.clone());

		} else {
			int resultado = comparatorStreaker.compare(streaker, jogador);
			if (resultado > 0) {
				partida.criarAtributoSessao(Partida.RANKING_STREAKER_PARTIDA,
						jogador.clone());
			}
		}

	}

	private Jogador obterPrimeiroJogador() {
		List<Jogador> jogadores = partida.listarJogadores();
		return jogadores.get(Util.random(jogadores.size()));
	}

	private Jogador obterSegundoJogador(Jogador jogadorUm) {

		Jogador jogadorDois = obterPrimeiroJogador();
		return jogadorUm.equals(jogadorDois) ? obterSegundoJogador(jogadorDois)
				: jogadorDois;
	}

	private boolean ehTerminoPartida() {
		long now = System.currentTimeMillis();
		return (now - partida.getId()) > partida.getDuracao() * 1000;

	}

}
