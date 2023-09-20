package br.com.projeto.apiservice.controle;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.repositorio.UsuarioRepositorio;
import br.com.projeto.apiservice.request.JwtRequest;
import br.com.projeto.apiservice.response.JwtResponse;
import br.com.projeto.apiservice.response.UsuarioInfoResponse;
import br.com.projeto.apiservice.service.JwtService;
import br.com.projeto.apiservice.service.UsuarioDetailsImpl;
import br.com.projeto.apiservice.util.JwtUtil;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class JwtControle {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtControle.class);
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	private JwtService jwtService;
	
	@PostMapping({"/entrar"})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		//return jwtService.createJwtToken(jwtRequest);
		
		JwtResponse jwtResponse = jwtService.createJwtToken(jwtRequest);
		logger.info("createJwtToken {} ", jwtResponse);
		return jwtResponse;
			
	}
	
	@PostMapping("/entrar-error-no-cast")
	public ResponseEntity<?> authenticateUser (@Valid @RequestBody JwtRequest jwtRequest){
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsuarioNome(), jwtRequest.getUsuarioSenha()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//erro nesta linhaa
		UsuarioDetailsImpl usuarioDetails = (UsuarioDetailsImpl) authentication.getPrincipal();		
				
		ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(usuarioDetails);
		
		List<String> roles = usuarioDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
		        .body(new UsuarioInfoResponse(usuarioDetails.getUsername(),roles));		        
	}
}
