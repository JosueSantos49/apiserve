package br.com.projeto.apiservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.projeto.apiservice.dto.PessoaDTO;
import br.com.projeto.apiservice.dto.mapper.PessoaMapper;
import br.com.projeto.apiservice.excecao.RegistroNaoEncontradoExcecao;
import br.com.projeto.apiservice.repositorio.PessoaRepositorio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
public class PessoaService {

	@Autowired
	private PessoaRepositorio pessoaRepositorio;
	
	private PessoaMapper pessoaMapper;
	
	public PessoaService(PessoaRepositorio pessoaRepositorio, PessoaMapper pessoaMapper) {
		this.pessoaRepositorio = pessoaRepositorio;
		this.pessoaMapper = pessoaMapper;
	}
	
	public List<PessoaDTO> list(){
		return pessoaRepositorio.findAll()
				.stream()
				.map(pessoaMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	public PessoaDTO findById(@PathVariable("codigo") @NotNull @Positive Long identificador) {
		return pessoaRepositorio.findById(identificador)
				.map(pessoaMapper::toDTO)
				.orElseThrow(() -> new RegistroNaoEncontradoExcecao(identificador));
	}
	
	public PessoaDTO criar(@Valid @NotNull PessoaDTO pessoa) {
		return pessoaMapper.toDTO(pessoaRepositorio.save(pessoaMapper.toEntity(pessoa)));
	}
	
	public PessoaDTO editar(@NotNull @Positive Long codigo, @NotNull @Valid PessoaDTO pessoa){
        return pessoaRepositorio.findById(codigo)
        		.map(registroEncontrado -> {
        			registroEncontrado.setNome(pessoa.nome());
        			registroEncontrado.setCpf(pessoa.cpf());
        			return pessoaMapper.toDTO(pessoaRepositorio.save(registroEncontrado));        			
        		}).orElseThrow(() -> new RegistroNaoEncontradoExcecao(codigo));
    }
	
	public void remover(@PathVariable @NotNull @Positive long codigo){    	
    	pessoaRepositorio.delete(pessoaRepositorio.findById(codigo)
    				.orElseThrow(() -> new RegistroNaoEncontradoExcecao(codigo)));   	
    }
	
}
