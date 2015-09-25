package br.com.mauriciotsilva.jogo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.mauriciotsilva.jogo.io.ArquivoModelado;
import br.com.mauriciotsilva.jogo.partida.Partida;

public class Principal {

	public static void main(String[] args) throws CloneNotSupportedException, IOException {

		try (InputStream inputStream = Principal.class.getResourceAsStream("/info.log");
				InputStreamReader reader = new InputStreamReader(inputStream)) {

			Partida partida = new Partida(new ArquivoModelado(reader));
			System.out.println(partida.getVencedor());

		}

	}

}
