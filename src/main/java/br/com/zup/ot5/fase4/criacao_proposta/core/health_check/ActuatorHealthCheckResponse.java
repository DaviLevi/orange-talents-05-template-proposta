package br.com.zup.ot5.fase4.criacao_proposta.core.health_check;

import org.springframework.boot.actuate.health.Status;

public class ActuatorHealthCheckResponse {

	private Status status;

	public ActuatorHealthCheckResponse() {
		super();
	}

	public ActuatorHealthCheckResponse(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}
}
