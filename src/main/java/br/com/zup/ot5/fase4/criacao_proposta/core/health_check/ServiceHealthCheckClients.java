package br.com.zup.ot5.fase4.criacao_proposta.core.health_check;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

public interface ServiceHealthCheckClients{
	
	@FeignClient(url = "${proposta.services.analiseFinanceira.host}:${proposta.services.analiseFinanceira.port}/actuator/health", name = "analiseFinancieraHealthCheckClient")
	public interface AnaliseFinanceiraHealthCheckWebClient {
		
		@GetMapping
		ActuatorHealthCheckResponse verificaSaude();
		
	}
}