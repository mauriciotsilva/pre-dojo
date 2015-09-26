package br.com.mauriciotsilva.jogo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import br.com.mauriciotsilva.jogo.estrutura.Modelavel;
import br.com.mauriciotsilva.jogo.io.ArquivoModelado;
import br.com.mauriciotsilva.jogo.partida.Partida;

public class App {

	private static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws CloneNotSupportedException, IOException {

		Modelavel modelavel = getModelado("/info.log");
		Partida partida = new Partida(modelavel);

		logger.info(partida.getVencedor());

	}

	private static Modelavel getModelado(String file) throws IOException {
		try (InputStream inputStream = App.class.getResourceAsStream(file);
				InputStreamReader reader = new InputStreamReader(inputStream);) {
			return new ArquivoModelado(reader);
		}
	}

}
