package br.com.projeto.apiservice.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PessoaDTO(
	Long codigo,
	@NotBlank @NotNull @Length(min = 20, max = 200) String nome,
	@NotNull String cpf) {
}
