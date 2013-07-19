package br.com.mauriciotsilva.jogo.partida.rank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.mauriciotsilva.jogo.Jogador;

public class Ranking {

	protected final List<Jogador> lista;

	private Ranking(Collection<Jogador> collection) {
		lista = new ArrayList<Jogador>(collection);
	}

	protected final List<Jogador> listar() {
		return Collections.unmodifiableList(lista);
	}

	public static List<Jogador> rankingAssassitos(Collection<Jogador> jogadores) {
		return new ComparatorAssassinos(jogadores).listar();
	}

	public static class ComparatorStreakers implements Comparator<Jogador> {

		@Override
		public int compare(Jogador jogadorUm, Jogador jogadorDois) {
			return jogadorDois.getNumeroStreak() - jogadorUm.getNumeroStreak();
		}
	}

	private static class ComparatorAssassinos extends Ranking implements
			Comparator<Jogador> {

		public ComparatorAssassinos(Collection<Jogador> collection) {
			super(collection);
			Collections.sort(lista, this);
		}

		@Override
		public int compare(Jogador jogadorUm, Jogador jogadorDois) {

			int resultado = 0;

			if (jogadorUm.getQuantidadeAssassinato() > jogadorDois
					.getQuantidadeAssassinato()) {
				resultado = -1;
			} else if (jogadorUm.getQuantidadeAssassinato() < jogadorDois
					.getQuantidadeAssassinato()) {
				resultado = 1;
			}

			if (resultado == 0) {
				if (jogadorUm.getQuantidadeMorte() > jogadorDois
						.getQuantidadeMorte()) {
					resultado = -1;
				} else if (jogadorUm.getQuantidadeMorte() < jogadorDois
						.getQuantidadeMorte()) {
					resultado = 1;
				}
			}

			if (resultado == 0) {
				if (jogadorUm.getNome().compareTo(jogadorDois.getNome()) > 0) {
					resultado = -1;
				} else if (jogadorUm.getNome().compareTo(jogadorDois.getNome()) < 0) {
					resultado = 1;
				}
			}

			return resultado;
		}

	}

}
