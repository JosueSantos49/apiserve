package br.com.projeto.apiservice.controle;

import java.util.HashMap;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.apiservice.modelo.Produto;
import br.com.projeto.apiservice.repositorio.Repositorio;
import ch.qos.logback.classic.pattern.Util;

@RestController
@CrossOrigin(origins = "*")//Porta do front-and que deve ser liberada para comunicacao com a API
public class ProdutoControle {
    
    @Autowired
    private Repositorio acao;

    @PostMapping("/")
    public Produto cadastrar(@RequestBody Produto produto){
        return acao.save(produto);
    }

    @GetMapping("/")
    public Iterable<Produto> selecionar(){
        return acao.findAll();
    }

    @PutMapping("/")    
    public Produto editar(@RequestBody Produto produto){
        return acao.save(produto);
    }

    @DeleteMapping("/{codigo}")
    public void remover(@PathVariable long codigo){
        acao.deleteById(codigo);
    }
   
}