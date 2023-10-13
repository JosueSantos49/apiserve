package br.com.projeto.apiservice.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.projeto.apiservice.dto.ProdutoDTO;
import br.com.projeto.apiservice.modelo.Produto;

@Component
public class ProdutoMapper {

	public ProdutoDTO toDTO(Produto produto) {
		
		//Se for null nao retornar instancia para nao dar nullPointExeption
		if(produto == null) {
			return null;
		}
		return new ProdutoDTO(produto.getCodigo(), produto.getTitulo(), produto.getPreco(), produto.getQuantidade());
	}
	
	public Produto toEntity(ProdutoDTO produtoDTO) {
		
		if(produtoDTO == null) {
			return null;
		}
		
		Produto produto = new Produto();
		
		if(produtoDTO.codigo() != null) {
			produto.setCodigo(produtoDTO.codigo());
		}
		produto.setTitulo(produtoDTO.titulo());
		produto.setPreco(produtoDTO.preco());
		produto.setQuantidade(produtoDTO.quantidade());
		produto.setStatus("Ativo");
		
		return produto;
	}
}
