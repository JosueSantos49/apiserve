package br.com.projeto.apiservice.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projeto.apiservice.modelo.Pessoa;

public interface PessoaRepositorio extends JpaRepository<Pessoa, Long>{
    
}
