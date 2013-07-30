package br.com.mauriciotsilva.jogo.io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.mauriciotsilva.jogo.estrutura.ItemModelavel;

/**
 * @author mtrindade
 * 
 */
public class Linha implements ItemModelavel {

	public static final String WORLD = "<WORLD>";
	public static final String MATAR = "killed";

	private String conteudo;
	private String sujeito;
	private String objetoDireto;
	private String meioUtilizado;
	private List<String> palavras;

	private final Character separador = '-';
	private final String patternDataHora = "[1-31]./+\\d{2}.(\\d{4}\\s).*\\d+[\\:].*$";

	public Linha(String conteudo) {

		this.conteudo = conteudo;
		palavras = new ArrayList<String>(5);
		tratarPalavras();
	}

	@Override
	public boolean isItemSistema() {
		return sujeito == null && objetoDireto == null;
	}

	private void tratarPalavras() {

		extrairPalavras();

		String verbo = palavras.get(1);
		if (MATAR.equals(verbo)) {
			sujeito = palavras.get(0);
			objetoDireto = palavras.get(2);
			meioUtilizado = palavras.get(palavras.size() - 1);
		}

	}

	private void extrairPalavras() {

		char[] chars = conteudo.toCharArray();

		boolean temInformacaoEvento = false;
		StringBuilder builder = new StringBuilder();

		for (Character charr : chars) {
			if (temInformacaoEvento) {
				if (Character.isWhitespace(charr)) {
					adicionarPalavra(builder.toString());
					builder = new StringBuilder();
				} else {
					builder.append(charr);
				}
			}

			if (charr == separador) {
				temInformacaoEvento = true;
			}
		}

		adicionarPalavra(builder.toString());
	}

	private void adicionarPalavra(String str) {
		if (!str.isEmpty()) {
			palavras.add(str.trim());
		}
	}

	public Date obterData() throws UnexpectedDataException {

		try {

			String stringData = extrairDataHora(conteudo);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");

			return simpleDateFormat.parse(stringData);
		} catch (ParseException e) {
			throw new UnexpectedDataException(e);
		}

	}

	private String extrairDataHora(String linha) {

		Pattern pattern = Pattern.compile(patternDataHora);
		Matcher matcher = pattern.matcher(linha);

		StringBuilder builder = new StringBuilder();
		while (matcher.find()) {
			builder.append(matcher.group());
		}

		return builder.toString();
	}

	@Override
	public Date getData() {
		return obterData();
	}

	@Override
	public String getMeioUtilizado() {
		return meioUtilizado;
	}

	@Override
	public String getObjetoDireto() {
		return objetoDireto.toString();
	}

	@Override
	public String getSujeito() {
		return sujeito.toString();
	}

	@Override
	public String toString() {
		return conteudo;
	}

}
