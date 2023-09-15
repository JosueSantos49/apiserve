package br.com.projeto.apiservice.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.modelo.Usuario;
import br.com.projeto.apiservice.service.UsuarioService;

@RestController
public class UsuarioControle {
	
	@Autowired
	private UsuarioService usuarioService;

	@PostMapping({"/registrarNovoUsuario"})
	public Usuario registrarNovoUsuario(@RequestBody Usuario usuario) {
		return usuarioService.registrarNovoUsuario(usuario);
	}
	
}
