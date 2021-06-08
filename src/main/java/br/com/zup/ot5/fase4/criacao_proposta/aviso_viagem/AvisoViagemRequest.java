package br.com.zup.ot5.fase4.criacao_proposta.aviso_viagem;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.AvisoViagem;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;

public class AvisoViagemRequest {

	@NotBlank
	private String destino;
	
	@NotNull
	@FutureOrPresent
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataTerminoViagem;

	
	@JsonCreator
	public AvisoViagemRequest(@NotBlank String destino, @NotNull @FutureOrPresent @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataTerminoViagem) {
		super();
		this.destino = destino;
		this.dataTerminoViagem = dataTerminoViagem;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getDataTerminoViagem() {
		return dataTerminoViagem;
	}
	
	public AvisoViagem converteParaAvisoViagem(String ipCliente, String userAgent, Cartao cartaoAvisado) {
		return new AvisoViagem(this.destino, this.dataTerminoViagem, ipCliente, userAgent, cartaoAvisado);
	}
}
