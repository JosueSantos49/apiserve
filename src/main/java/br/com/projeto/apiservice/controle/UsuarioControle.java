package br.com.projeto.apiservice.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.modelo.Usuario;
import br.com.projeto.apiservice.service.UsuarioService;
import jakarta.annotation.PostConstruct;

@RestController
@CrossOrigin(origins = "*")
public class UsuarioControle {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostConstruct
	public void initRoleUsuario() {
		usuarioService.initRoleUsuario();
	}

	@PostMapping({"/registrarNovoUsuario"})
	public Usuario registrarNovoUsuario(@RequestBody Usuario usuario) {
		return usuarioService.registrarNovoUsuario(usuario);
	}
	
	@GetMapping({"/paraAdmin"})
	@PreAuthorize("hasRole('Admin')")
	public String paraAdmin( ) {
		return "Este URL só é acessível ao administrador.";
	}
	
	@GetMapping({"/paraUsuario"})
	@PreAuthorize("hasRole('User')")
	public String paraUsuario( ) {
		return "Este URL só é acessível ao Usuário.";
	}
}
