package br.com.projeto.apiservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;

import br.com.projeto.apiservice.modelo.Pessoa;
import br.com.projeto.apiservice.repositorio.PessoaRepositorio;

@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@SpringBootApplication
public class ApiserviceApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ApiserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
				
	}

	/*
	//Bean de exemplo (é executado quando o projeto é iniciado)
	@Bean
	CommandLineRunner initDatabase(PessoaRepositorio pessoaRepositorio) {
		return args -> {
			pessoaRepositorio.deleteAll();

			Pessoa p = new Pessoa();
			p.setNome("Josué da Conceição");
			p.setCpf("12822617709");

			pessoaRepositorio.save(p);
		};
	}
	*/

}
