package br.com.projeto.apiservice.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.modelo.Usuario;
import br.com.projeto.apiservice.service.UsuarioService;
import jakarta.annotation.PostConstruct;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
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
		return "Esta URL só é acessível ao administrador.";
	}
	
	@GetMapping({"/paraUsuario"})
	@PreAuthorize("hasAnyRole('Usuario')")
	public String paraUsuario( ) {
		return "Esta URL só é acessível ao Usuário.";
	}
}
