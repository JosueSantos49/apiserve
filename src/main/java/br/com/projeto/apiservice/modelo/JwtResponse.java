package br.com.projeto.apiservice.modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private Usuario usuario;
	private String jwtToken;
	
	public JwtResponse(Usuario usuario, String jwtToken) {
        this.usuario = usuario;
        this.jwtToken = jwtToken;
    }
	
}
