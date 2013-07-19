package br.com.mauriciotsilva.jogo.partida;

import java.util.Date;

import br.com.mauriciotsilva.jogo.Arma;
import br.com.mauriciotsilva.jogo.Award;
import br.com.mauriciotsilva.jogo.Jogador;
import br.com.mauriciotsilva.jogo.util.Mensagem;

public class Evento {

	private Date data;
	private Arma arma;
	private Jogador jogador;
	private Jogador outroJogador;
	private StringBuilder descricao;

	private Evento(Jogador jogador, Jogador outroJogador, Arma arma) {

		data = new Date();
		this.jogador = jogador;
		this.outroJogador = outroJogador;

		String mensagem = Mensagem.getMensagem(Mensagem.JOGO_PARTIDA_EVENTO,
				jogador, outroJogador, arma);
		descricao = new StringBuilder(mensagem);

	}

	public static Evento criarEvento(Jogador jogador, Jogador outroJogador,
			Arma arma) {
		return new Evento(jogador, outroJogador, arma);
	}

	public Evento anexar(Award award) {

		descricao.append(" ");
		descricao.append(Mensagem.getMensagem(Mensagem.JOGO_PARTIDA_CONQUISTA,
				award.getDescricao()));

		return this;
	}

	public Date getData() {
		return data;
	}

	public Arma getArma() {
		return arma;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public Jogador getOutroJogador() {
		return outroJogador;
	}

	@Override
	public String toString() {
		return descricao.toString();
	}
}
