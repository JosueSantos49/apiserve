package br.com.projeto.apiservice.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.apiservice.modelo.Produto;

@Repository
public interface Repositorio extends CrudRepository<Produto, Long>{
    
    
}
