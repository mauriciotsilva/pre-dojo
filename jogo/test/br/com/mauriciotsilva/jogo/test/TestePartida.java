package br.com.mauriciotsilva.jogo.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.mauriciotsilva.jogo.Arma;
import br.com.mauriciotsilva.jogo.Jogador;
import br.com.mauriciotsilva.jogo.partida.Partida;

public class TestePartida {

	private Partida partida;
	private Jogador jogador;
	private Jogador jogadorInvalido;

	@Before
	public void antesTeste() {
		partida = new Partida(iniciarJogadores());
	}

	private List<Jogador> iniciarJogadores() {

		jogador = new Jogador("Jogar");
		jogadorInvalido = new Jogador("<WORD>", false);

		Jogador outro = new Jogador("Outro");
		outro.assassinar(Arma.random(), jogador);
		jogadorInvalido.assassinar(Arma.random(), outro);
		jogador.assassinar(Arma.random(), outro);
		jogador.assassinar(Arma.random(), jogadorInvalido);
		jogador.assassinar(Arma.random(), outro);

		return Arrays.asList(jogador, jogadorInvalido, outro);

	}

	@Test
	public void testarRankingAssassinos() {

		List<Jogador> jogadores = partida.listarRankingAssassinatos();
		Assert.assertSame(jogador, jogadores.get(0));
		Assert.assertSame(jogadorInvalido, jogadores.get(jogadores.size() - 1));

	}
}
