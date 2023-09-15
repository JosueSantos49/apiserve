package br.com.projeto.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.apiservice.dao.UsuarioDao;
import br.com.projeto.apiservice.modelo.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;
	
	public Usuario registrarNovoUsuario(Usuario usuario) {
		return usuarioDao.save(usuario);
	}
}
