package br.com.mauriciotsilva.jogo.partida;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.mauriciotsilva.jogo.estrutura.ItemModelavel;

public class Acontecimento implements Comparable<Acontecimento> {

	private Date data;
	private String arma;
	private Jogador jogadorUm;
	private Jogador jogadorDois;
	private String identificadorGrupo;

	private Partida partida;

	private Acontecimento(Partida partida) {
		this.partida = partida;
	}

	public static Acontecimento criar(Partida partida, ItemModelavel item) {

		Acontecimento acontecimento = new Acontecimento(partida);

		acontecimento.configurar(item);
		acontecimento.gerarIdentificadorGrupo();

		return acontecimento;
	}

	private void configurar(ItemModelavel item) throws IllegalStateException {

		if (item.isItemSistema()) {
			throw new IllegalStateException("System line cannot be used as parameter");
		}

		data = item.getData();
		arma = item.getMeioUtilizado();
		jogadorUm = partida.adicionarJogador(item.getSujeito());
		jogadorDois = partida.adicionarJogador(item.getObjetoDireto());

	}

	private void gerarIdentificadorGrupo() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		identificadorGrupo = simpleDateFormat.format(data);
	}

	public void executar() {
		gerarIdentificadorGrupo();
		jogadorUm.assassinar(arma, jogadorDois);
	}

	void adicionarPremiacaoPorMinuto(int contador) {
		if (contador == 5) {
			jogadorUm.adicionarPremio(Premio.PADAWAN);
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

	public String getIdentificadorGrupo() {
		return identificadorGrupo;
	}

	@Override
	public int compareTo(Acontecimento acontecimento) {

		int comparacao = identificadorGrupo.compareTo(acontecimento.identificadorGrupo);
		if (comparacao == 0) {
			comparacao = jogadorUm.compareTo(acontecimento.jogadorUm);
		}

		return comparacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identificadorGrupo == null) ? 0 : identificadorGrupo.hashCode());
		result = prime * result + ((jogadorUm == null) ? 0 : jogadorUm.hashCode());
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
		if (!(obj instanceof Acontecimento)) {
			return false;
		}
		Acontecimento other = (Acontecimento) obj;
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
