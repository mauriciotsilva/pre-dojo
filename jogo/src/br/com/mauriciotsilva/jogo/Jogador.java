package br.com.mauriciotsilva.jogo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Jogador implements Serializable, Cloneable {

	private static final long serialVersionUID = 846984605196036134L;

	private String nome;

	private boolean valido;
	private int numeroStreak;
	private int quantidadeMorte;
	private int quantidadeAssassinato;
	private int quantidadeAssassinatoPorMinuto;

	private LinkedList<Award> awards;
	private Map<Arma, Integer> armaFavorita;

	public Jogador(String nome) {
		this(nome, true);
	}

	public Jogador(String nome, boolean valido) {
		this.nome = nome;
		this.valido = valido;
		this.awards = new LinkedList<Award>();
		this.armaFavorita = new HashMap<Arma, Integer>();
	}

	public boolean assassinar(Arma arma, Jogador outroJogador)
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

	private void matar(Arma arma, Jogador outroJogador) {

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
			quantidadeAssassinatoPorMinuto++;
		}
	}

	public void iniciarAssassinatosPorSegundo() {
		quantidadeAssassinatoPorMinuto = 0;
	}

	public void adicionarAward(Award award) {

		if (award == Award.NORRIS)
			awards.addFirst(award);
		else
			awards.add(award);

	}
	
	public String obterArmaFavorita(){
		
		int quantidade = 0;
		Arma arma = null;
		Set<Arma> armas = armaFavorita.keySet();
		for(Arma a : armas){
		 	int valor = armaFavorita.get(a);
		 	if(valor > quantidade){
		 		quantidade = valor;
		 		arma = a;
		 	}
		 	
		}
		
		return arma == null ? "" : arma.name()+" "+quantidade;
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

	public int getQuantidadeAssassinatoPorMinuto() {
		return quantidadeAssassinatoPorMinuto;
	}

	public List<Award> getAwards() {
		return awards;
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
	public Jogador clone() throws CloneNotSupportedException {
		return (Jogador) super.clone();
	}

	@Override
	public String toString() {
		return nome;
	}

}
