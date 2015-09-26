package br.com.mauriciotsilva.jogo.partida.rank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.com.mauriciotsilva.jogo.partida.Jogador;

public class Ranking {

	public static List<Jogador> rankingAssassitos(Collection<Jogador> jogadores) {
		List<Jogador> lista = new ArrayList<>(jogadores);
		Collections.sort(lista, Ranking::compararAssassinos);

		return lista;
	}

	private static int compararAssassinos(Jogador jogador, Jogador outroJogador) {

		int resultado = (jogador.getQuantidadeAssassinato() - outroJogador.getQuantidadeAssassinato()) * -1;

		if (resultado == 0) {
			resultado = (jogador.getQuantidadeMorte() - outroJogador.getQuantidadeMorte()) * -1;
		} else

		if (resultado == 0) {
			resultado = jogador.compareTo(outroJogador) * -1;
		}

		return resultado;
	}

}
