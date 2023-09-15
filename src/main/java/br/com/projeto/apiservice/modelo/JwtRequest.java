package br.com.projeto.apiservice.modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {

	private String usuarioNome;
	private String usuarioSenha;
	
	
}
