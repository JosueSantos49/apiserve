package br.com.projeto.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.apiservice.modelo.Role;
import br.com.projeto.apiservice.repositorio.RoleRepositorio;

@Service
public class RoleService {

	@Autowired
	private RoleRepositorio roleDao;
	
	public Role crateNewRole(Role role) {
		return roleDao.save(role);
	}
}
