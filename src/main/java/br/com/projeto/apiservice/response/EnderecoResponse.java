package br.com.projeto.apiservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnderecoResponse {
 
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}
