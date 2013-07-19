package br.com.mauriciotsilva.jogo;

import br.com.mauriciotsilva.jogo.partida.Partida;

public class Principal {
	
	public static void main(String[] args) throws CloneNotSupportedException {

			Partida partida = new Partida();

//		partida.definirDuracao(60);
//		partida.definirVelocidade(1);
		partida.iniciar();
	}

}
