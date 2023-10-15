package br.com.projeto.apiservice.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.dto.PessoaDTO;
import br.com.projeto.apiservice.modelo.Pessoa;
import br.com.projeto.apiservice.repositorio.PessoaRepositorio;
import br.com.projeto.apiservice.service.PessoaService;

@Component
@Validated
@RestController
@RequestMapping("/api/pessoas")
//@AllArgsConstructor
public class PessoaControle {

	private PessoaService pessoaService;
	
    @Autowired
    private PessoaRepositorio pessoaRepositorio;
    
    public PessoaControle(PessoaService pessoaService) {
    	this.pessoaService = pessoaService;
    }

    @GetMapping("/lista-pessoas")
    @PreAuthorize("hasAnyRole('Admin','Usuario')")
    public @ResponseBody List<PessoaDTO> lista(){
        return pessoaService.list();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> findById(@PathVariable("codigo") Long identificador) {
        return pessoaRepositorio.findById(identificador)
            .map(registro -> ResponseEntity.ok(registro))
            .orElse(ResponseEntity.notFound().build());
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pessoa criar(@RequestBody Pessoa pessoa) {
        return pessoaRepositorio.save(pessoa);
        //return ResponseEntity.status(HttpStatus.CREATED).body(pessoaRepositorio.save(pessoa));
    }


}
