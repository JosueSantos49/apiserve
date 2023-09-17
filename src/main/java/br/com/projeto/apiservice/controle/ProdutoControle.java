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
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.modelo.Produto;
import br.com.projeto.apiservice.repositorio.ProdutoRepositorio;

@RestController
@CrossOrigin(origins = "*")//Porta do front-and que deve ser liberada para comunicacao com a API
public class ProdutoControle {
    
    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @PostMapping("/")
    public ResponseEntity<Produto> criar(@RequestBody Produto produto){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepositorio.save(produto));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Produto> findById(@PathVariable("codigo") Long identificador){
        return produtoRepositorio.findById(identificador)
            .map(registro -> ResponseEntity.ok(registro))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('Admin','Usuario')")
    public Iterable<Produto> selecionar(){
        return produtoRepositorio.findAll();
    }

    @PutMapping("/")    
    public Produto editar(@RequestBody Produto produto){
        return produtoRepositorio.save(produto);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasRole('Admin')")
    public void remover(@PathVariable long codigo){
        produtoRepositorio.deleteById(codigo);
    }
   
}
