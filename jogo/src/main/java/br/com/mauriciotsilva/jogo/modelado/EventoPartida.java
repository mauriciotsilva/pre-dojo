package br.com.mauriciotsilva.jogo.modelado;

import java.util.Date;

import br.com.mauriciotsilva.jogo.estrutura.ItemModelavel;
import br.com.mauriciotsilva.jogo.partida.Jogador;

public class EventoPartida implements ItemModelavel {

	private Date data;
	private Jogador jogadorUm;
	private Jogador jogadorDois;
	private String meioUtilizado;

	public EventoPartida(Jogador jogadorUm, Jogador jogadorDois, String arma) {
		this.data = new Date();
		this.meioUtilizado = arma;
		this.jogadorUm = jogadorUm;
		this.jogadorDois = jogadorDois;
	}

	@Override
	public Date getData() {
		return data;
	}

	@Override
	public String getMeioUtilizado() {
		return meioUtilizado;
	}

	@Override
	public String getSujeito() {
		return jogadorUm.getNome();
	}

	@Override
	public String getObjetoDireto() {
		return jogadorDois.getNome();
	}

	@Override
	public boolean isItemSistema() {
		return false;
	}

}
