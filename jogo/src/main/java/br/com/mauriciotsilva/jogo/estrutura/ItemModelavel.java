package br.com.mauriciotsilva.jogo.estrutura;

import java.util.Date;

public interface ItemModelavel {

	public Date getData();

	public String getMeioUtilizado();

	public String getSujeito();

	public String getObjetoDireto();

	public boolean isItemSistema();
}
