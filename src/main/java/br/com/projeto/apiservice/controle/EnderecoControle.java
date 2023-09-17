package br.com.projeto.apiservice.controle;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
public class EnderecoControle {

    private final EnderecoService enderecoService;

    @GetMapping("/consulta")
    @PreAuthorize("hasAnyRole('Admin','Usuario')")
    public ResponseEntity consultaCep(@RequestBody EnderecoRequest enderecoRequest){
        return ResponseEntity.ok(enderecoService.executa(enderecoRequest));
        //System.out.println(enderecoRequest.getCep());        
    }
}
