package br.com.projeto.apiservice.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.modelo.JwtRequest;
import br.com.projeto.apiservice.modelo.JwtResponse;
import br.com.projeto.apiservice.service.JwtService;

@RestController
@CrossOrigin(origins = "*")
public class JwtControle {

	@Autowired
	private JwtService jwtService;
	
	@PostMapping({"/autenticacao"})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		return jwtService.createJwtToken(jwtRequest);
	}
}
