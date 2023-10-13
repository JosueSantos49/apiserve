package br.com.projeto.apiservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.projeto.apiservice.dto.ProdutoDTO;
import br.com.projeto.apiservice.dto.mapper.ProdutoMapper;
import br.com.projeto.apiservice.excecao.RegistroNaoEncontradoExcecao;
import br.com.projeto.apiservice.repositorio.ProdutoRepositorio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
public class ProdutoService {

	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	private ProdutoMapper produtoMapper;

	public ProdutoService(ProdutoRepositorio produtoRepositorio, ProdutoMapper produtoMapper) {		
		this.produtoRepositorio = produtoRepositorio;
		this.produtoMapper = produtoMapper;
	}
	
	public List<ProdutoDTO> list(){
        return produtoRepositorio.findAll()
        		.stream()
        		.map(produtoMapper::toDTO)
        		.collect(Collectors.toList());
    }
    
    public ProdutoDTO findById(@PathVariable("codigo") @NotNull @Positive Long identificador){
        return produtoRepositorio.findById(identificador)
        		.map(produtoMapper::toDTO)
        		.orElseThrow(() -> new RegistroNaoEncontradoExcecao(identificador));
    }
    
    public ProdutoDTO criar(@Valid @NotNull ProdutoDTO produto){
        return produtoMapper.toDTO(produtoRepositorio.save(produtoMapper.toEntity(produto)));
    }
    
    public ProdutoDTO editar(@NotNull @Positive Long codigo, @NotNull @Valid ProdutoDTO produto){
        return produtoRepositorio.findById(codigo)
        		.map(registroEncontrado -> {
        			registroEncontrado.setTitulo(produto.titulo());
        			registroEncontrado.setPreco(produto.preco());
        			registroEncontrado.setQuantidade(produto.quantidade());
        			return produtoMapper.toDTO(produtoRepositorio.save(registroEncontrado));        			
        		}).orElseThrow(() -> new RegistroNaoEncontradoExcecao(codigo));
    }
    
    public void remover(@PathVariable @NotNull @Positive long codigo){    	
    	produtoRepositorio.delete(produtoRepositorio.findById(codigo)
    				.orElseThrow(() -> new RegistroNaoEncontradoExcecao(codigo)));   	
    }
	
}
