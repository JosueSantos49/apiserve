package br.com.projeto.apiservice.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.modelo.Role;
import br.com.projeto.apiservice.service.RoleService;

@RestController
public class RoleControle {

	@Autowired
	private RoleService roleService;
	
	@PostMapping({"/createNewRole"})
	public Role createNewRole(@RequestBody Role role) {
		return roleService.crateNewRole(role);
	}
}
