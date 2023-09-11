package br.com.projeto.apiservice.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projeto.apiservice.modelo.Produto;

@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto, Long>{
    
    
}
