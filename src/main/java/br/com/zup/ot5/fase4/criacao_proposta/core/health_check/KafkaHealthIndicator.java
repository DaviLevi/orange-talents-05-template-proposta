package br.com.zup.ot5.fase4.criacao_proposta.core.health_check;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class KafkaHealthIndicator implements HealthIndicator{

    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        details.put("hospedado_em", "http://localhost:9092/");
        
        return Health.status(Status.UP).withDetails(details).build();
    }
}
