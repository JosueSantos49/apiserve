package br.com.projeto.apiservice.modelo;

import java.util.Set;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {
	
	@Id
	private String usuarioNome;
	
	private String usuarioPrimeiroNome;
	
	private String usuarioUltimoNome;
	
	private String usuarioSenha;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USUARIO_ROLE",
	joinColumns = {
		@JoinColumn(name = "USUARIO_ID")
	},
		inverseJoinColumns = {
				@JoinColumn(name = "ROLE_ID")	
		}
	)
	private Set<Role> role;
}
