package br.com.projeto.apiservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.projeto.apiservice.modelo.Produto;
import br.com.projeto.apiservice.repositorio.ProdutoRepositorio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class ProdutoService {

	private ProdutoRepositorio produtoRepositorio;

	public ProdutoService(ProdutoRepositorio produtoRepositorio) {		
		this.produtoRepositorio = produtoRepositorio;
	}
	
	public Iterable<Produto> selecionar(){
        return produtoRepositorio.findAll();
    }
    
    public Optional<Produto> findById(@PathVariable("codigo") @NotNull @Positive Long identificador){
        return produtoRepositorio.findById(identificador);
    }
    
    public Produto criar(@Valid Produto produto){
        return produtoRepositorio.save(produto);
    }
    
    public Optional<Produto> editar(@NotNull @Positive Long codigo, @Valid Produto produto){
        return produtoRepositorio.findById(codigo)
        		.map(registroEncontrado -> {
        			registroEncontrado.setTitulo(produto.getTitulo());
        			registroEncontrado.setPreco(produto.getPreco());
        			registroEncontrado.setQuantidade(produto.getQuantidade());
        			return produtoRepositorio.save(registroEncontrado);
        			
        		});
    }
    
    public boolean remover(@PathVariable @NotNull @Positive long codigo){
    	return produtoRepositorio.findById(codigo)
        		.map(registroEncontrado -> {
        			produtoRepositorio.deleteById(codigo);
        			return true;
        		})
        		.orElse(false);
    }
	
}
