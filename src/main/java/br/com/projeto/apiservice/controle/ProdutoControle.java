package br.com.projeto.apiservice.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import br.com.projeto.apiservice.repositorio.ProdutoRepositorio;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class ProdutoControle {
    
    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @PostMapping("/criar-produto")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Produto> criar(@RequestBody Produto produto){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepositorio.save(produto));
    }
    
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAnyRole('Admin')")
    public ResponseEntity<Produto> findById(@PathVariable("codigo") Long identificador){
        return produtoRepositorio.findById(identificador)
            .map(registro -> ResponseEntity.ok(registro))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/lista-produtos")
    @PreAuthorize("hasAnyRole('Admin','Usuario')")
    public Iterable<Produto> selecionar(){
        return produtoRepositorio.findAll();
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Produto> editar(@PathVariable Long codigo, @RequestBody Produto produto){
        return produtoRepositorio.findById(codigo)
        		.map(registroEncontrado -> {
        			registroEncontrado.setTitulo(produto.getTitulo());
        			registroEncontrado.setPreco(produto.getPreco());
        			registroEncontrado.setQuantidade(produto.getQuantidade());
        			Produto editar = produtoRepositorio.save(registroEncontrado);
        			return ResponseEntity.ok().body(editar);
        		})
        		.orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasRole('Admin')")
    public void remover(@PathVariable long codigo){
        produtoRepositorio.deleteById(codigo);
    }
   
}
