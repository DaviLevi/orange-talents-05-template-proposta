package br.com.zup.ot5.fase4.criacao_proposta.acompanha_proposta;

import java.time.LocalDateTime;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Vencimento;

public class AcompanhaPropostaCartaoVencimentoResponse {
	
	private String id;
	
	private Integer dia;
	
	private LocalDateTime dataDeCriacao;

	public AcompanhaPropostaCartaoVencimentoResponse(String id, Integer dia, LocalDateTime dataDeCriacao) {
		this.id = id;
		this.dia = dia;
		this.dataDeCriacao = dataDeCriacao;
	}

	public AcompanhaPropostaCartaoVencimentoResponse(Vencimento vencimento) {
		this.id = vencimento.getId();
		this.dia = vencimento.getDia();
		this.dataDeCriacao = vencimento.getDataDeCriacao();
	}

	public String getId() {
		return id;
	}

	public Integer getDia() {
		return dia;
	}

	public LocalDateTime getDataDeCriacao() {
		return dataDeCriacao;
	}
	
}
