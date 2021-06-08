package br.com.zup.ot5.fase4.criacao_proposta.associa_carteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Carteira;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.TipoCarteira;

public class AssociaCarteiraPaypalRequest implements AssociaCarteiraRequest{

	@Email
	@NotBlank
	private String email;

	@JsonCreator
	public AssociaCarteiraPaypalRequest(@Email @NotBlank String email) {
		super();
		this.email = email;
	}

	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public Carteira converterParaCarteira(Cartao cartaoAssociado, String id) {
		return new Carteira(id, cartaoAssociado, TipoCarteira.PAYPAL, this.email);
	}
}
