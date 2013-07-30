package br.com.mauriciotsilva.jogo.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.mauriciotsilva.jogo.Jogador;

public class TesteJogador {

	public static final String AK47 = "AK47";
	public static final String M16 = "M16";
	public static final String PK7 = "PK7";

	private Jogador jogador;
	private Jogador outroJogador;
	private Jogador jogadorInvalido;

	@Before
	public void antesTeste() {
		jogador = new Jogador("Jogador 1");
		outroJogador = new Jogador("Jogador 2");
		jogadorInvalido = new Jogador("<WORLD>");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testarAssassinatoComParametroNulo() {
		jogador.assassinar(M16, null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testarTentativaDeSuicidio() {
		jogador.assassinar(M16, jogador);
	}

	@Test
	public void testarContadores() {

		jogador.assassinar(PK7, outroJogador);
		verificarContatores(jogador, 1, 0, 1);
		verificarContatores(outroJogador, 0, 1, 0);

		outroJogador.assassinar(M16, jogador);
		verificarContatores(jogador, 1, 1, 0);
		verificarContatores(outroJogador, 1, 1, 1);

		outroJogador.assassinar(PK7, jogador);
		verificarContatores(jogador, 1, 2, 0);
		verificarContatores(outroJogador, 2, 1, 2);

		jogador.assassinar(M16, outroJogador);
		verificarContatores(jogador, 2, 2, 1);
		verificarContatores(outroJogador, 2, 2, 0);

		jogador.assassinar(M16, outroJogador);
		verificarContatores(jogador, 3, 2, 2);
		verificarContatores(outroJogador, 2, 3, 0);

		jogadorInvalido.assassinar(AK47, jogador);
		verificarContatores(jogadorInvalido, 0, 0, 0);
		verificarContatores(jogador, 3, 3, 0);

		Assert.assertEquals("Arma favorita nao confere", M16,
				jogador.obterNomeArmaFavorita());

	}

	private void verificarContatores(Jogador jogador, int assassinato,
			int morte, int streak) {

		verificarNumeroAssanatos(jogador, assassinato);
		verificarNumeroMorte(jogador, morte);
		verificarNumeroStreak(jogador, streak);
	}

	private void verificarNumeroMorte(Jogador jogador, int valor) {
		Assert.assertTrue("Numero Morte: " + jogador,
				jogador.getQuantidadeMorte() == valor);
	}

	private void verificarNumeroAssanatos(Jogador jogador, int valor) {
		Assert.assertTrue("Numero Assassinato: " + jogador,
				jogador.getQuantidadeAssassinato() == valor);
	}

	private void verificarNumeroStreak(Jogador jogador, int valor) {
		Assert.assertTrue("Numero Streak:" + jogador,
				jogador.getNumeroStreak() == valor);
	}

}
