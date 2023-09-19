package br.com.projeto.apiservice.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		
		String usuarioNome = jwtRequest.getUsuarioNome();
		String usuarioSenha = jwtRequest.getUsuarioSenha();
		
		if(usuarioNome != null && usuarioSenha != null) {
			
			autenticacao(usuarioNome, usuarioSenha);	
			logger.info("createJwtToken {}" + usuarioSenha + usuarioSenha);
			
		}
		
		UserDetails userDetails = loadUserByUsername(usuarioNome);	
		
		String newGeneratedToken =  jwtUtil.generateToken(userDetails);		
		
		Usuario usuario = usuarioDao.findById(usuarioNome).get();
		logger.info("JwtService Usuário: {}" + usuario.getRole());

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
	
	private Set<SimpleGrantedAuthority> getAutorizacao(Usuario usuario) {
		
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
			
			throw new Exception("O usuário está desativado."+e);
			
		} catch (BadCredentialsException e) {
			
			throw new Exception("Credenciais incorretas do usuário."+e);
			
		}
	}

}
