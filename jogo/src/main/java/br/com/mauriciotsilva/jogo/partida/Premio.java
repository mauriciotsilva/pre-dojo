package br.com.mauriciotsilva.jogo.partida;

import static br.com.mauriciotsilva.jogo.util.Mensagem.JOGO_PARTIDA_CONQUISTA_NORRIS;
import static br.com.mauriciotsilva.jogo.util.Mensagem.JOGO_PARTIDA_CONQUISTA_PADAWAN;
import static br.com.mauriciotsilva.jogo.util.Mensagem.getMensagem;

public enum Premio {

	PADAWAN(getMensagem(JOGO_PARTIDA_CONQUISTA_PADAWAN)),

	NORRIS(getMensagem(JOGO_PARTIDA_CONQUISTA_NORRIS));

	private String descricao;

	private Premio(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
