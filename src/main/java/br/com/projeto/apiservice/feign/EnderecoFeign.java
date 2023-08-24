package br.com.projeto.apiservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.projeto.apiservice.response.EnderecoResponse;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface EnderecoFeign{

    @GetMapping("{cep}/json")
    EnderecoResponse buscaEnderecoCep(@PathVariable("cep") String cep);
}