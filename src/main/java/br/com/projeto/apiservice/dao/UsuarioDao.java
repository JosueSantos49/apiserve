package br.com.projeto.apiservice.dao;

import org.springframework.data.repository.CrudRepository;

import br.com.projeto.apiservice.modelo.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, String>{

}
