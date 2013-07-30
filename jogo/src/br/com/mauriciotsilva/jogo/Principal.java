package br.com.mauriciotsilva.jogo;

import java.io.IOException;

import br.com.mauriciotsilva.jogo.partida.Partida;

public class Principal {

	public static void main(String[] args) throws CloneNotSupportedException,
			IOException {

		Partida partida = Partida.carregarArquivo("log/info.log");
		System.out.println(partida.getVencedor());

	}
}
