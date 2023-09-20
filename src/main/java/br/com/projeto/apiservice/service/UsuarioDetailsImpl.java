package br.com.projeto.apiservice.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.projeto.apiservice.modelo.Usuario;

public class UsuarioDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 6526623349393728548L;
	
	private String usuarioNome;
		
	@JsonIgnore
	private String usuarioSenha;

	private Collection<? extends GrantedAuthority> authorities;
	
	public UsuarioDetailsImpl(String usuarioNome, String usuarioSenha, Collection<? extends GrantedAuthority> authorities) {
		//super();
		this.usuarioNome = usuarioNome;
		this.usuarioSenha = usuarioSenha;
		this.authorities = authorities;
	}
	
	 public static UsuarioDetailsImpl build(Usuario usuario) {
		    List<GrantedAuthority> authorities = usuario.getRole().stream()
		        .map(role -> new SimpleGrantedAuthority(role.getRoleNome()))
		        .collect(Collectors.toList());

		    return new UsuarioDetailsImpl(		        
		        usuario.getUsuarioNome(),
		        usuario.getUsuarioSenha(), 
		        authorities);
	 }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return authorities;
	}	
	
	@Override
	public String getPassword() {
		return usuarioSenha;
	}

	@Override
	public String getUsername() {
		return usuarioNome;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	  public boolean equals(Object o) {
	    if (this == o)
	      return true;
	    if (o == null || getClass() != o.getClass())
	      return false;
	    UsuarioDetailsImpl usuario = (UsuarioDetailsImpl) o;
	    return Objects.equals(usuarioNome, usuario.usuarioNome);
	  }

}
