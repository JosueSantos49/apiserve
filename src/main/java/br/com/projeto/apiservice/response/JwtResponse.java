package br.com.projeto.apiservice.response;

import br.com.projeto.apiservice.modelo.Usuario;

public class JwtResponse {

	private Usuario usuario;
	private String jwtToken;
	
	//Original
	public JwtResponse(Usuario usuario, String jwtToken) {
        this.usuario = usuario;
        this.jwtToken = jwtToken;
    }
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	
}
