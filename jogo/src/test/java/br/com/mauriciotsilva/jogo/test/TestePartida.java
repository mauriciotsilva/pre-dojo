package br.com.mauriciotsilva.jogo.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.mauriciotsilva.jogo.estrutura.Modelavel;
import br.com.mauriciotsilva.jogo.modelado.ModeloPartida;
import br.com.mauriciotsilva.jogo.partida.Jogador;
import br.com.mauriciotsilva.jogo.partida.Partida;

public class TestePartida {

	private Partida partida;

	private Jogador vettel;
	private Jogador alonso;
	private Jogador jogadorInvalido;

	@Before
	public void antesTeste() {

		vettel = new Jogador("Vettel");
		alonso = new Jogador("Alonso");
		jogadorInvalido = new Jogador("<WORLD>");

		partida = new Partida(criaModelo());
	}

	private Modelavel criaModelo() {

		ModeloPartida modelo = new ModeloPartida();

		modelo.criarItem(alonso, vettel, TesteJogador.M16);
		modelo.criarItem(vettel, alonso, TesteJogador.M16);
		modelo.criarItem(jogadorInvalido, alonso, TesteJogador.AK47);
		modelo.criarItem(vettel, alonso, TesteJogador.PK7);
		modelo.criarItem(vettel, alonso, TesteJogador.PK7);
		modelo.criarItem(vettel, alonso, TesteJogador.PK7);
		modelo.criarItem(vettel, alonso, TesteJogador.PK7);

		return modelo;

	}

	@Test
	public void testarPremiacao() {
		Assert.assertEquals(1, partida.getVencedor().getPremios().size());
	}

	@Test
	public void testarStreakerPartida() {
		Assert.assertEquals(vettel, partida.getStreaker());
	}

	@Test
	public void testarBuscaJogador() {
		Assert.assertEquals(alonso, partida.buscarJogador(alonso.getNome()));
	}

	@Test
	public void testarRankingAssassinos() {

		List<Jogador> jogadores = partida.listarRankingAssassinatos();
		Assert.assertEquals(vettel, partida.getVencedor());
		Assert.assertEquals(jogadorInvalido, jogadores.get(jogadores.size() - 1));

	}
}
