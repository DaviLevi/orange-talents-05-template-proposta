package br.com.zup.ot5.fase4.criacao_proposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CriacaoPropostaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CriacaoPropostaApplication.class, args);
	}

}
