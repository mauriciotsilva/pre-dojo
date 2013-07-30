package br.com.mauriciotsilva.jogo.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
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

	private ArquivoModelado(Reader reader) {
		this.reader = reader;
		linhas = new ArrayList<ItemModelavel>();
	}

	public static ArquivoModelado carregar(String nomeArquivo)
			throws IOException {

		FileReader reader = new FileReader(new File(nomeArquivo));
		ArquivoModelado arquivo = carregar(reader);
		arquivo.finalizarRecurso(reader);

		return arquivo;
	}

	public static ArquivoModelado carregar(Reader reader)
			throws IOException {

		ArquivoModelado arquivo = new ArquivoModelado(reader);
		arquivo.lerLinhasArquivo();

		return arquivo;
	}

	private void lerLinhasArquivo() throws IOException {
		BufferedReader buffer = new BufferedReader(reader);

		while (buffer.ready()) {
			Linha linha = new Linha(buffer.readLine());
			linhas.add(linha);
		}

		finalizarRecurso(buffer);
	}

	@Override
	public Collection<ItemModelavel> getItens() {
		return linhas;
	}

	private void finalizarRecurso(Closeable closeable) throws IOException {
		closeable.close();
	}

}
