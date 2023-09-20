package br.com.projeto.apiservice.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projeto.apiservice.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{

}
