package br.com.mauriciotsilva.jogo.io;

public class Palavra {

	private StringBuilder conteudo;

	public Palavra(StringBuilder conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public String toString() {
		return conteudo.toString();
	}

}
