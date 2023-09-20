package br.com.projeto.apiservice.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.projeto.apiservice.modelo.Role;
import br.com.projeto.apiservice.modelo.Usuario;
import br.com.projeto.apiservice.repositorio.RoleRepositorio;
import br.com.projeto.apiservice.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio usuarioDao;
	
	@Autowired
	private RoleRepositorio roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void initRoleUsuario() {
		Role adminRole = new Role();
		adminRole.setRoleNome("Admin");
		adminRole.setRoleDescricao("Admin role");
		roleDao.save(adminRole);
		
		Role usuarioRole = new Role();
		usuarioRole.setRoleNome("Usuario");
		usuarioRole.setRoleDescricao("Função padrão para registro recém-criado");
		roleDao.save(usuarioRole);
		
		Usuario adminUsuario = new Usuario();
		adminUsuario.setUsuarioPrimeiroNome("admin");
		adminUsuario.setUsuarioUltimoNome("admin");
		adminUsuario.setUsuarioNome("admin123");
		adminUsuario.setUsuarioSenha(getEncodedPassword("admin@pass"));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUsuario.setRole(adminRoles);
		usuarioDao.save(adminUsuario);
		
		/**/
		Usuario usuario = new Usuario();
		usuario.setUsuarioPrimeiroNome("raj");
		usuario.setUsuarioUltimoNome("sharma");
		usuario.setUsuarioNome("raj123");
		usuario.setUsuarioSenha(getEncodedPassword("raj@pass"));
		Set<Role> usuarioRoles = new HashSet<>();
		usuarioRoles.add(usuarioRole);
		usuario.setRole(usuarioRoles);
		usuarioDao.save(usuario);	
			
	}
	
	public Usuario registrarNovoUsuario(Usuario usuario) {
		
		Role role = roleDao.findById("Usuario").get();
		
		Set<Role> usuarioRoles = new HashSet<>();
		usuarioRoles.add(role);
		usuario.setRole(usuarioRoles);		
		usuario.setUsuarioSenha(getEncodedPassword(usuario.getUsuarioSenha()));
		
		return usuarioDao.save(usuario);
	}
	
	public String getEncodedPassword(String senha) {
		return passwordEncoder.encode(senha);
	}
}
