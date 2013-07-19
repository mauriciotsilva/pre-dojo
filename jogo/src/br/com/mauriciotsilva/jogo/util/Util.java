package br.com.mauriciotsilva.jogo.util;


public final class Util {

	public static int random() {
		return random(10);
	}

	public static int random(int base) {
		return (int) (Math.random() * base);
	}

}
