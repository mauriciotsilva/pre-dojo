package br.com.mauriciotsilva.jogo;

import br.com.mauriciotsilva.jogo.util.Util;

public enum Arma {

	M16, AK47, PK7, RPG7, BALLISTA;

	public static Arma random() {
		Arma[] armas = Arma.values();
		return armas[Util.random(armas.length)];
	}
}
