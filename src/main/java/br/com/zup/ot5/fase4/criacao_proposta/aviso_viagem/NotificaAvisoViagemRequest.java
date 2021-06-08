package br.com.zup.ot5.fase4.criacao_proposta.aviso_viagem;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotificaAvisoViagemRequest {

	@NotBlank
	private String destino;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull
	@FutureOrPresent
	private LocalDate validoAte;

	public NotificaAvisoViagemRequest(@NotBlank String destino, @NotNull @FutureOrPresent LocalDate validoAte) {
		super();
		this.destino = destino;
		this.validoAte = validoAte;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getValidoAte() {
		return validoAte;
	}
}
