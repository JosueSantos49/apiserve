package br.com.projeto.apiservice.response;

import java.util.List;

public class UsuarioInfoResponse {

	private String usuarioNome;
	
	private String usuarioPrimeiroNome;
	
	private String usuarioUltimoNome;
		
	private List<String> roles;

	/*
	public UsuarioInfoResponse(String usuarioNome, String usuarioPrimeiroNome, String usuarioUltimoNome,
			List<String> roles) {
		//super();
		this.usuarioNome = usuarioNome;
		this.usuarioPrimeiroNome = usuarioPrimeiroNome;
		this.usuarioUltimoNome = usuarioUltimoNome;
		this.roles = roles;
	}*/

	public UsuarioInfoResponse(String username, List<String> roles) {
		this.usuarioNome = username;
		this.roles = roles;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public String getUsuarioPrimeiroNome() {
		return usuarioPrimeiroNome;
	}

	public void setUsuarioPrimeiroNome(String usuarioPrimeiroNome) {
		this.usuarioPrimeiroNome = usuarioPrimeiroNome;
	}

	public String getUsuarioUltimoNome() {
		return usuarioUltimoNome;
	}

	public void setUsuarioUltimoNome(String usuarioUltimoNome) {
		this.usuarioUltimoNome = usuarioUltimoNome;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
	
}
