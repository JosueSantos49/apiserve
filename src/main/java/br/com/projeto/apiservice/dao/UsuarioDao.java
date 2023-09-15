package br.com.projeto.apiservice.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.apiservice.modelo.Usuario;

@Repository
public interface UsuarioDao extends CrudRepository<Usuario, String>{

}
