package br.com.projeto.apiservice.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.apiservice.modelo.Pessoa;

@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, Long>{
    
}
