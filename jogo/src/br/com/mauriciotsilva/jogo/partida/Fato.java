package br.com.mauriciotsilva.jogo.partida;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.mauriciotsilva.jogo.Award;
import br.com.mauriciotsilva.jogo.Jogador;
import br.com.mauriciotsilva.jogo.estrutura.ItemModelavel;

public class Fato implements Comparable<Fato> {

	private Date data;
	private String arma;
	private Jogador jogadorUm;
	private Jogador jogadorDois;
	private String identificadorGrupo;

	private Partida partida;

	private Fato(Partida partida) {
		this.partida = partida;
	}

	public static Fato criar(Partida partida, ItemModelavel item) {

		Fato fato = new Fato(partida);
		fato.configurarFato(item);
		fato.gerarIdentificadorGrupo();

		return fato;
	}

	private void configurarFato(ItemModelavel item)
			throws IllegalStateException {

		if (item.isItemSistema()) {
			throw new IllegalStateException(
					"System line cannot be used as parameter");
		}

		data = item.getData();
		arma = item.getMeioUtilizado();
		jogadorUm = partida.adicionarJogador(item.getSujeito());
		jogadorDois = partida.adicionarJogador(item.getObjetoDireto());

	}

	private void gerarIdentificadorGrupo() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm");
		identificadorGrupo = simpleDateFormat.format(data);
	}

	public void executar() {
		gerarIdentificadorGrupo();
		jogadorUm.assassinar(arma, jogadorDois);
	}

	void adicionarPremiacaoPorMinuto(int count) {
		if (count == 5) {
			jogadorUm.adicionarAward(Award.PADAWAN);
		}
	}

	public Date getData() {
		return data;
	}

	public Jogador getJogadorUm() {
		return jogadorUm;
	}

	public Jogador getJogadorDois() {
		return jogadorDois;
	}

	@Override
	public int compareTo(Fato outroFato) {

		int comp = identificadorGrupo.compareTo(outroFato.identificadorGrupo);
		if (comp == 0) {
			comp = jogadorUm.compareTo(outroFato.jogadorUm);
		}

		return comp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((identificadorGrupo == null) ? 0 : identificadorGrupo
						.hashCode());
		result = prime * result
				+ ((jogadorUm == null) ? 0 : jogadorUm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Fato)) {
			return false;
		}
		Fato other = (Fato) obj;
		if (identificadorGrupo == null) {
			if (other.identificadorGrupo != null) {
				return false;
			}
		} else if (!identificadorGrupo.equals(other.identificadorGrupo)) {
			return false;
		}
		if (jogadorUm == null) {
			if (other.jogadorUm != null) {
				return false;
			}
		} else if (!jogadorUm.equals(other.jogadorUm)) {
			return false;
		}
		return true;
	}

}
