package br.com.zup.ot5.fase4.criacao_proposta.associa_carteira;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class NotificaAssociacaoCarteiraResponse {
	
	@JsonFormat(shape = Shape.STRING)
	@NotNull
	private ResultadoAssociacaoCarteiraResponse resultado;
	
	@NotBlank
	private String id;

	@JsonCreator
	public NotificaAssociacaoCarteiraResponse(@NotNull ResultadoAssociacaoCarteiraResponse resultado, @NotBlank String id) {
		this.resultado = resultado;
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
}
