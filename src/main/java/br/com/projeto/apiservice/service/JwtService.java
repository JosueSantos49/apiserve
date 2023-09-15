package br.com.projeto.apiservice.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.projeto.apiservice.dao.UsuarioDao;
import br.com.projeto.apiservice.modelo.JwtRequest;
import br.com.projeto.apiservice.modelo.JwtResponse;
import br.com.projeto.apiservice.modelo.Usuario;
import br.com.projeto.apiservice.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService{

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String usuarioNome = jwtRequest.getUsuarioNome();
		String usuarioSenha = jwtRequest.getUsuarioSenha();
		autenticacao(usuarioNome, usuarioSenha);
		
		UserDetails userDetails = loadUserByUsername(usuarioNome);
		String newGeneratedToken =  jwtUtil.generateToken(userDetails);
		
		Usuario usuario = usuarioDao.findById(usuarioNome).get();
		return new JwtResponse(usuario, newGeneratedToken);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		Usuario usuario = usuarioDao.findById(username).get();
		
		if(usuario != null) {
			return new org.springframework.security.core.userdetails.User(
					usuario.getUsuarioNome(),
					usuario.getUsuarioSenha(),
					getAutorizacao(usuario)
			);
		}else {
			throw new UsernameNotFoundException("O nome de usuário não é válido.");
		}
	}
	
	private Set getAutorizacao(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		usuario.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleNome()));
		});
		return authorities;
	}
	
	private void autenticacao(String usuarioNome, String usuarioSenha) throws Exception{
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioNome, usuarioSenha));		
			
		} catch (DisabledException e) {
			throw new Exception("O usuário está desativado.");
		} catch (BadCredentialsException e) {
			throw new Exception("Credenciais incorretas do usuário.");
		}
	}

}
