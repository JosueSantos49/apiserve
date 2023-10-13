package br.com.projeto.apiservice.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projeto.apiservice.modelo.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long>{
    
    
}
