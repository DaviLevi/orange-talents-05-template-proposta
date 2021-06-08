package br.com.zup.ot5.fase4.criacao_proposta.associa_carteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class NotificaAssociacaoCarteiraRequest {

	@NotBlank
	@Email
	private String email;
	
	@NotNull
	@JsonFormat(shape = Shape.STRING)
	private TipoCarteiraLegadoRequest carteira;

	public NotificaAssociacaoCarteiraRequest(@NotBlank @Email String email,
			@NotNull TipoCarteiraLegadoRequest carteira) {
		this.email = email;
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public TipoCarteiraLegadoRequest getCarteira() {
		return carteira;
	}
}
