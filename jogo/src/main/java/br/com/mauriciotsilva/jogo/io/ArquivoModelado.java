package br.com.mauriciotsilva.jogo.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.mauriciotsilva.jogo.estrutura.ItemModelavel;
import br.com.mauriciotsilva.jogo.estrutura.Modelavel;

public class ArquivoModelado implements Modelavel {

	private Reader reader;
	private List<ItemModelavel> linhas;

	public ArquivoModelado(Reader reader) throws IOException {
		this.reader = reader;
		linhas = lerLinhasArquivo();
	}

	private List<ItemModelavel> lerLinhasArquivo() throws IOException {

		List<ItemModelavel> linhas = new ArrayList<>();
		try (BufferedReader buffer = new BufferedReader(reader)) {
			while (buffer.ready()) {
				linhas.add(new Linha(buffer.readLine()));
			}
		}
		return linhas;
	}

	@Override
	public Collection<ItemModelavel> getItens() {
		return linhas;
	}

}
