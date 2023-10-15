package br.com.projeto.apiservice.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.projeto.apiservice.dto.PessoaDTO;
import br.com.projeto.apiservice.modelo.Pessoa;

//Transferir dados de um objeto para o outro

@Component
public class PessoaMapper {

	public PessoaDTO toDTO(Pessoa pessoa) {
		if(pessoa == null) {
			return null;
		}
		return new PessoaDTO(pessoa.getCodigo(), pessoa.getNome(), pessoa.getCpf());
	}
	
	public Pessoa toEntity(PessoaDTO pessoaDTO) {
		if(pessoaDTO == null){
			return null;
		}
		
		Pessoa pessoa = new Pessoa();
		
		if(pessoaDTO.codigo() != null) {
			pessoa.setCodigo(pessoaDTO.codigo());
		}
		pessoa.setNome(pessoaDTO.nome());
		pessoa.setCpf(pessoaDTO.cpf());
		
		return pessoa;
	}
}
