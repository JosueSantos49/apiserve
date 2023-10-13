package br.com.projeto.apiservice.excecao;

public class RegistroNaoEncontradoExcecao extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public RegistroNaoEncontradoExcecao(Long codigo) {
		super("Registro não encontrado com o código: " + codigo);
	}
}
