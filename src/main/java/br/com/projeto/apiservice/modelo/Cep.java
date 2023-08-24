package br.com.projeto.apiservice.modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cep {
    
    private String cep;

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;
}
