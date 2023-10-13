package br.com.projeto.apiservice.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDTO(
		Long codigo, 
		@NotBlank @NotNull @Length(min = 5, max = 100) String titulo, 
		@NotNull double preco, 
		int quantidade) {
}
