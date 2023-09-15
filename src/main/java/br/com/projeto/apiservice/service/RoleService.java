package br.com.projeto.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.apiservice.dao.RoleDao;
import br.com.projeto.apiservice.modelo.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	
	public Role crateNewRole(Role role) {
		return roleDao.save(role);
	}
}
