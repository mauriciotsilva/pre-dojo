package br.com.mauriciotsilva.jogo.io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.mauriciotsilva.jogo.estrutura.ItemModelavel;
import br.com.mauriciotsilva.jogo.estrutura.UnexpectedDataException;

public class Linha implements ItemModelavel {

	public static final String MATOU = "killed";
	public static final String WORLD = "<world>";
	public static final String SEPARADOR = "-";
	public static final String PATTER_DATAHORA = "[1-31]./+\\d{2}.(\\d{4}\\s).*\\d+[\\:].*$";

	private String conteudo;
	private String sujeito;
	private String objetoDireto;
	private String meioUtilizado;

	public Linha(String conteudo) {

		this.conteudo = conteudo;

		List<String> palavras = listarPalavras();

		String verbo = palavras.get(1);
		if (MATOU.equals(verbo)) {
			sujeito = palavras.get(0);
			objetoDireto = palavras.get(2);
			meioUtilizado = palavras.get(palavras.size() - 1);
		}

	}

	@Override
	public boolean isItemSistema() {
		return sujeito == null && objetoDireto == null;
	}

	private List<String> listarPalavras() {

		List<String> palavras = new ArrayList<>(5);
		String informacoes = conteudo.split(SEPARADOR)[1];

		for (String palavra : informacoes.split("\\s")) {
			if (!palavra.isEmpty()) {
				palavras.add(palavra.trim());
			}
		}

		return palavras;
	}

	private String extrairDataHora(String linha) {

		Pattern pattern = Pattern.compile(PATTER_DATAHORA);
		Matcher matcher = pattern.matcher(linha);

		StringBuilder builder = new StringBuilder();
		while (matcher.find()) {
			builder.append(matcher.group());
		}

		return builder.toString();
	}

	@Override
	public Date getData() throws UnexpectedDataException {
		try {

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return simpleDateFormat.parse(extrairDataHora(conteudo));
		} catch (ParseException e) {
			throw new UnexpectedDataException(e);
		}
	}

	@Override
	public String getMeioUtilizado() {
		return meioUtilizado;
	}

	@Override
	public String getObjetoDireto() {
		return objetoDireto;
	}

	@Override
	public String getSujeito() {
		return sujeito;
	}

	@Override
	public String toString() {
		return conteudo;
	}

}
