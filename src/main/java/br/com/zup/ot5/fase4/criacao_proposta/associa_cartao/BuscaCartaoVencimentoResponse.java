package br.com.zup.ot5.fase4.criacao_proposta.associa_cartao;

import java.time.LocalDateTime;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Vencimento;

public class BuscaCartaoVencimentoResponse {
	
	private String id;
	
	private Integer dia;
	
	private LocalDateTime dataDeCriacao;

	public BuscaCartaoVencimentoResponse(String id, Integer dia, LocalDateTime dataDeCriacao) {
		this.id = id;
		this.dia = dia;
		this.dataDeCriacao = dataDeCriacao;
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
	
	public Vencimento paraVencimento() {
		return new Vencimento(this.id, this.dia, this.dataDeCriacao);
	}
}
