package br.com.projeto.apiservice.controle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.request.EnderecoRequest;
import br.com.projeto.apiservice.service.EnderecoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/endereco")
@RestController
public class EnderecoControle {

    @Autowired
    private final EnderecoService enderecoService;

    @GetMapping("/consulta")
    public ResponseEntity consultaCep(@RequestBody EnderecoRequest enderecoRequest){
        return ResponseEntity.ok(enderecoService.executa(enderecoRequest));
        //System.out.println(enderecoRequest.getCep());        
    }
}
