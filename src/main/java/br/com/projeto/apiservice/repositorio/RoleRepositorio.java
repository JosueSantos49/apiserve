package br.com.projeto.apiservice.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projeto.apiservice.modelo.Role;

public interface RoleRepositorio extends JpaRepository<Role, String>{

}
