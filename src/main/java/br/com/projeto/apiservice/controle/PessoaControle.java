package br.com.projeto.apiservice.controle;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.dto.PessoaDTO;
import br.com.projeto.apiservice.dto.ProdutoDTO;
import br.com.projeto.apiservice.service.PessoaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Component
@Validated
@RestController
@RequestMapping("/api/auth")
public class PessoaControle {

	private PessoaService pessoaService;
	    
    public PessoaControle(PessoaService pessoaService) {
    	this.pessoaService = pessoaService;
    }

    @GetMapping("/lista-pessoas")
    @PreAuthorize("hasAnyRole('Admin','Usuario')")
    public @ResponseBody List<PessoaDTO> lista(){
        return pessoaService.list();
    }

    
    @GetMapping("/pessoa/{codigo}")
    //@PreAuthorize("hasAnyRole('Admin','Usuario')")
    public PessoaDTO findById(@PathVariable("codigo") @NotNull @Positive Long identificador) {
        return pessoaService.findById(identificador);
    }

    @PostMapping("/criar-pessoa")
    //@PreAuthorize("hasAnyRole('Admin')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PessoaDTO criar(@RequestBody @Valid @NotNull PessoaDTO pessoa) {
        return pessoaService.criar(pessoa);
    }
    
    @PutMapping("/pessoa/{codigo}")
    //@PreAuthorize("hasRole('Admin')")
    public PessoaDTO editar(@PathVariable Long codigo, @RequestBody @Valid @NotNull PessoaDTO pessoa){
        return pessoaService.editar(codigo, pessoa);
    }
    
    @DeleteMapping("/pessoa/{codigo}")
    //@PreAuthorize("hasRole('Admin')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable @NotNull @Positive long codigo){
    	pessoaService.remover(codigo);
    }


}
