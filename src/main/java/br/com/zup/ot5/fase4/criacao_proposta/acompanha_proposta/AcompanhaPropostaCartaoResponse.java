package br.com.zup.ot5.fase4.criacao_proposta.acompanha_proposta;

import java.time.LocalDateTime;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;

public class AcompanhaPropostaCartaoResponse {
	
	private String id;
	
	private String titular;
	
	private LocalDateTime emitidoEm;
	
	private Integer limite;
	
	private AcompanhaPropostaCartaoVencimentoResponse vencimento;

	public AcompanhaPropostaCartaoResponse(String id, String titular, LocalDateTime emitidoEm, Integer limite,
			AcompanhaPropostaCartaoVencimentoResponse vencimento) {
		this.id = id;
		this.titular = titular;
		this.emitidoEm = emitidoEm;
		this.limite = limite;
		this.vencimento = vencimento;
	}

	public AcompanhaPropostaCartaoResponse(Cartao cartao) {
		this.id = cartao.getId();
		this.titular = cartao.getTitular();
		this.emitidoEm = cartao.getEmitidoEm();
		this.limite = cartao.getLimite();
		this.vencimento = new AcompanhaPropostaCartaoVencimentoResponse(cartao.getVencimento());
	}

	public String getId() {
		return id;
	}

	public String getTitular() {
		return titular;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public Integer getLimite() {
		return limite;
	}

	public AcompanhaPropostaCartaoVencimentoResponse getVencimento() {
		return vencimento;
	}
}
