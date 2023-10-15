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

import br.com.projeto.apiservice.dto.ProdutoDTO;
import br.com.projeto.apiservice.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Component
@Validated
@RestController
@RequestMapping("/api/auth")
//@AllArgsConstructor
public class ProdutoControle {
        
    private ProdutoService produtoService;  
    
    public ProdutoControle(ProdutoService produtoService) {				
		this.produtoService = produtoService;
	}

    @GetMapping("/lista-produtos")
    @PreAuthorize("hasAnyRole('Admin','Usuario')")
    public @ResponseBody List<ProdutoDTO> selecionar(){
        return produtoService.list();
    }
    
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAnyRole('Admin','Usuario')")
    public ProdutoDTO findById(@PathVariable("codigo") @NotNull @Positive Long identificador){
        return produtoService.findById(identificador);
    }
    
	@PostMapping("/criar-produto")
    @PreAuthorize("hasAnyRole('Admin')")
	@ResponseStatus(code = HttpStatus.CREATED)
    public ProdutoDTO criar(@RequestBody @Valid @NotNull ProdutoDTO produto){
        return produtoService.criar(produto);
    }           

    @PutMapping("/{codigo}")
    @PreAuthorize("hasRole('Admin')")
    public ProdutoDTO editar(@PathVariable Long codigo, @RequestBody @Valid @NotNull ProdutoDTO produto){
        return produtoService.editar(codigo, produto);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasRole('Admin')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void remover(@PathVariable @NotNull @Positive long codigo){
    	produtoService.remover(codigo);
    }
   
}
