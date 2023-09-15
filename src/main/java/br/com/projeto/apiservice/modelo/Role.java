package br.com.projeto.apiservice.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

	@Id
	private String roleNome;
	
	private String roleDescricao;
	
}
