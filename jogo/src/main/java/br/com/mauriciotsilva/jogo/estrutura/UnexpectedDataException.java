package br.com.mauriciotsilva.jogo.estrutura;

public class UnexpectedDataException extends RuntimeException {

	private static final long serialVersionUID = -8063923387689038535L;

	public UnexpectedDataException(Throwable throwable) {
		super(throwable);
	}

}
