package br.com.mauriciotsilva.jogo.modelado;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.mauriciotsilva.jogo.estrutura.ItemModelavel;
import br.com.mauriciotsilva.jogo.estrutura.Modelavel;
import br.com.mauriciotsilva.jogo.partida.Jogador;

public class ModeloPartida implements Modelavel	 {

	private List<ItemModelavel> itens;

	public ModeloPartida() {
		itens = new ArrayList<ItemModelavel>();
	}

	public void criarItem(Jogador jogadorUm, Jogador jogadorDois, String arma) {
		adicionarItem(new EventoPartida(jogadorUm, jogadorDois, arma));

	}

	public void adicionarItem(ItemModelavel item) {
		itens.add(item);
	}

	@Override
	public Collection<ItemModelavel> getItens() {
		return itens;
	}

}
