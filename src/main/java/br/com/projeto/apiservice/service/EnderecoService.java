package br.com.projeto.apiservice.service;

import org.springframework.stereotype.Service;

import br.com.projeto.apiservice.feign.EnderecoFeign;
import br.com.projeto.apiservice.request.EnderecoRequest;
import br.com.projeto.apiservice.response.EnderecoResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EnderecoService {

    private final EnderecoFeign enderecoFeign;
    
    public EnderecoResponse executa(EnderecoRequest request){
        return enderecoFeign.buscaEnderecoCep(request.getCep());        
    }
}
