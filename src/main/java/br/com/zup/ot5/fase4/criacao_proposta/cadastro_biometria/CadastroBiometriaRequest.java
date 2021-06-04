package br.com.zup.ot5.fase4.criacao_proposta.cadastro_biometria;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.zup.ot5.fase4.criacao_proposta.core.validation.Base64;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Biometria;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;

public class CadastroBiometriaRequest {

	@NotBlank
	@Base64
	private String fingerprint;

	@JsonCreator
	public CadastroBiometriaRequest(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public Biometria paraBiometria(Cartao cartao) {
		return new Biometria(this.fingerprint, cartao);
	}

	public String getFingerprint() {
		return fingerprint;
	}
}
