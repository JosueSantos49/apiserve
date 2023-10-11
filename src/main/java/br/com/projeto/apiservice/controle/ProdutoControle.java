package br.com.projeto.apiservice.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.modelo.Produto;
import br.com.projeto.apiservice.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
@RequestMapping("/api/auth")
//@AllArgsConstructor
public class ProdutoControle {
    
    @Autowired
    private final ProdutoService produtoService;  
    
    public ProdutoControle(ProdutoService produtoService) {				
		this.produtoService = produtoService;
	}

    @GetMapping("/lista-produtos")
    @PreAuthorize("hasAnyRole('Admin','Usuario')")
    public Iterable<Produto> selecionar(){
        return produtoService.selecionar();
    }
    
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Produto> findById(@PathVariable("codigo") @NotNull @Positive Long identificador){
        return produtoService.findById(identificador)
            .map(registro -> ResponseEntity.ok(registro))
            .orElse(ResponseEntity.notFound().build());
    }
    
	@PostMapping("/criar-produto")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Produto> criar(@RequestBody @Valid Produto produto){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criar(produto));
    }           

    @PutMapping("/{codigo}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Produto> editar(@PathVariable Long codigo, @RequestBody @Valid Produto produto){
        return produtoService.editar(codigo, produto)
        		.map(registroEncontrado -> ResponseEntity.ok().body(registroEncontrado))
        		.orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> remover(@PathVariable @NotNull @Positive long codigo){
    	if(produtoService.remover(codigo)) {
    		return ResponseEntity.noContent().<Void>build();
    	}
        return ResponseEntity.notFound().build();
    }
   
}
