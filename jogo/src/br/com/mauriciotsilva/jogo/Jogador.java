package br.com.mauriciotsilva.jogo;

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

	private LinkedList<Award> awards;
	private Map<String, Integer> armaFavorita;

	public Jogador(String nome) {
		this(nome, !Linha.WORLD.equals(nome));
	}

	private Jogador(String nome, boolean valido) {
		this.nome = nome;
		this.valido = valido;
		this.awards = new LinkedList<Award>();
		this.armaFavorita = new HashMap<String, Integer>();
	}

	public boolean assassinar(String arma, Jogador outroJogador)
			throws UnsupportedOperationException, IllegalArgumentException {

		if (outroJogador == null) {
			throw new IllegalArgumentException(
					"Wrong player try to kill other one");
		} else

		if (this.equals(outroJogador)) {
			throw new UnsupportedOperationException("Suicide is not allowed");
		}

		matar(arma, outroJogador);

		return true;
	}

	private void matar(String arma, Jogador outroJogador) {

		atualizarContador();
		outroJogador.morrer();

		Integer quantidade = armaFavorita.get(arma);
		if (quantidade == null) {
			armaFavorita.put(arma, 1);
		} else {
			armaFavorita.put(arma, ++quantidade);
		}
	}

	private void atualizarContador() {
		if (valido) {
			numeroStreak++;
			quantidadeAssassinato++;
		}
	}

	public void adicionarAward(Award award) {

		if (award == Award.NORRIS)
			awards.addFirst(award);
		else
			awards.add(award);

	}

	public String obterNomeArmaFavorita() {

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

		return arma == null ? "" : arma;
	}

	private void morrer() {
		numeroStreak = 0;
		quantidadeMorte++;
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

	public List<Award> getAwards() {
		return awards;
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
		}

		return null;
	}

	@Override
	public String toString() {
		return nome;
	}

}
