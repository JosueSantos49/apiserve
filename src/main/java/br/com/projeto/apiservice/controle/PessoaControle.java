package br.com.projeto.apiservice.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.projeto.apiservice.modelo.Pessoa;
import br.com.projeto.apiservice.repositorio.PessoaRepositorio;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pessoas")
@AllArgsConstructor
public class PessoaControle {

    @Autowired
    private PessoaRepositorio pessoaRepositorio;

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Pessoa> lista(){
        return pessoaRepositorio.findAll();
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pessoa criar(@RequestBody Pessoa pessoa) {
        return pessoaRepositorio.save(pessoa);
        //return ResponseEntity.status(HttpStatus.CREATED).body(pessoaRepositorio.save(pessoa));
    }


}
