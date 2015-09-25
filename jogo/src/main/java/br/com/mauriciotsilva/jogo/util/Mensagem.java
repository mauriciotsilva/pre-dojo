package br.com.mauriciotsilva.jogo.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Mensagem {

	private static final ResourceBundle bundle = ResourceBundle
			.getBundle("message");

	public static String JOGO_PARTIDA_EVENTO = "jogo.partida.evento";
	public static String JOGO_PARTIDA_INICIO = "jogo.partida.inicio";
	public static String JOGO_PARTIDA_TERMINO = "jogo.partida.termino";
	public static String JOGO_PARTIDA_CONQUISTA = "jogo.partida.conquista";
	public static String JOGO_PARTIDA_CONQUISTA_PADAWAN = "jogo.partida.conquista.padawan";
	public static String JOGO_PARTIDA_CONQUISTA_NORRIS = "jogo.partida.conquista.norris";

	public static String getMensagem(String chave, Object... argumentos) {
		return MessageFormat.format(getMensagem(chave), argumentos);
	}

	public static String getMensagem(String chave) {
		return bundle.getString(chave);
	}
}
