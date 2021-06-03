package br.com.zup.ot5.fase4.criacao_proposta.associa_cartao;

import java.time.LocalDateTime;

import br.com.zup.ot5.fase4.criacao_proposta.dominio.Cartao;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Proposta;
import br.com.zup.ot5.fase4.criacao_proposta.dominio.Vencimento;

public class BuscaCartaoResponse {

	private String id;
	
	private String titular;
	
	private LocalDateTime emitidoEm;
	
	private Integer limite;
	
	private BuscaCartaoVencimentoResponse vencimento;
	
	public BuscaCartaoResponse(String id, String titular, LocalDateTime emitidoEm, Integer limite,
			BuscaCartaoVencimentoResponse vencimento) {
		this.id = id;
		this.titular = titular;
		this.emitidoEm = emitidoEm;
		this.limite = limite;
		this.vencimento = vencimento;
	}

	public Cartao paraCartao(Proposta proposta) {
		Vencimento vencimento = this.vencimento.paraVencimento();
		Cartao cartao = new Cartao(this.id, this.titular, this.emitidoEm, this.limite, vencimento, proposta);
		proposta.associaCartao(cartao);
		cartao.associaVencimento(vencimento);
		return cartao;
	}
	
}
