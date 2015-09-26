package br.com.mauriciotsilva.jogo.partida;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.mauriciotsilva.jogo.io.Linha;

public class Jogador implements Serializable, Comparable<Jogador>, Cloneable {

	private static final long serialVersionUID = 846984605196036134L;

	private String nome;

	private boolean valido;
	private int numeroStreak;
	private int quantidadeMorte;
	private int quantidadeAssassinato;

	private LinkedList<Premio> premios;
	private Map<String, Integer> armaFavorita;

	public Jogador(String nome) {
		this(nome, !Linha.WORLD.equals(nome));
	}

	private Jogador(String nome, boolean valido) {
		this.nome = nome;
		this.valido = valido;
		this.premios = new LinkedList<>();
		this.armaFavorita = new HashMap<>();
	}

	public void assassinar(String arma, Jogador outroJogador) {

		validarAssassinato(arma, outroJogador);
		outroJogador.morrer();
		atualizarContadores(arma);
	}

	private void validarAssassinato(String arma, Jogador outroJogador) {
		if (outroJogador == null || equals(outroJogador)) {
			throw new IllegalArgumentException("Wrong player try to kill other one");
		}
	}

	private void morrer() {
		numeroStreak = 0;
		quantidadeMorte++;
	}

	private void atualizarContadores(String arma) {
		if (valido) {
			numeroStreak++;
			quantidadeAssassinato++;
		}

		Integer quantidade = armaFavorita.get(arma);
		if (quantidade == null) {
			quantidade = 0;
		}

		armaFavorita.put(arma, ++quantidade);
	}

	public void adicionarPremio(Premio primio) {

		if (primio == Premio.NORRIS) {
			premios.addFirst(primio);
		} else {
			premios.add(primio);
		}
	}

	public String getNomeArmaFavorita() {

		int quantidade = 0;
		String arma = null;

		Set<String> armas = armaFavorita.keySet();
		for (String a : armas) {
			int valor = armaFavorita.get(a);
			if (valor > quantidade) {
				quantidade = valor;
				arma = a;
			}

		}

		return arma;
	}

	public String getNome() {
		return nome;
	}

	public int getQuantidadeMorte() {
		return quantidadeMorte;
	}

	public int getQuantidadeAssassinato() {
		return quantidadeAssassinato;
	}

	public boolean isValido() {
		return valido;
	}

	public int getNumeroStreak() {
		return numeroStreak;
	}

	public List<Premio> getPremios() {
		return premios;
	}

	@Override
	public int compareTo(Jogador outroJogador) {
		return nome.compareTo(outroJogador.nome);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jogador other = (Jogador) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public Jogador clone() {
		try {
			return (Jogador) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		return nome;
	}

}
