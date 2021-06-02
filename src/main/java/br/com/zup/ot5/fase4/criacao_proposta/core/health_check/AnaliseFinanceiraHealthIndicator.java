package br.com.zup.ot5.fase4.criacao_proposta.core.health_check;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import br.com.zup.ot5.fase4.criacao_proposta.core.health_check.ServiceHealthCheckClients.AnaliseFinanceiraHealthCheckWebClient;
import feign.FeignException;

@Component
public class AnaliseFinanceiraHealthIndicator implements HealthIndicator{

	@Autowired
	private AnaliseFinanceiraHealthCheckWebClient webClient;
	
    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        details.put("hospedado_em", "http://localhost:9999/");
        details.put("servico_solicitacao", "http://localhost:9999/api/solicitacao");
        
        try {
        	ActuatorHealthCheckResponse resposta = webClient.verificaSaude();
        	return Health.status(resposta.getStatus()).withDetails(details).build();
        }catch(FeignException e) {
        	return Health.down(e).build();
        }
    }
}
