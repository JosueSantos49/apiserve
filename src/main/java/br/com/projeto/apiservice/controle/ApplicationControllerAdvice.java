package br.com.projeto.apiservice.controle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.projeto.apiservice.excecao.RegistroNaoEncontradoExcecao;

/* Classe especial que prover sugestoes de como tratar excessao para toda aplicacao
 * Capturar e recomendar o que fazer com a excessoes que foram lancadas por quaiquer controle*/

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(RegistroNaoEncontradoExcecao.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotFoundException(RegistroNaoEncontradoExcecao ex) {
		return ex.getMessage();
	}
}
