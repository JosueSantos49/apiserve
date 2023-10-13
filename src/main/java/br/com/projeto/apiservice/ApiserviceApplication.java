package br.com.projeto.apiservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
//@EntityScan( basePackages = {"br.com.projeto.apiservice.*"} )
//@SpringBootApplication( scanBasePackages = {"br.com.projeto.apiservice.*"} )
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
//@ComponentScan( basePackages = { "br.com.projeto.apiservice.service.ProdutoService" } )
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
